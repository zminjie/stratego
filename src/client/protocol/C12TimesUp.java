package client.protocol;

public class C12TimesUp extends ClientMessage {

	public C12TimesUp()
	{
		payload = new byte[0];
	}
	
	@Override
	public byte getFrameType() {
		return 12;
	}

}
