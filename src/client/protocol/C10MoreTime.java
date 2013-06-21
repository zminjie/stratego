package client.protocol;

public class C10MoreTime extends ClientMessage {

	public C10MoreTime()
	{
		payload = new byte[0];
	}
	
	public byte getFrameType() {
		return 10;
	}

}
