package server.protocol;

public class S63Ping extends ServerMessage {

	public S63Ping(){
		payload = new byte[0];
	}
	
	@Override
	public byte getFrameType() {
		return 63;
	}
	
	/**
	 * Byte array is empty
	 */

}
