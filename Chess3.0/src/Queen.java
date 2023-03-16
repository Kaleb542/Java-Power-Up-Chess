
public class Queen extends Piece {
	public Queen(String team, String location) {
		super(team, location);
	}
	public String toString() {
		return "queen";
	}
	public void move(Board board, int x, int y) {
		super.move(board, x, y);
		super.getCurrentLocation(board).setPiece(new Queen(super.team, super.getCurrentLocation(board).getLocation()));
	}
	public boolean testMove(Board board, int x, int y) {
		return super.testMove(board, x, y);
	}
	public boolean checkRules(int x, int y, Board board) {
		if (x != super.getX() && y != super.getY() && Math.abs(x - super.getX()) != Math.abs(y - super.getY())) {
			return false;
		}else{
			return super.checkRules(x, y, board);
		}
	}
}
