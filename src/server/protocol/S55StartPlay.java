package server.protocol;

public class S55StartPlay extends ServerMessage {

	//The server moves the game from setup to play.  It is Red's (0's) turn.
	
	public S55StartPlay(){
		payload = new byte[0];
	}
	
	@Override
	public byte getFrameType() {
		return 55;
	}

	/**
	 * Byte array is empty
	 */

}
