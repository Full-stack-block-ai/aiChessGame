
package com.aichessgame.main;

import com.aichessgame.controller.GameController;
import com.aichessgame.model.Board;
import com.aichessgame.model.Piece;
import com.aichessgame.utils.Color;
import com.aichessgame.utils.Position;

import java.util.Scanner;

/**
 * Main class for manually testing the chess game.
 */
public class Main {

    public static void main(String[] args) {
        // Initialize the game controller
        GameController gameController = new GameController();
        Board board = gameController.getBoard();

        // Create a scanner for user input
        Scanner scanner = new Scanner(System.in);

        // Game loop
        while (true) {
            // Display the board
            displayBoard(board);

            // Check game state
            switch (gameController.getGameState()) {
                case CHECKMATE:
                    System.out.println("Checkmate! " + gameController.getCurrentPlayer().getColor().opposite() + " wins.");
                    return;
                case STALEMATE:
                    System.out.println("Stalemate! It's a draw.");
                    return;
                case CHECK:
                    System.out.println(gameController.getCurrentPlayer().getColor().opposite() + " is in check!");
                    break;
                default:
                    break;
            }

            // Prompt the current player for a move
            System.out.println(gameController.getCurrentPlayer().getColor() + "'s turn.");
            System.out.print("Enter your move (e.g., e2e4 or type 'exit' to quit): ");
            String moveInput = scanner.nextLine().trim();

            if (moveInput.equalsIgnoreCase("exit")) {
                System.out.println("Game terminated by user.");
                break;
            }

            if (moveInput.length() != 4) {
                System.out.println("Invalid input format. Please enter moves like e2e4.");
                continue;
            }

            Position fromPosition = parsePosition(moveInput.substring(0, 2));
            Position toPosition = parsePosition(moveInput.substring(2, 4));

            if (fromPosition == null || toPosition == null) {
                System.out.println("Invalid positions entered.");
                continue;
            }

            // Attempt to process the move
            boolean moveSuccessful = gameController.processMove(fromPosition, toPosition);
            if (!moveSuccessful) {
                System.out.println("Invalid move. Please try again.");
            }
        }

        scanner.close();
    }

    /**
     * Displays the board in the console.
     *
     * @param board The current state of the board.
     */
    private static void displayBoard(Board board) {
        String[][] boardRepresentation = new String[8][8];

        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                boardRepresentation[row][column] = ".";
            }
        }

        for (Position position : board.getBoardMap().keySet()) {
            Piece piece = board.getPieceAt(position);
            String pieceSymbol = getPieceSymbol(piece);
            boardRepresentation[7 - position.getRow()][position.getColumn()] = pieceSymbol;
        }

        System.out.println("  a b c d e f g h");
        for (int row = 0; row < 8; row++) {
            System.out.print((8 - row) + " ");
            for (int column = 0; column < 8; column++) {
                System.out.print(boardRepresentation[row][column] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Gets a symbolic representation of a piece.
     *
     * @param piece The piece to represent.
     * @return A string symbol for the piece.
     */
    private static String getPieceSymbol(Piece piece) {
        String symbol;
        String color = piece.getColor() == Color.WHITE ? "w" : "b";

        switch (piece.getType()) {
            case "Pawn" -> symbol = color + "P";
            case "Rook" -> symbol = color + "R";
            case "Knight" -> symbol = color + "N";
            case "Bishop" -> symbol = color + "B";
            case "Queen" -> symbol = color + "Q";
            case "King" -> symbol = color + "K";
            default -> symbol = "??";
        }

        return symbol;
    }

    /**
     * Parses a position from a string in algebraic notation.
     *
     * @param positionStr The string representing the position (e.g., "e2").
     * @return The Position object, or null if invalid.
     */
    private static Position parsePosition(String positionStr) {
        if (positionStr.length() != 2) {
            return null;
        }

        char fileChar = positionStr.charAt(0);
        char rankChar = positionStr.charAt(1);

        int column = fileChar - 'a';
        int row = rankChar - '1';

        if (!Position.isValidCoordinate(row) || !Position.isValidCoordinate(column)) {
            return null;
        }

        return new Position(row, column);
    }
}
