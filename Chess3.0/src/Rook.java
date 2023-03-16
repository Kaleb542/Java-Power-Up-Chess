
public class Rook extends Piece {
	public Rook(String team, String location) {
		super(team, location);
	}
	public String toString() {
		return "rook";
	}
	public void move(Board board, int x, int y) {
		super.move(board, x, y);
		super.getCurrentLocation(board).setPiece(new Rook(super.team, super.getCurrentLocation(board).getLocation()));
	}
	public boolean testMove(Board board, int x, int y) {
		return super.testMove(board, x, y);
	}
	public boolean checkRules(int x, int y, Board board) {
		if (x != super.getX() && y != super.getY()) {
			super.illegal = true;
			return false;
		}else{
			return super.checkRules(x, y, board);
		}
	}
}
