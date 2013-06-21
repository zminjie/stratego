package server.game;

public class Piece {

	byte team;
	byte pieceID;


	public Piece(byte t, byte p) {
		team = t;
		pieceID = p;
	}

	public byte getTeam() {
		return team;
	}

	public byte getPieceID() {
		return pieceID;
	}
	

	public void setTeam(byte team) {
		this.team = team;
	}

	public void setPieceID(byte pieceID) {
		this.pieceID = pieceID;
	}

	
}
