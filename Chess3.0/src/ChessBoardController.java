
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.BufferedReader;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class ChessBoardController {

	@FXML
	private Label TitleLabel;

	@FXML
	private GridPane BoardGrid;

	@FXML
	private TextField messageTextField;

	private Board board = new Board();
	private String touch;
	private String player = "W";
	private String destination = "A";
	private int powerUpTimer = (int) (Math.random() * 10) + 5;

	public void initialize() {
		printBoard();
		messageTextField.setText("Welcome to Power-Up Chess. Player white, please begin.");
	}

	@FXML
	void SquareClicked(MouseEvent event) {
		messageTextField.clear();
		if (board.checkMate("W") == false && board.checkMate("B") == false) {
			if (destination != null) {
				touch = (((ImageView) event.getSource()).getId());
				if (board.validSquare(touch) == false) {
					messageTextField.setText("Invalid coordinates; that space does not exist.");
					System.out.println("Invalid coordinates; that space does not exist.");
				} else {
					if (board.findSquare(touch).getOccupied() == true) {
						if (board.findSquare(touch).getPiece().getTeam() != player) {
							messageTextField.setText("That piece isn't yours! Please select a valid piece to continue.");
							System.out.println("That piece isn't yours! Please select a valid piece to continue.");
						} else {
							destination = null;
						}
					} else {
						messageTextField.setText("That space is empty. Please select a valid piece to continue.");
						System.out.println("That space is empty. Please select a valid piece to continue.");
					}
				}
			} else {
				destination = (((ImageView) event.getSource()).getId());
				if (board.validSquare(destination) == false) {
					messageTextField.setText("Invalid coordinates; that space does not exist.");
					System.out.println("Invalid coordinates; that space does not exist.");
				} else {
					int x = destination.charAt(0) - 'a';
					int y = 8 - Character.getNumericValue(destination.charAt(1));
					if (board.findSquare(touch).getPiece().testMove(board, x, y) == true) {
						board.findSquare(touch).getPiece().move(board, x, y);
						if (player == "W") {
							player = "B";
							TitleLabel.setText("Black's turn");
							powerUpTimer--;
						} else {
							player = "W";
							TitleLabel.setText("White's turn");
							powerUpTimer--;
						}
						// System.out.println(destination);
					} else {
						messageTextField.setText("Illegal or invalid move. Try again!");
						System.out.println("Illegal or invalid move. Try again!");
					}
				}
			}
			printBoard();
			if (board.checkMate("B") == true) {
				TitleLabel.setText("Checkmate. White wins!");
				System.out.println("Checkmate. White wins!");
			} else if (board.checkMate("W") == true) {
				TitleLabel.setText("Checkmate. Black wins!");
				System.out.println("Checkmate. Black wins!");
			}
		}
	}

	public void printBoard() {

		Node node;
		if (powerUpTimer == 0) {
			int temp1;
			int temp2;
			while (true) {
				temp1 = (int) (Math.random() * 7);
				temp2 = (int) (Math.random() * 7);
				if (board.getSquare(temp1, temp2).getOccupied() == false) {
					board.getSquare(temp1, temp2).setPowerUp(true);
					powerUpTimer = (int) (Math.random() * 10) + 5;
					break;
				}
			}
		}
		for (int i = 0; i < 8; i++) {
			for (int x = 0; x < 8; x++) {

				node = BoardGrid.lookup("#" + ((char) (i + 'a')) + "" + (x + 1));

				String square = node.getId();

				if (board.findSquare(square).getOccupied() == true) {
					if (board.findSquare(square).getPiece().getTeam() == "B") {
						switch (board.findSquare(square).getPiece().toString()) {
						case "pawn":
							((ImageView) node).setImage(new Image("Images/Chess_pdt60.png"));
							break;
						case "rook":
							((ImageView) node).setImage(new Image("Images/Chess_rdt60.png"));
							break;
						case "knight":
							((ImageView) node).setImage(new Image("Images/Chess_ndt60.png"));
							break;
						case "bishop":
							((ImageView) node).setImage(new Image("Images/Chess_bdt60.png"));
							break;
						case "king":
							((ImageView) node).setImage(new Image("Images/Chess_kdt60.png"));
							break;
						case "queen":
							((ImageView) node).setImage(new Image("Images/Chess_qdt60.png"));
							break;
						default:
							;
						}
					} else {
						switch (board.findSquare(square).getPiece().toString()) {
						case "pawn":
							((ImageView) node).setImage(new Image("Images/Chess_plt60.png"));
							break;
						case "rook":
							((ImageView) node).setImage(new Image("Images/Chess_rlt60.png"));
							break;
						case "knight":
							((ImageView) node).setImage(new Image("Images/Chess_nlt60.png"));
							break;
						case "bishop":
							((ImageView) node).setImage(new Image("Images/Chess_blt60.png"));
							break;
						case "king":
							((ImageView) node).setImage(new Image("Images/Chess_klt60.png"));
							break;
						case "queen":
							((ImageView) node).setImage(new Image("Images/Chess_qlt60.png"));
							break;
						default:
							;
						}
					}
				} else if (board.findSquare(square).getPowerUp() == true) {
					((ImageView) node).setImage(new Image("Images/PowerUp.png"));
				} else {
					((ImageView) node).setImage(null);
				}
				if (board.findSquare(square).getPowerUp() == true && board.findSquare(square).getPiece() != null) {
					activatePowerUp(square);
				}
			}
		}
	}

	@FXML
	void resetBoardPressed(ActionEvent event) {
		player = "W";
		board = new Board();
		printBoard();
		messageTextField.setText("A new game has begun, enjoy!");
		TitleLabel.setText("White's turn");
	}

	void saveGame() {
		// attempt 1
		try {
			FileOutputStream file = new FileOutputStream("chess.txt");
			ObjectOutputStream object = new ObjectOutputStream(file);
			object.writeObject(board.boardData() + player);
			object.close();
		} catch (IOException ioException) {
			System.err.println("Error opening file. Terminating.");
		}
	}
	
	@FXML
	void saveGamePressed(ActionEvent event) {
		saveGame();
		messageTextField.setText("Game saved, successfully.");
	}

	void loadGame() {
		int count = 0;
		try {
			BufferedReader file = new BufferedReader(new FileReader("chess.txt"));
			String boardString = file.readLine().substring(7);
			String[] boardArray = boardString.split(", ");
			for (int y = 0; y < 8; y++) {
				for (int x = 0; x < 8; x++) {
					if (boardArray[count] == "E") {
						board.getSquare(x, y).setPiece(null);
						board.getSquare(x, y).setOccupied(false);
					} else {
						switch (boardArray[count]) {
						case "Brook":
							board.getSquare(x, y).addPiece(new Rook("B", board.getSquare(x, y).getLocation()));
							break;
						case "Bknight":
							board.getSquare(x, y).addPiece(new Knight("B", board.getSquare(x, y).getLocation()));
							break;
						case "Bbishop":
							board.getSquare(x, y).addPiece(new Bishop("B", board.getSquare(x, y).getLocation()));
							break;
						case "Bpawn":
							board.getSquare(x, y).addPiece(new Pawn("B", board.getSquare(x, y).getLocation()));
							break;
						case "Bqueen":
							board.getSquare(x, y).addPiece(new Queen("B", board.getSquare(x, y).getLocation()));
							break;
						case "Bking":
							board.getSquare(x, y).addPiece(new King("B", board.getSquare(x, y).getLocation()));
							break;
						case "Wrook":
							board.getSquare(x, y).addPiece(new Rook("W", board.getSquare(x, y).getLocation()));
							break;
						case "Wknight":
							board.getSquare(x, y).addPiece(new Knight("W", board.getSquare(x, y).getLocation()));
							break;
						case "Wbishop":
							board.getSquare(x, y).addPiece(new Bishop("W", board.getSquare(x, y).getLocation()));
							break;
						case "Wpawn":
							board.getSquare(x, y).addPiece(new Pawn("W", board.getSquare(x, y).getLocation()));
							break;
						case "Wqueen":
							board.getSquare(x, y).addPiece(new Queen("W", board.getSquare(x, y).getLocation()));
							break;
						case "Wking":
							board.getSquare(x, y).addPiece(new King("W", board.getSquare(x, y).getLocation()));
							break;
						case "U":
							board.getSquare(x, y).setPiece(null);
							board.getSquare(x, y).setOccupied(false);
							board.getSquare(x, y).setPowerUp(true);
							break;
						default:
							board.getSquare(x, y).setPiece(null);
							board.getSquare(x, y).setOccupied(false);
							break;
						}
					}
					count++;
				}
			}
			switch (boardArray[count]) {
			case "B":
				player = "B";
				TitleLabel.setText("Black's turn");
				break;
			case "W":
				player = "W";
				TitleLabel.setText("White's turn");
				break;
			}
			System.out.println(boardString);
			System.out.println(boardArray[count]);
			file.close();
			printBoard();
		} catch (IOException ioException) {
			System.err.println("Error opening file. Terminating.");
		}
	}
	
	@FXML
	void loadGamePressed(ActionEvent event) {
		loadGame();
		messageTextField.setText("Game loaded.");
	}

	public void activatePowerUp(String coordinate) {
		String team = board.findSquare(coordinate).getPiece().getTeam();
		String piece = board.findSquare(coordinate).getPiece().toString();
		board.findSquare(coordinate).setPowerUp(false);
		int power = (int) (Math.random() * 4);
		if (power == 0) {
			while (true) {
				int temp1 = (int) (Math.random() * 7);
				int temp2 = (int) (Math.random() * 7);
				if (board.getSquare(temp1, temp2).getPiece() != null) {
					if (board.getSquare(temp1, temp2).getPiece().getTeam() == team
							&& board.getSquare(temp1, temp2).getPiece().toString() == "pawn") {
						int roll = (int) (Math.random() * 3);
						coordinate = board.getSquare(temp1, temp2).getLocation();
						switch (roll) {
						case 0:
							board.getSquare(temp1, temp2).setPiece(new Knight(team, coordinate));
							break;
						case 1:
							board.getSquare(temp1, temp2).setPiece(new Rook(team, coordinate));
							break;
						case 2:
							board.getSquare(temp1, temp2).setPiece(new Bishop(team, coordinate));
							break;
						case 3:
							board.getSquare(temp1, temp2).setPiece(new Queen(team, coordinate));
							break;
						}
						messageTextField.setText("Random promotion!");
						break;
					}
				}
			}
		} else if (power == 1) {
			player = team;
			touch = coordinate;
			destination = null;
			if (player == "W") {
				TitleLabel.setText("White's turn");
			} else {
				TitleLabel.setText("Black's turn");
			}
			messageTextField.setText("Move that piece again!");
		} else if (power == 2) {
			messageTextField.setText("Choose which piece your opponent moves!");
		} else if (power == 3) {
			messageTextField.setText("Pawn Guarde!");
			for (int i = 0; i < 8; i++) {
				if (board.getSquare(i, 6).getPiece() == null) {
					board.getSquare(i, 6).addPiece(new Pawn("W", board.getSquare(i, 6).getLocation()));
				}
				if (board.getSquare(i, 1).getPiece() == null) {
					board.getSquare(i, 1).addPiece(new Pawn("B", board.getSquare(i, 1).getLocation()));
				}
			}
		}
		printBoard();
	}

}
