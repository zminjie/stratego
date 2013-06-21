package server.game;

import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import server.protocol.*;

//import studio9.Server;


/*Client group has fixed the server because the for loops were stupid*/


public class GameController extends Thread {

	BlockingQueue<Runnable> runqueue; /* thread-safe */
	Set<PlayerHandler> players;
	Piece[][] board = new Piece[10][10];
	// initial zero is just a placeholder
	Byte[] maxPieces = new Byte[] { 0, 1, 1, 2, 3, 4, 4, 4, 5, 8, 1, 6, 1 };
	Byte[] zerosPieces = new Byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	Byte[] onesPieces = new Byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	Byte zerosStalemates = 5;
	Byte onesStalemates = 5;
	Byte zerosTimeRequests = 10;
	Byte onesTimeRequests = 10;
	Timer timer;
	TimerTask runsOut;
	Boolean p0ready = false;
	Boolean p1ready = false;

	public GameController() {
		runqueue = new BlockingQueue<Runnable>(10);
		players = new HashSet<PlayerHandler>();
		timer = new Timer();

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				board[i][j] = new Piece((byte) 99, (byte) 99);
			}
		}
		// water is piece/team 77
		board[4][2] = new Piece((byte) 77, (byte) 77);
		board[4][3] = new Piece((byte) 77, (byte) 77);
		board[5][2] = new Piece((byte) 77, (byte) 77);
		board[5][3] = new Piece((byte) 77, (byte) 77);
		board[4][6] = new Piece((byte) 77, (byte) 77);
		board[4][7] = new Piece((byte) 77, (byte) 77);
		board[5][6] = new Piece((byte) 77, (byte) 77);
		board[5][7] = new Piece((byte) 77, (byte) 77);
	}

	public void addRunnable(Runnable r) {
		runqueue.enqueue(r);
	}

	public void addPlayer(final PlayerHandler h) {
		addRunnable(new Runnable() {
			public void run() {
				if (players.size() < 2) {
					players.add(h);
					if (players.size() == 2) {
						for (PlayerHandler h : players) {
							h.send(new S52GameOn(new byte[] { (byte) (h
									.getTeam() )}));
						}
					}
				}
				// message("Player joined: " + h);
				// for (PlayerHandler p : players) {
				// tellClient(p, "New player here! " + h);
				// }
			}
		});

	}

	public void run() {
		while (true) {
			Runnable r = runqueue.dequeue();
			r.run();
		}
	}

	// public void message(String readUTF) {
	// // FIXME Auto-generated method stub
	//
	// }

	public void removePlayer(final PlayerHandler h) {
		addRunnable(new Runnable() {
			public void run() {
				players.remove(h);
				// message("Player left: " + h);
			}
		});
	}

	// BIG METHOD
	public void reactToMessage(Message readFrame, final PlayerHandler p) {

		byte frameType = readFrame.getFrameType();
		byte[] payload = readFrame.getPayload();

		runsOut = new TimerTask() {
			@Override
			public void run() {
				for (PlayerHandler folks : players) {
					folks.send(new S60VictoryMessage(new byte[] {
							(byte) ((p.getTeam() + 1) % 2), 4 }));
				}
			}
		};

		switch (frameType) {

		// placing a piece
		case 2: {
			/**
			 * C2 array is {row,col,id}
			 */

			// off board police
			if (payload[0] < 0 || payload[0] > 9 || payload[1] < 0
					|| payload[1] > 9) {
				p.send(new MError("ridiculous (not on board)".getBytes()));
			}

			// check if the place is occupied already
			if (board[payload[0]][payload[1]].getTeam() != 99) {
				p.send(new S53SetupError(
						new byte[] { 3, payload[0], payload[1] }));
			}

			byte pieceID = payload[2];

			// check to make sure it's a valid piece
			if (pieceID < 1 || pieceID > 12) {
				p.send(new MError("rogue piece".getBytes()));
			}

			// player 0
			if (p.getTeam() % 2 == 0) {
				// check piece resources
				if (zerosPieces[pieceID] + 1 > maxPieces[pieceID]) {
					p.send(new S53SetupError(new byte[] { 1, payload[0],
							payload[1] }));
				}

				// check if it's in 4x10 setup zone
				if (payload[0] < 6) {
					p.send(new S53SetupError(new byte[] { 2, payload[0],
							payload[1] }));
				}

				else {

					// actually place piece
					board[payload[0]][payload[1]] = new Piece((byte) 0, pieceID);
					zerosPieces[pieceID]++;
				}
			}

			// player 1
			else {
				// if (p.getTeam() % 2 == 1) {
				// check piece resources
				if (onesPieces[pieceID] + 1 > maxPieces[pieceID]) {
					p.send(new S53SetupError(new byte[] { 1, payload[0],
							payload[1] }));
				}

				// check if it's in 4x10 setup zone
				if (payload[0] > 3) {
					p.send(new S53SetupError(new byte[] { 2, payload[0],
							payload[1] }));
				}

				// actually place piece
				else {
					board[payload[0]][payload[1]] = new Piece((byte) 1, pieceID);
					onesPieces[pieceID]++;
				}
			}
			break;
		}

		// removing a piece
		case 3: {
			/**
			 * C3 array is {row,col,id}
			 */

			// off board police
			if (payload[0] < 0 || payload[0] > 9 || payload[1] < 0
					|| payload[1] > 9) {
				p.send(new MError("ridiculous (not on board)".getBytes()));
			}

			// check to make sure the place is occupied
			if (board[payload[0]][payload[1]].getTeam() == 99) {
				p
				.send(new MError("ridiculous (no piece to remove)"
						.getBytes()));
			}

			byte pieceID = payload[2];

			// player 0
			if (p.getTeam() % 2 == 0) {
				// check piece resources
				if (zerosPieces[pieceID] == 0) {
					p.send(new MError("ridiculous (haven't placed any of those"
							.getBytes()));
				}

				// check if it's in 4x10 setup zone
				if (payload[0] < 6) {
					p
					.send(new MError("ridiculous (not your piece)"
							.getBytes()));
				}

				else {

					// actually remove piece
					board[payload[0]][payload[1]] = new Piece((byte) 99,
							(byte) 99);
					zerosPieces[pieceID]--;

				}

			}

			// player 1
			else {
				// if (p.getTeam() % 2 == 1) {
				// check piece resources
				if (onesPieces[pieceID] == 0) {
					p.send(new MError("ridiculous (haven't placed any of those"
							.getBytes()));
				}

				// check if it's in 4x10 setup zone
				if (payload[0] > 3) {
					p
					.send(new MError("ridiculous (not your piece)"
							.getBytes()));
				}

				else {
					// actually place piece
					board[payload[0]][payload[1]] = new Piece((byte) 99,
							(byte) 99);
					onesPieces[pieceID]--;
					p.send(new S54ConfirmPieceRemoval(new byte[] { payload[0],
							payload[1], pieceID }));
				}
			}
			break;
		}

		// ready?
		case 4: {

			// check to make sure all the right places are occupied

			for (int i = 6; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					if (board[i][j].equals(new Piece((byte) 99, (byte) 99))) {
						System.out.println("second setup incomplete");
						p.send(new S67SetupIncomplete(new byte[] { 1 }));
					}
				}
			}
			for (int i = 4; i < 6; i++) {
				for (int j = 0; j < 10; j++) {
					if (board[i][j].getPieceID() != 77
							&& board[i][j].getPieceID() != 99) {
						System.out.println((int) board[i][j].getPieceID());
						System.out.println("third setup incomplete");
						p.send(new S67SetupIncomplete(new byte[] { 3 }));
					}
				}
			}

			// check to make sure all the pieces are valid
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					if (!board[i][j].equals(new Piece((byte) 99, (byte) 99))
							|| !board[i][j].equals(new Piece((byte) 77,
									(byte) 77))) {
						if (board[i][j].getPieceID() < 1
								|| board[i][j].getPieceID() > 12
								// trying this
								&& board[i][j].getPieceID() != 99
								&& board[i][j].getPieceID() != 77) {
							System.out.println("ridiculous merror");
							p.send(new MError("ridiculous: rogue pieces"
									.getBytes()));
						}
					}
				}
			}

			// check to make sure each player has the correct number of pieces
			if (p.getTeam() % 2 == 0) {

				for (int i = 0; i < maxPieces.length; i++) {
					if (zerosPieces[i] != maxPieces[i]) {
						System.out.println("zero did not equal max");
						p.send(new S67SetupIncomplete(new byte[] { 1 }));
					} else {
						p0ready = true;
					}
				}
			}

			else {

				for (int i = 0; i < maxPieces.length; i++) {
					if (onesPieces[i] != maxPieces[i]) {
						System.out.println("one did not equal max");
						p.send(new S67SetupIncomplete(new byte[] { 0 }));
					} else {
						p1ready = true;
					}
				}
			}

			if (p0ready && p1ready) {
				for (PlayerHandler folks : players) {
					folks.send(new S55StartPlay());
					folks.send(new S66ChangeTurn(new byte[] { (byte) 0 }));
					// timer.schedule(runsOut, 120000);
				}
			}
		}
		break;

		// move
		case 5: {
			/**
			 * byte array is {r0,c0,r,c,id}
			 */

			byte pieceID = payload[4];

			// check to make sure it's a valid piece
			if (pieceID < 1 || pieceID > 12) {
				p.send(new MError("ridiculous: rogue piece".getBytes()));
			}

			// check to make sure the player is moving their own piece
			if (p.getTeam() % 2 != board[payload[0]][payload[1]].getTeam()) {
				p.send(new MError("ridiculous: not player's piece".getBytes()));
			}
			// check to make sure the piece is on the board
			if (payload[0] < 0 || payload[0] > 9 || payload[1] < 0
					|| payload[1] > 9) {
				p.send(new MError("ridiculous: (moving from outer space)"
						.getBytes()));
			}

			// is it a bomb?
			if (pieceID == 11) {
				p.send(new S58MoveError(new byte[] { 1 }));
			}

			// is it a flag?
			if (pieceID == 12) {
				p.send(new S58MoveError(new byte[] { 2 }));
			}

			// is it going into water?
			// Water is (4,2) (4,3) (5,2) (5,3) and (4, 6) (4,7) (5,6) (5,7)
			if (board[payload[2]][payload[3]].getTeam() == 77) {
				p.send(new S58MoveError(new byte[] { 3 }));
			}

			// is it trying to move out of bounds?
			if (payload[2] < 0 || payload[2] > 9 || payload[3] < 0
					|| payload[3] > 9) {
				p.send(new S58MoveError(new byte[] { 4 }));
			}

			// is it trying to move out of range?
			if (payload[0] != payload[2] && payload[1] != payload[3]){
				p.send(new S58MoveError(new byte[] { 5 }));
				System.out.println(1);
			}

			// scout
			if (pieceID == 9
					&& ((payload[0] != payload[2] ^ payload[1] != payload[3]))) {
				if (payload[0] < payload[2]) {
					for (int i = payload[0] + 1; i < payload[2]; i++) {
						if (board[i][payload[1]].getPieceID() != 99) {
							p.send(new S58MoveError(new byte[] { 5 }));
							System.out.println(2);
						}
					}
				}

				if (payload[0] > payload[2]) {
					for (int i = payload[0] - 1; i > payload[2]; i--) {
						if (board[i][payload[1]].getPieceID() != 99) {
							p.send(new S58MoveError(new byte[] { 5 }));
							System.out.println(3);
						}
					}
				}
				if (payload[1] < payload[3]) {
					for (int i = payload[1] + 1; i < payload[3]; i++) {
						if (board[payload[0]][i].getPieceID() != 99) {
							p.send(new S58MoveError(new byte[] { 5 }));
							System.out.println(4);
						}
					}
				}
				if (payload[1] > payload[3]) {
					for (int i = payload[1] - 1; i > payload[3]; i--) {
						if (board[payload[0]][i].getPieceID() != 99) {
							p.send(new S58MoveError(new byte[] { 5 }));
							System.out.println(5);
						}
					}
				}

			}

			// nonscout
			if (pieceID != 9
					&& !((payload[0] == payload[2] && payload[1] == (payload[3] + 1))
							|| (payload[0] == payload[2] && payload[1] == (payload[3] - 1))
							|| (payload[0] == (payload[2] + 1) && payload[1] == payload[3])
							|| (payload[0] == (payload[2] - 1) && payload[1] == payload[3]))) {
				p.send(new S58MoveError(new byte[] { 5 }));
				System.out.println(6);
			}

			// is it trying to attack itself (friendly fire)
			if ((board[payload[2]][payload[3]].getTeam() != 99) && (board[payload[2]][payload[3]].getTeam() != 77) 
					&& (p.getTeam() % 2) == (board[payload[2]][payload[3]].getTeam() % 2)) {
				p.send(new S58MoveError(new byte[] { 7 }));
				System.out.println(7);
			}

			// successful move...
			else {

				// vacate space
				board[payload[0]][payload[1]] = new Piece((byte) 99, (byte) 99);

				// regular move
				if (board[payload[2]][payload[3]].getPieceID() == 99) {
					board[payload[2]][payload[3]] = new Piece((byte) (p
							.getTeam() % 2), (byte) pieceID);
					p.send(new S56ValidMoveConfirmationToMover(payload));
					p.send(new S66ChangeTurn(
							new byte[] { (byte) ((p.getTeam() + 1) % 2) }));

					for (PlayerHandler folks : players) {
						if (folks != p) {
							folks.send(new S57ValidMoveConfirmationToNonmover(
									new byte[] { payload[0], payload[1],
											payload[2], payload[3] }));
							folks.send(new S66ChangeTurn(
									new byte[] { (byte) ((p.getTeam() + 1) % 2) }));
						}
					}

				} else {
					// battle

					// flag is captured
					System.out.println("Player "+(p.getTeam()+1)%2+":"+pieceID);
					System.out.println("Player "+p.getTeam()%2+":"+board[payload[2]][payload[3]].getPieceID());
					if (board[payload[2]][payload[3]].getPieceID() == 12) {
						p.send(new S56ValidMoveConfirmationToMover(payload));
					/*	for (PlayerHandler folks : players) {
							if (folks != p) {
								folks
								.send(new S57ValidMoveConfirmationToNonmover(
										new byte[] { payload[0],
												payload[1], payload[2],
												payload[3] }));
							}
						}*/
						for (PlayerHandler folks : players) {
							folks
							.send(new S59BattleResult(
									new byte[] {
											pieceID,
											(board[payload[2]][payload[3]]
											                   .getPieceID()),
											                   payload[2], payload[3],
											                   payload[0], payload[1] }));
						}
						board[payload[2]][payload[3]] = new Piece((byte) (p
								.getTeam() % 2), (byte) pieceID);
						for (PlayerHandler folks : players) {
							folks.send(new S60VictoryMessage(new byte[] {
									(byte) (p.getTeam() % 2), (byte) 1 }));
						}
					}

					// spy takes marshal
					else if (pieceID == 10
							&& board[payload[2]][payload[3]].getPieceID() == 1) {
						for (PlayerHandler folks : players) {
							folks
							.send(new S59BattleResult(
									new byte[] {
											pieceID,
											(board[payload[2]][payload[3]]
											                   .getPieceID()),
											                   payload[2], payload[3],
											                   payload[0], payload[1] }));
						}
						for (PlayerHandler folks : players) {
							board[payload[2]][payload[3]] = new Piece((byte) (p
									.getTeam() % 2), (byte) pieceID);
							folks
							.send(new S66ChangeTurn(
									new byte[] { (byte) ((p.getTeam() + 1) % 2) }));
						}
					}

					// miner takes bomb
					else if (pieceID == 8
							&& board[payload[2]][payload[3]].getPieceID() == 11) {
						for (PlayerHandler folks : players) {
							folks
							.send(new S59BattleResult(
									new byte[] {
											pieceID,
											(board[payload[2]][payload[3]]
											                   .getPieceID()),
											                   payload[2], payload[3],
											                   payload[0], payload[1] }));
						}
						board[payload[2]][payload[3]] = new Piece((byte) (p
								.getTeam() % 2), (byte) pieceID);
						for (PlayerHandler folks : players) {
							folks
							.send(new S66ChangeTurn(
									new byte[] { (byte) ((p.getTeam() + 1) % 2) }));
						}
					}

					// bomb explodes a player
					else if (pieceID != 8
							&& board[payload[2]][payload[3]].getPieceID() == 11) {
						for (PlayerHandler folks : players) {
							folks.send(new S59BattleResult(
									new byte[] {
											(board[payload[2]][payload[3]]
											                   .getPieceID()), pieceID,
											                   payload[2], payload[3], payload[0],
											                   payload[1] }));
						}

						board[payload[0]][payload[1]] = new Piece((byte) 99,
								(byte) 99);
						for (PlayerHandler folks : players) {
							folks
							.send(new S66ChangeTurn(
									new byte[] { (byte) ((p.getTeam() + 1) % 2) }));
						}
					}

					// tie
					else if (pieceID == board[payload[2]][payload[3]]
					                                      .getPieceID()) {
						for (PlayerHandler folks : players) {
							folks
							.send(new S59BattleResult(
									new byte[] {
											pieceID,
											(board[payload[2]][payload[3]]
											                   .getPieceID()),
											                   payload[2], payload[3],
											                   payload[0], payload[1] }));
						}
						board[payload[0]][payload[1]] = new Piece((byte) 99,
								(byte) 99);						
						board[payload[2]][payload[3]] = new Piece((byte) 99,
								(byte) 99);
						for (PlayerHandler folks : players) {
							folks
							.send(new S66ChangeTurn(
									new byte[] { (byte) ((p.getTeam() + 1) % 2) }));
						}
					}

					// regular old battle (spy taken incl.)
					else {
						// player who moved wins
						if (pieceID < board[payload[2]][payload[3]]
						                                .getPieceID()) {

							for (PlayerHandler folks : players) {
								folks.send(new S59BattleResult(new byte[] {
										pieceID,
										(board[payload[2]][payload[3]]
										                   .getPieceID()), payload[2],
										                   payload[3], payload[0], payload[1] }));
							}
							board[payload[2]][payload[3]] = new Piece((byte) (p
									.getTeam() % 2), (byte) pieceID);
						}
						// player who didn't move wins
						else {
							for (PlayerHandler folks : players) {
								folks.send(new S59BattleResult(new byte[] {
										(board[payload[2]][payload[3]]
										                   .getPieceID()), pieceID,
										                   payload[2], payload[3], payload[0],
										                   payload[1] }));
							}
							board[payload[0]][payload[1]] = new Piece((byte) 99,
									(byte) 99);
						}
						for (PlayerHandler folks : players) {
							folks
							.send(new S66ChangeTurn(
									new byte[] { (byte) ((p.getTeam() + 1) % 2) }));
						}
					}

				}// battle

				// timer.cancel();
				// timer.schedule(runsOut, 120000);

			}// reg move

			break;
		}// case 5 bracket

		// forfeit
		case 6: {
			if (payload[0] == 1) {
				for (PlayerHandler folks : players) {
					folks.send(new S60VictoryMessage(new byte[] {
							(byte) ((p.getTeam() + 1) % 2), (byte) 3 }));
				}
				// timer.cancel();
			}
			break;
		}

		// stalemate request
		case 7: {
			if ((p.getTeam() % 2 == 0 && zerosStalemates == 0)
					|| (p.getTeam() % 2 == 1 && onesStalemates == 0)) {
				for (PlayerHandler folks : players) {
					folks.send(new S62StalemateResponseRelay(new byte[] {
							(byte) 2, (byte) zerosStalemates,
							(byte) onesStalemates }));
				}
			}

			for (PlayerHandler folks : players) {
				if (folks != p) {
					folks.send(new S61StalemateQuestion());
				}
				if (p.getTeam() % 2 == 0) {
					zerosStalemates--;
				} else {
					onesStalemates--;
				}
			}
			break;
		}

		// stalemate response relay
		case 8: {
			if (payload[0] == 1) {

				for (PlayerHandler folks : players) {
					folks.send(new S62StalemateResponseRelay(new byte[] {
							(byte) 1, (byte) zerosStalemates,
							(byte) onesStalemates }));
				}
				// timer.cancel();
			} else {
				for (PlayerHandler folks : players) {
					folks.send(new S62StalemateResponseRelay(new byte[] {
							(byte) 0, (byte) zerosStalemates,
							(byte) onesStalemates }));
				}
			}
			break;
		}

		case 10: {
			if ((p.getTeam() % 2 == 0 && zerosTimeRequests == 0)
					|| (p.getTeam() % 2 == 1 && onesTimeRequests == 0)) {
				for (PlayerHandler folks : players) {
					folks.send(new S64TimeRequest(new byte[] { 2 }));
				}
			}

			else {
				p.send(new S64TimeRequest(new byte[] { 1 }));
				if (p.getTeam() % 2 == 0) {
					zerosStalemates--;
				} else {
					onesStalemates--;
				}
				// timer.schedule(runsOut, 120000);
			}
			break;
		}

		case 11: {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
			for (PlayerHandler folks : players) {
				folks.send(new S65RelayChat(payload));
			}
			break;
		}

		// timer is in this method now
		// case 12: {
		// for( PlayerHandler folks : players ) {
		// folks.send(new S60VictoryMessage(new byte[]
		// { (byte)((p.getTeam() + 1) % 2), (byte)4 }));
		// }
		// }

		}// switch bracket

	}// method bracket

	public void message(final String message) {
		addRunnable(new Runnable() {

			public void run() {
				System.out.println(message);
			}
		});

	}
}
