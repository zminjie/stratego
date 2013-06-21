package server.protocol;

public class S62StalemateResponseRelay extends ServerMessage {

	//Server sends result of stalemate.
	//accept or reject code 1 means it's accepted: game over via stalemate
	//accept or reject code 0 means it's rejected: game continues. stalemates remaining
	//accept or reject code 2 means player who offered is out of stalemates
	//for player 0(red), and player 1(blue), displayed
	
	int stalemateAcceptOrReject, zerosRemaining, onesRemaining;
	
	public S62StalemateResponseRelay(byte[] array) {
		payload = array;
		stalemateAcceptOrReject = (int)payload[0];
		zerosRemaining = (int)payload[1];
		onesRemaining = (int)payload[2];
	}
	
	@Override
	public byte getFrameType() {
		return 62;
	}
	
	/**
	 * Byte array (stalemate response, 0(red)'s remaining stalemates,
	 * 				1(blue)'s remaining stalemates).
	 * stalemate response is 1 (yes) or 0 (no) or 2 (out)
	 */


}
