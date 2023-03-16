
public class GridSquare {
	boolean occupied = false;
	Piece occupyingPiece;
	int ygrid;
	int xgrid;
	boolean powerUp = false;
	public GridSquare(boolean occupied, int x, int y) {
		this.occupied = occupied;
		xgrid = x;
		ygrid = y;
	}
	public String printSquare() {
		if (occupied == true) {
			switch (occupyingPiece.toString()) {
				case "pawn": return occupyingPiece.getTeam() + "P";
				case "rook": return occupyingPiece.getTeam() + "R";
				case "knight": return occupyingPiece.getTeam() + "N";//This is taken from standard chess notation; knights are represented by the letter N to differentiate them from the king at a glance.
				case "bishop": return occupyingPiece.getTeam() + "B";
				case "king": return occupyingPiece.getTeam() + "K";
				case "queen": return occupyingPiece.getTeam() + "Q";
			}
		}
		return "  ";
	}
	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}
	public void setPiece(Piece piece) {
		occupyingPiece = piece;
	}
	public Piece getPiece() {
		return occupyingPiece;
	}
	public boolean getOccupied() {
		return occupied;
	}
	public void addPiece(Piece piece) {
		setOccupied(true);
		setPiece(piece);
	}
	public String getLocation() {
		switch (xgrid) {
		case 0: return "A"+ygrid;
		case 1: return "B"+ygrid;
		case 2: return "C"+ygrid;
		case 3: return "D"+ygrid;
		case 4: return "E"+ygrid;
		case 5: return "F"+ygrid;
		case 6: return "G"+ygrid;
		case 7: return "H"+ygrid;
		default: return "invalid";
		}
	}
	public void setPowerUp(boolean x) {
		powerUp = x;
	}
	public boolean getPowerUp() {
		return powerUp;
	}
}
