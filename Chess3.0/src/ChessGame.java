
import java.util.Scanner;

public class ChessGame {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		Board board = new Board();
		board.printBoard();
		String player = "W";
		String touch;
		String move;
		//Current interface seems to try and make a lot of moves without input.
		//Also need to fix movement method; boolean return type just isn't working out.
		while (board.checkMate("B") == false && board.checkMate("W") == false) {
			if (player == "W") {
				System.out.println("White's turn.");
			}else {
				System.out.println("Black's turn.");
			}
			System.out.println("Select piece that you want to move: ");
			while (true) {
				touch = input.nextLine();
				if (board.validSquare(touch) == false) {
					System.out.println("Invalid coordinates; that space does not exist.");
				}else {
					if (board.findSquare(touch).getOccupied() == true) {
						if (board.findSquare(touch).getPiece().getTeam() != player) {
							System.out.println("That piece isn't yours!");
						}else {
							break;
						}
					}else {
						System.out.println("That space is empty.");
					}
				}
			}
			System.out.println("Enter destination: ");
			boolean legal = false;
			while (legal == false) {
				String destination = input.nextLine();
				if (board.validSquare(destination) == false) {
					System.out.println("Invalid coordinates; that space does not exist.");
				}else {
					int x = destination.charAt(0) - 'a';
					int y = 8 - Character.getNumericValue(destination.charAt(1));
					if (board.findSquare(touch).getPiece().testMove(board, x, y) == true) {
						legal = true;
						board.findSquare(touch).getPiece().move(board, x, y);
					}
				}
			}
			if (player == "W") {
				player = "B";
			}else {
				player = "W";
			}
			board.printBoard();
			//System.out.println("White is in check:"+board.getWhiteKing().inCheck(board));
			//System.out.println("Black is in check:"+board.getBlackKing().inCheck(board));
		}
		if (board.checkMate("B") == true) {
			System.out.println("White wins!");
		}else if (board.checkMate("W") == true) {
			System.out.println("Black wins!");
		}
		/*board.getSquare(0, 5).getPiece().move(board, 1, 6);//I added these commands here to test piece movement; detecting path obstructions,
		board.getSquare(1, 6).getPiece().move(board, 0, 7);//occupied spaces, and the ability to take enemy pieces. They can also serve as a
		board.getSquare(0, 7).getPiece().move(board, 1, 7);//template for what to type in order to move pieces with an integrated UI.
		board.getSquare(1, 7).getPiece().move(board, 2, 6);
		board.getSquare(2, 6).getPiece().move(board, 3, 6);
		board.getSquare(3, 6).getPiece().move(board, 2, 5);
		board.getSquare(2, 7).getPiece().move(board, 3, 6);
		board.getSquare(0, 0).getPiece().move(board, 0, 6);
		board.getSquare(0, 6).getPiece().move(board, 0, 7);
		board.getSquare(0, 7).getPiece().move(board, 2, 7);*/
		//board.getSquare(0, 1).getPiece().move(board, 0, 2);					   //Add or remove them at your leisure.
		//System.out.println(board.getSquare(0, 5).getOccupied());
		board.printBoard();
	}
	
}

