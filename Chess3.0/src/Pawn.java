
public class Pawn extends Piece {
	public Pawn(String team, String location) {
		super(team, location);
	}

	public String toString() {
		return "pawn";
	}

	public void move(Board board, int x, int y) {
		super.move(board, x, y);
		if (super.getY() == 0 || super.getY() == 7) {
			super.getCurrentLocation(board)
					.setPiece(new Queen(super.team, super.getCurrentLocation(board).getLocation()));
		} else {
			super.getCurrentLocation(board)
					.setPiece(new Pawn(super.team, super.getCurrentLocation(board).getLocation()));
		}
	}

	/*
	 * public boolean testMove(Board board, int x, int y) { return
	 * super.testMove(board, x, y); }
	 */
	@Override
	public boolean checkRules(int x, int y, Board board) {// Interestingly, the pawn was the first piece that I
															// programmed, since its limited basic movement was so
															// simple. But as I continued to flesh out the rules, and
															// the pawns became cabable of more than just moving
															// forward, it turned into the most complicated.
		if (super.team == "B") {
			if (y > super.getY() + 1 || (x > super.getX() + 1 || x < super.getX() - 1) || y <= super.getY()) {
				if (x == super.getX() && y == super.getY() + 2 && board.getSquare(x, y).getOccupied() == false) {
					return true;
				} else {
					return false;
				}
			} else if (x == super.getX() + 1 || x == super.getX() - 1) {
				if (board.getSquare(x, y).getOccupied() == true) {
					return true;
				} else {
					return false;
				}
			} else if (board.getSquare(x, y).getOccupied() == false) {
				return true;
			} else {
				System.out.println("Cannot get there.");
				return false;
			}
		} else {
			if (y < super.getY() - 1 || (x > super.getX() + 1 || x < super.getX() - 1) || y >= super.getY()) {
				if (x == super.getX() && y == super.getY() - 2 && board.getSquare(x, y).getOccupied() == false) {
					return true;
				} else {
					return false;
				}
			} else if (x == super.getX() + 1 || x == super.getX() - 1) {
				if (board.getSquare(x, y).getOccupied() == true) {
					return true;
				} else {
					return false;
				}
			} else if (board.getSquare(x, y).getOccupied() == false) {
				return true;
			} else {
				System.out.println("Cannot get there.");
				return false;
			}
		}
	}
}
