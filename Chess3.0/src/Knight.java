
public class Knight extends Piece{
	public Knight(String team, String location) {
		super(team, location);
	}
	public String toString() {
		return "knight";
	}
	public void move(Board board, int x, int y) {
		super.move(board, x, y);
		super.getCurrentLocation(board).setPiece(new Knight(super.team, super.getCurrentLocation(board).getLocation()));
	}
	public boolean testMove(Board board, int x, int y) {
		return super.testMove(board, x, y);
	}
	@Override
	public boolean checkRules(int x, int y, Board board) {//This one doesn't call to the superclass method, since knights can jump over other pieces.
		if (((x == super.getX() + 2 || x == super.getX() - 2) && (y == super.getY() + 1 || y == super.getY() - 1)) || ((x == super.getX() + 1 || x == super.getX() - 1) && (y == super.getY() + 2 || y == super.getY() - 2))) {
			return true;
		}else {
			return false;
		}
	}
}
