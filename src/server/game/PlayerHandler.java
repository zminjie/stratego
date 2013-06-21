package server.game;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.Socket;

import server.protocol.S51PlayerWait;

import client.protocol.C1PlayerName;

/**
 * An instance of this class sits between each Client
 * and the server.
 * @author cytron
 * edited by Stratego
 * 
 */
public class PlayerHandler extends Thread {

	private Socket socket;
	private final GameController game;
	private int playerNum;
	private boolean shouldDie;
	private StrategoOutputStream out;
	private final byte team;
	private String name;

	public PlayerHandler(Socket s, GameController g, byte t, String n) {
		this.socket = s;
		this.game = g;
		this.shouldDie = false;
		this.team = t;
		this.name = n;
		
		out = new StrategoOutputStream(s);
	}
	
	/**
	 * Tell the game a player has been added
	 * Then repeatedly get a String from the player and
	 * pass it to the game using sendMessage(string);
	 * 
	 * The protocol here is that the Client sends a String
	 * and this class sends it to the game.  You could just as easily
	 * accept integers, by doing in.readInt() instead of in.readUTF()
	 * 
	 * The protocol for your game is up to you, but it has to be
	 * consistent from the Server and Client points of view.
	 */
	public void run() {
		try {
			// get the socket's input stream and make a DataInputStream out of it
			InputStream istream = socket.getInputStream();
			DataInputStream in = new DataInputStream(istream);
			final StrategoInputStream sis = new StrategoInputStream(in);
			Message clientmessage;
			while (!((clientmessage = sis.readFrame()) instanceof C1PlayerName)){
				Thread.sleep(100);
			}
			setUsername(((C1PlayerName)clientmessage).getUsername());
			this.send(new S51PlayerWait());
			game.addPlayer(this);
			while (true) {
				if (shouldDie) {
					throw new Error("dead player");
				}
				//  call game.message on the next string I read
				final Message m = sis.readFrame();
				game.addRunnable(new Runnable(){
					public void run(){
						game.reactToMessage(m, PlayerHandler.this);
					}
				});				
//				game.message(in.readUTF());
			}

		}
		catch (Throwable t) {
			System.out.println("Noting exception for " + name);
		}
		finally {
			game.removePlayer(this);
		}

	}
	
	public void tellClient(String s) {
		System.out.println("Server would now send message to client: " + s);
	}
	
	/**
	 * Generate uniform messages
	 * @param s
	 */
//	private void sendMessage(String s) {
//		game.message("From Client " + this + " " + s);
//		
//	}
	
	public void die() {
		shouldDie = true;
	}
	
	public int getPlayerNum() {
		return playerNum;
	}

	public String toString() {
		return "Player " + playerNum;
	}
	
	public void send(Message frame){
		out.send(frame);
	}
	
	public byte getTeam(){
		return team;
	}
	
	public String getUsername(){
		return name;
	}
	
	public void setUsername(String n){
		this.name = n;
	}

}
