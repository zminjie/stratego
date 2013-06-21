package server.protocol;

public class S65RelayChat extends ServerMessage {

	//Server sends back chat message
	
	public S65RelayChat(byte[] m){
		payload = m;
	}
	
	@Override
	public byte getFrameType() {
		return 65;
	}
	
	/**
	 * Byte array is the message made bytes
	 */
	
}
