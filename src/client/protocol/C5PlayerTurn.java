package client.protocol;

public class C5PlayerTurn extends ClientMessage {

	int r0, c0, r, c, id;
	
	public C5PlayerTurn(byte[] array)
	{
		payload = array;
		r0 = (int)payload[0];
		c0 = (int)payload[1];
		r = (int)payload[2];
		c = (int)payload[3];
	}
	
	public byte getFrameType() {
		return 5;
	}

	
	/**
	 *  byte array is {r0,c0,r,c,id}
	 */
	 
	
}
