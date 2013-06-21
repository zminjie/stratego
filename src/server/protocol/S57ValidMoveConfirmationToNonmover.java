package server.protocol;

public class S57ValidMoveConfirmationToNonmover extends ServerMessage {

	//Server sends original row, column position and new row, column position
	//of moved piece to only the NONMOVER.  Nb. PieceID is not sent
	
	int roworig, colorig, rownew, colnew;
	
	public S57ValidMoveConfirmationToNonmover(byte[] array) {
		payload = array;
		roworig = (int)payload[0];
		colorig = (int)payload[1];
		rownew = (int)payload[2];
		colnew = (int)payload[3];

	}
	
	@Override
	public byte getFrameType() {
		return 57;
	}
	
	/**
	 * Byte array is (old row, old col, new row, new col)
	 */

}
