package server.protocol;

public class S56ValidMoveConfirmationToMover extends ServerMessage {
	
	//Server sends original row, column position and new row, column position
	//along with pieceID of moved piece to only the MOVER
	
	int roworig, colorig, rownew, colnew, pieceID;

	public S56ValidMoveConfirmationToMover(byte[] array) {
		payload = array;
		roworig = (int)payload[0];
		colorig = (int)payload[1];
		rownew = (int)payload[2];
		colnew = (int)payload[3];
		pieceID = (int)payload[4];
	}

	@Override
	public byte getFrameType() {
		return 56;
	}

	/**
	 * Byte array is (old row, old col, new row, new col, pieceID)
	 */

}
