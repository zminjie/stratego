package server.game;


import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import server.protocol.S51PlayerWait;
import server.protocol.S52GameOn;
import server.protocol.S53SetupError;
import server.protocol.S54ConfirmPieceRemoval;
import server.protocol.S55StartPlay;
import server.protocol.S56ValidMoveConfirmationToMover;
import server.protocol.S57ValidMoveConfirmationToNonmover;
import server.protocol.S58MoveError;
import server.protocol.S59BattleResult;
import server.protocol.S60VictoryMessage;
import server.protocol.S61StalemateQuestion;
import server.protocol.S62StalemateResponseRelay;
import server.protocol.S63Ping;
import server.protocol.S64TimeRequest;
import server.protocol.S65RelayChat;
import server.protocol.S66ChangeTurn;
import server.protocol.S67SetupIncomplete;

import client.protocol.C10MoreTime;
import client.protocol.C11SendMessage;
import client.protocol.C12TimesUp;
import client.protocol.C1PlayerName;
import client.protocol.C2PlacePiece;
import client.protocol.C3PieceRemove;
import client.protocol.C4SendReady;
import client.protocol.C5PlayerTurn;
import client.protocol.C6Forfeit;
import client.protocol.C7Stalemate;
import client.protocol.C8StalemateReply;
import client.protocol.C9Pong;

public class StrategoInputStream {
	
	private DataInputStream stream;
	
	public StrategoInputStream(InputStream stream) {
		
		this.stream = new DataInputStream(stream);
		
	}
	
	public Message readFrame() throws IOException {
		byte ast = stream.readByte();
		if (ast != 42){
			throw new IOException("bad [asterisk]");
		}
		byte frameType = stream.readByte();

		short payloadLength = stream.readShort();
		
		byte[] payloadByteArray = new byte[payloadLength];
		stream.readFully(payloadByteArray);
		switch((int)frameType){
			case 1: return new C1PlayerName(payloadByteArray);
			case 2: return new C2PlacePiece(payloadByteArray);
			case 3: return new C3PieceRemove(payloadByteArray);
			case 4: return new C4SendReady(payloadByteArray);
			case 5: return new C5PlayerTurn(payloadByteArray);
			case 6: return new C6Forfeit(payloadByteArray);
			case 7: return new C7Stalemate();
			case 8: return new C8StalemateReply(payloadByteArray);
			case 9: return new C9Pong();
			case 10: return new C10MoreTime();
			case 11: return new C11SendMessage(payloadByteArray);
			case 12: return new C12TimesUp();
			
			case 51: return new S51PlayerWait();
			case 52: return new S52GameOn(payloadByteArray);
			case 53: return new S53SetupError(payloadByteArray);
			case 54: return new S54ConfirmPieceRemoval(payloadByteArray);
			case 55: return new S55StartPlay();
			case 56: return new S56ValidMoveConfirmationToMover(payloadByteArray);
			case 57: return new S57ValidMoveConfirmationToNonmover(payloadByteArray);
			case 58: return new S58MoveError(payloadByteArray);
			case 59: return new S59BattleResult(payloadByteArray);
			case 60: return new S60VictoryMessage(payloadByteArray);
			case 61: return new S61StalemateQuestion();
			case 62: return new S62StalemateResponseRelay(payloadByteArray);
			case 63: return new S63Ping();
			case 64: return new S64TimeRequest(payloadByteArray);
			case 65: return new S65RelayChat(payloadByteArray);
			case 66: return new S66ChangeTurn(payloadByteArray);
			case 67: return new S67SetupIncomplete(payloadByteArray);
						
			}
		
			return new MError(payloadByteArray);
				
			
		}
	}