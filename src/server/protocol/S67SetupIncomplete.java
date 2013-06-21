package server.protocol;

public class S67SetupIncomplete extends ServerMessage {
	
/*/
 * 	 Error 1: Player 0 does not have all places occupied / has not used all pieces
	 Error 2: Player 1 does not have all places occupied / has not used all pieces
	 Error 3: Middle has a player
 */
	
	int e;
	
	public S67SetupIncomplete(byte[] array){
		payload = array;
		e = (int)payload[0];
		
	}

	@Override
	public byte getFrameType() {
		return 67;
	}
	
	/**
	 * Byte array is (Error code).
	 */

}
