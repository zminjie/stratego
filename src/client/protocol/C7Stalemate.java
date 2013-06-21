package client.protocol;

public class C7Stalemate extends ClientMessage {

	public C7Stalemate()
	{
		payload = new byte[0];
	}
	
	public byte getFrameType() {
		return 7;
	}

}
