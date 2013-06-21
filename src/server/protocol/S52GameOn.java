package server.protocol;

public class S52GameOn extends ServerMessage {
	
	//This says: Game on. You are player n (n = 0 xor n = 1).
	//Nb. 0 = red = starting player.
	
	int player;


	public S52GameOn(byte[] array) {
		payload = array;
		player = (int)payload[0];
	}

	@Override
	public byte getFrameType() {
		return 52;
	}
	
	/**
	 * Byte array is (n) which = 0 or 1
	 */

}
