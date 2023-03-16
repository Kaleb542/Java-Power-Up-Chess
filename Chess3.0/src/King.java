
public class King extends Piece{
	public King(String team, String location) {
		super(team, location);
	}
	public String toString() {
		return "king";
	}
	public void move(Board board, int x, int y) {
		if (team == "B") {//Seems redundant at first glance, but this is important for detecting if the king is moving into check.
			board.setBlackKing(board.getSquare(x,  y).getLocation());
		}else {
			board.setWhiteKing(board.getSquare(x,  y).getLocation());
		}
		super.move(board, x, y);
		super.getCurrentLocation(board).setPiece(new King(super.team, super.getCurrentLocation(board).getLocation()));
		if (team == "B") {
			board.setBlackKing(location);
		}else {
			board.setWhiteKing(location);
		}
	}
	public boolean testMove(Board board, int x, int y) {
		if (team == "B") {
			board.setBlackKing(board.getSquare(x,  y).getLocation());
		}else {
			board.setWhiteKing(board.getSquare(x,  y).getLocation());
		}
		boolean result = super.testMove(board, x, y);
		if (team == "B") {
			board.setBlackKing(location);
		}else {
			board.setWhiteKing(location);
		}
		return result;
	}
	public boolean checkRules(int x, int y, Board board) {
		if (x > super.getX() + 1 || x < super.getX() - 1 || y > super.getY() + 1 || y < super.getY() - 1) {
			return false;
		}else{
			return super.checkRules(x, y, board);
		}
	}
	public boolean inCheck(Board board) {
		for (int i = 0; i < 8; i++) {
			for (int x = 0; x < 8; x++) {
				if (board.getSquare(x, i).getOccupied() == true && board.getSquare(x, i).getPiece() != null) {
					if (board.getSquare(x, i).getPiece().getTeam() != team) {
						if ((board.getSquare(x, i).getPiece().checkValid(board, super.getX(), super.getY()) == true) && board.getSquare(x, i).getPiece().checkRules(super.getX(), super.getY(), board) == true) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}
