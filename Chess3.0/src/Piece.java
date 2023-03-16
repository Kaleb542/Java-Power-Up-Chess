
public abstract class Piece {
	protected final String team;
	protected String location;
	protected boolean illegal = false;
	private boolean moving = false;
	public Piece(String team, String location) {
		this.team = team;
		this.location = location;
	}
	public boolean checkValid(Board board, int x, int y) {
		if (board.getSquare(x, y).getOccupied() == true) {
			if (board.getSquare(x, y).getPiece().getTeam() == team) {
				if (moving == true) {
					System.out.println("That space is occupied!");
				}
				return false;
			}else {
				return true;
			}
		}else {
			return true;
		}
	}
	public boolean testMove(Board board, int x, int y) {
		if (checkValid(board, x, y) == true && checkRules(x, y, board) == true) {
			String tempLoc = location;
			getCurrentLocation(board).setOccupied(false);
			location = board.getSquare(x, y).getLocation();
			Piece temPiece = null;
			boolean tempOccupy = board.getSquare(x, y).getOccupied();
			if (board.getSquare(x, y).getOccupied() == true) {
				temPiece = board.getSquare(x, y).getPiece();
			}
			board.getSquare(x, y).setOccupied(false);
			//If moving this piece puts its king in check, revert the move.
			if ((team == "B" && board.getBlackKing().inCheck(board) == true || team == "W" && board.getWhiteKing().inCheck(board) == true)) {
				board.getSquare(x, y).addPiece(new Knight(team, location));//This looks sloppy, but it has a good reason: to check if a move will successfully block check, we need to set that space to occupied due to how the checkRules() method works. However, this will also trigger the inCheck method, which throws an error if the space is set to occupied without a piece value. It also ensures that no "ghost" pieces can screw this up.
				board.getSquare(x, y).setOccupied(true);
				if ((team == "B" && board.getBlackKing().inCheck(board) == true || team == "W" && board.getWhiteKing().inCheck(board) == true)) {
					board.getSquare(x, y).setOccupied(tempOccupy);
					board.getSquare(x, y).setPiece(temPiece);
					location = tempLoc;
					getCurrentLocation(board).setOccupied(true);
					return false;
				}else {
					board.getSquare(x, y).setOccupied(tempOccupy);
					board.getSquare(x, y).setPiece(temPiece);
					location = tempLoc;
					getCurrentLocation(board).setOccupied(true);
					return true;
				}
			}else {
				board.getSquare(x, y).setOccupied(tempOccupy);
				board.getSquare(x, y).setPiece(temPiece);
				location = tempLoc;
				getCurrentLocation(board).setOccupied(true);
				return true;
			}
		}else if (illegal == true) {
			illegal = false;
			return false;
		}
		return false;
	}
	public void move(Board board, int x, int y) {
		if (checkValid(board, x, y) == true && checkRules(x, y, board) == true) {
			String tempLoc = location;
			getCurrentLocation(board).setOccupied(false);
			location = board.getSquare(x, y).getLocation();
			Piece temPiece = null;
			boolean tempOccupy = board.getSquare(x, y).getOccupied();
			if (board.getSquare(x, y).getOccupied() == true) {
				temPiece = board.getSquare(x, y).getPiece();
			}
			board.getSquare(x, y).setOccupied(false);
			//If moving this piece puts its king in check, revert the move.
			if ((team == "B" && board.getBlackKing().inCheck(board) == true || team == "W" && board.getWhiteKing().inCheck(board) == true)) {
				board.getSquare(x, y).addPiece(new Knight(team, location));//This looks sloppy, but it has a good reason: to check if a move will successfully block check, we need to set that space to occupied due to how the checkRules() method works. However, this will also trigger the inCheck method, which throws an error if the space is set to occupied without a piece value. It also ensures that no "ghost" pieces can screw this up.
				board.getSquare(x, y).setOccupied(true);
				if ((team == "B" && board.getBlackKing().inCheck(board) == true || team == "W" && board.getWhiteKing().inCheck(board) == true)) {
					board.getSquare(x, y).setOccupied(tempOccupy);
					board.getSquare(x, y).setPiece(temPiece);
					location = tempLoc;
					getCurrentLocation(board).setOccupied(true);
					System.out.println("Can't move there; in check or moving into check.");
				}else {
					location = tempLoc;
					board.getSquare(x, y).setOccupied(true);
					getCurrentLocation(board).setOccupied(false);
					getCurrentLocation(board).setPiece(null);
					location = board.getSquare(x, y).getLocation();
				}
			}else {
				location = tempLoc;
				board.getSquare(x, y).setOccupied(true);
				getCurrentLocation(board).setOccupied(false);
				getCurrentLocation(board).setPiece(null);
				location = board.getSquare(x, y).getLocation();
			}
		}else if (illegal == true) {
			System.out.println("Illegal move.");
			illegal = false;
		}
	}
	public boolean checkRules(int x, int y, Board board) {
		while (x != getX() || y != getY()) {
			if (x > getX()) {
				x--;
			}else if (x < getX()) {
				x++;
			}
			if (y > getY()) {
				y--;
			}else if (y < getY()) {
				y++;
			}
			if (board.getSquare(x, y).getOccupied() == true && (board.getSquare(x, y) != getCurrentLocation(board))) {
				if (moving == true) {
					System.out.println("Cannot get there.");
				}
				return false;
			}
		}
		return true;
	}
	public int getX() {
		switch(location.charAt(0)) {
		case 'A': return 0;
		case 'B': return 1;
		case 'C': return 2;
		case 'D': return 3;
		case 'E': return 4;
		case 'F': return 5;
		case 'G': return 6;
		case 'H': return 7;
		default: return 8;//This will cause an error. That's intentional; if this returns an 8, something is wrong.
		}
	}
	public int getY() {
		return Character.getNumericValue(location.charAt(1));
	}
	public GridSquare getCurrentLocation(Board board) {
		return board.getSquare(getX(), getY());
	}
	public abstract String toString();
	
	public String getTeam() {
		return team;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public boolean inCheck(Board board) {
		return true;
	}
}
