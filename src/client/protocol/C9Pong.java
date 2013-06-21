package client.protocol;

public class C9Pong extends ClientMessage {

	public C9Pong(){
		payload = new byte[0];
	}
	
	@Override
	public byte getFrameType() {
		return 9;
	}
	
	/**
	 * Byte array is empty
	 */

}
