package server.protocol;

public class S60VictoryMessage extends ServerMessage {

	//Server tells the clients that the game is over and someone, the playerWhoWon,
	//has won.  Different wincodes for different types of victories:
	//Win Code 1: Flag is attacked
	//Win Code 2: Opponent cannot move
	//Win Code 3: Opponent forfeits
	//Win Code 4: Opponent times out
	
	int playerWhoWon, winCode;
	
	public S60VictoryMessage(byte[] array){
		payload = array;
		playerWhoWon = (int)payload[0];
		winCode = (int)payload[1];
	}
	
	@Override
	public byte getFrameType() {
		return 60;
	}
	
	/**
	 * Byte array is (playerWhoWon, winCode). playerWhoWon is 0 or 1, winCode is
	 * 1, 2, 3, or 4
	 */

}
