package server.protocol;

public class S58MoveError extends ServerMessage {

	//This is the server telling the client that there has been an error in the move
	//based on its interpretation of the move the client attempted to do
	//Based on the error code sent back, the client handles what should be done.
	
	//Error 1: Trying to move a bomb
	//Error 2: Trying to move a flag
	//Error 3: Trying to move into water
	//Error 4: Trying to move out of bounds
	//Error 5: Trying to move out of range (more than one space if not a scout)
	//Error 6: Trying to repeat a move for the third time (NOT DOING THIS ANYMORE)
	//Error 7: Friendly fire
	
	int e;
	
	public S58MoveError(byte[] array){
		payload = array;
		e = (int)payload[0];
	}
	
	@Override
	public byte getFrameType() {
		return 58;
	}
	
	/**
	 * Byte array is (Error code).  Error code is 1, 2, 3, 4, 5, 6, or 7
	 */

}
