package server.protocol;

public class S59BattleResult extends ServerMessage {

	//Server sends clients info about the battle that just happened
	//If there is a tie, winnerID and loserID correspond to the members
	//of the tie.
	//Client handles result of battle.
	
	int winnerID, loserID, row, col, r0, c0;
	
	public S59BattleResult(byte[] array) {
		payload = array;
		winnerID = (int)payload[0];
		loserID = (int)payload[1];
		row = (int)payload[2];
		col = (int)payload[3];
		r0 = (int)payload[4];
		c0 = (int)payload[5];
	}
	
	@Override
	public byte getFrameType() {
		return 59;
	}
	
	/**
	 * Byte array is (winnerID, loserID, row, col). winnerID and loserID can also
	 * be the two members of a tying battle
	 */

}
