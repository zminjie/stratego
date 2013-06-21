package server.game;

public class MError extends Message {

	public MError(byte[] error)
	{
		payload = error;
	}
	
	
	@Override
	public byte getFrameType() {
		return 99;
	}
	
}
