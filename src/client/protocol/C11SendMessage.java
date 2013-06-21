package client.protocol;

public class C11SendMessage extends ClientMessage {

	public C11SendMessage(byte[] m)
	{
		payload = m;
	}
	
	public byte getFrameType() {
		return 11;
	}

}
