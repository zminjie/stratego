package client.protocol;

public class C3PieceRemove extends ClientMessage {

	int row, col, id;
	
	public C3PieceRemove(byte[] array)
	{
		payload = array;
		row = (int)payload[0];
		col = (int)payload[1];
		id = (int)payload[2];
		
	}
	
	public byte getFrameType() {
		return 3;
	}

}


