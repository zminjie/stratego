package server.protocol;

public class S54ConfirmPieceRemoval extends ServerMessage {

	//Server sends (row, col, piece ID) where (row, col) is the coordinates of
	//that piece to confirm the piece's removal from the setup board

	int row, col, pieceID;
	
	public S54ConfirmPieceRemoval(byte[] array){
		payload = array;
		row = (int)payload[0];
		col = (int)payload[1];
		pieceID = (int)payload[2];
	}
	
	
	@Override
	public byte getFrameType() {
		return 54;
	}

	/**
	 * Byte array is (row, col, pieceID)
	 */
	
}
