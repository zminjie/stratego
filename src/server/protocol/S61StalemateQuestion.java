package server.protocol;

public class S61StalemateQuestion extends ServerMessage {

	//Server asks whoever did not send the request for stalemate if he wants
	//to accept or reject it
	
	public S61StalemateQuestion(){
		payload = new byte[0];
	}
		
	@Override
	public byte getFrameType() {
		return 61;
	}

	/**
	 * Byte array is empty
	 */

}
