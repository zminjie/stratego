package server.protocol;

public class S66ChangeTurn extends ServerMessage{

	int whoseTurnItIs;
	
	public S66ChangeTurn(byte[] array){
		payload = array;
		whoseTurnItIs = (int)payload[0];
	}
	
	@Override
	public byte getFrameType() {
		return 66;
	}

}
