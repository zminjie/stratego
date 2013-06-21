package server.protocol;

public class S51PlayerWait extends ServerMessage {
	
	//This is the server telling the client it's waiting for another player

	public S51PlayerWait(){
		payload = new byte[0];
	}
	
	@Override
	public byte getFrameType() {
		return 51;
	}
	
	/**
	 * Byte array is empty
	 */
	
	

}
