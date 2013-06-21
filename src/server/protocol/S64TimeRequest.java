package server.protocol;

public class S64TimeRequest extends ServerMessage {
	
	//server explains to clients result of time request:
	//Result 1: Request granted
	//Result 2: Error - No requests left

	
	int result;
	
	public S64TimeRequest(byte[] array){
		payload = array;
		result = (int)payload[0];
	}

	@Override
	public byte getFrameType() {
		return 64;
	}
	
	/**
	 * Byte array is (result) where result is 1, 2, or 3
	 */

}
