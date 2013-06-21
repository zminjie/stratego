package server.protocol;

public class S53SetupError extends ServerMessage {
	
	//Server telling the client there's been a setup error, giving error code
	//and location: (Error code, rownumber, colnumber)
	
	//Error 1: Using a unit already used up
	//Error 2: Trying to place outside placement zone
	//Error 3: Space already occupied
	//Error 4: Board not filled
	//Force piece removal
	//Nb. On client side, if you put a piece where one of your pieces already is,
	//then you should be asked if you want to replace the piece that's already there
	//or not.  If you do want to replace it, see messages 3 and 54
	
	int e, row, col;
	
	
	public S53SetupError(byte[] array){
		payload = array;
		e = (int)payload[0];
		row = (int)payload[1]; 
		col = (int)payload[2];
		
	}

	@Override
	public byte getFrameType() {
		return 53;
	}

	/**
	 * Byte array is (Error code, row, col).  Error code is 1 or 2 or 3
	 */

}
