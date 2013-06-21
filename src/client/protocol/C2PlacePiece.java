package client.protocol;

public class C2PlacePiece extends ClientMessage{

	int row, col, id;
	public C2PlacePiece(byte[] array)
	{
		payload = array;
		row = (int)payload[0];
		col = (int)payload[1];
		id = (int)payload[2];
	}
	
	public byte getFrameType() {
		return 2;
	}
	
	/**
	 *  byte array is {row,col,id}
	 */

}

