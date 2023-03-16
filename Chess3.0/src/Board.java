

public class Board {
	public GridSquare[][] board = new GridSquare[8][8];
	public Piece whiteKing = new King("W", "D7");
	public Piece blackKing = new King("B", "D0");

	public Board() {
		for (int i = 0; i < 8; i++) {
			for (int x = 0; x < 8; x++) {
				board[i][x] = new GridSquare(false, x, i);
			}
		}
		for (int i = 0; i < 8; i++) {
			board[1][i].addPiece(new Pawn("B", board[1][i].getLocation()));
			board[6][i].addPiece(new Pawn("W", board[6][i].getLocation()));
		}
		for (int i = 0; i < 8; i += 7) {
			String x;
			if (i == 0) {
				x = "B";
			} else {
				x = "W";
			}
			board[i][0].addPiece(new Rook(x, board[i][0].getLocation()));
			board[i][7].addPiece(new Rook(x, board[i][7].getLocation()));
			board[i][2].addPiece(new Bishop(x, board[i][2].getLocation()));
			board[i][5].addPiece(new Bishop(x, board[i][5].getLocation()));
			board[i][1].addPiece(new Knight(x, board[i][1].getLocation()));
			board[i][6].addPiece(new Knight(x, board[i][6].getLocation()));
			board[i][3].addPiece(new King(x, board[i][3].getLocation()));
			board[i][4].addPiece(new Queen(x, board[i][4].getLocation()));
		}
	}

	public void printBoard() {
		System.out.println("    a    b    c    d    e    f    g    h");
		System.out.println("  _________________________________________");
		for (int i = 0; i < 8; i++) {
			System.out.print(8 - i + " | ");
			for (int x = 0; x < 8; x++) {
				System.out.print(board[i][x].printSquare() + " | ");
			}
			System.out.println(8 - i);
			System.out.println("  _________________________________________");
		}
		System.out.println("    a    b    c    d    e    f    g    h");
	}

	public boolean getOccupied(int x, int y) {
		return board[x][y].getOccupied();
	}

	public GridSquare getSquare(int x, int y) {
		return board[y][x];
	}

	public Piece getWhiteKing() {
		return whiteKing;
	}

	public Piece getBlackKing() {
		return blackKing;
	}

	public void setWhiteKing(String location) {
		whiteKing.setLocation(location);
	}

	public void setBlackKing(String location) {
		blackKing.setLocation(location);
	}

	public boolean checkMate(String team) {
		if (whiteKing.inCheck(this) == true || blackKing.inCheck(this) == true) {
			for (int i = 0; i < 8; i++) {
				for (int x = 0; x < 8; x++) {
					if (board[i][x].getOccupied() == true && board[i][x].getPiece() != null) {
						if (board[i][x].getPiece().getTeam() == team) {
							for (int y = 0; y < 8; y++) {
								for (int z = 0; z < 8; z++) {
									if (board[i][x].getPiece().testMove(this, y, z) == true) {
										return false;
									}
								}
							}
						}
					}
				}
			}
			return true;
		}
		return false;
	}

	public boolean validSquare(String location) {
		int x = location.charAt(0) - 'a';
		int y = location.charAt(1) - '1';
		if (x > 7 || y > 7 || x < 00 || y < 0) {
			return false;
		}
		return true;
	}

	public GridSquare findSquare(String location) {
		int x = location.charAt(0) - 'a';
		int y = 7 - (location.charAt(1) - '1');
		return getSquare(x, y);
	}

	public String boardData() {
		String status = "";
		GridSquare square;
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				square = board[x][y];
				if (square.getPowerUp() == true) {
					status += "U, ";
				} else if (square.getPiece() == null) {
					status += "E, ";
				} else {
					status += square.getPiece().getTeam() + square.getPiece().toString() + ", ";
				}
			}
		}
		return status;
	}
}
