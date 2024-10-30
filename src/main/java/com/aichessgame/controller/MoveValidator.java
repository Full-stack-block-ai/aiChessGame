package com.aichessgame.controller;
import com.aichessgame.model.Board;
import com.aichessgame.model.Piece;
import com.aichessgame.utils.Color;
import com.aichessgame.utils.Position;
import java.util.List;

/**
 * Class responsible for validating moves according to chess rules.
 */
public class MoveValidator {

    /**
     * Checks if a move is valid for a given piece.
     *
     * @param piece       The piece to move.
     * @param toPosition  The destination position.
     * @param board       The current state of the board.
     * @return True if the move is valid, false otherwise.
     */
    public boolean isMoveValid(Piece piece, Position toPosition, Board board) {
        // Get all possible moves for the piece
        List<Position> possibleMoves = piece.getPossibleMoves(board);

        // Check if the destination position is among the possible moves
        if (!possibleMoves.contains(toPosition)) {
            return false;
        }

        // Simulate the move to check for checks
        Board simulatedBoard = simulateMove(piece.getPosition(), toPosition, board);
        if (simulatedBoard.isKingInCheck(piece.getColor())) {
            // The move would leave the king in check
            return false;
        }

        return true;
    }

    /**
     * Simulates the move on a copy of the board to check for checks.
     *
     * @param fromPosition The starting position.
     * @param toPosition   The destination position.
     * @param board        The current state of the board.
     * @return A new Board object representing the state after the move.
     */
    private Board simulateMove(Position fromPosition, Position toPosition, Board board) {
        Board simulatedBoard = new Board(board); // Implement a copy constructor in Board
        simulatedBoard.movePiece(fromPosition, toPosition);
        return simulatedBoard;
    }

    /**
     * Checks if a player is in checkmate.
     *
     * @param color The color of the player.
     * @param board The current state of the board.
     * @return True if in checkmate, false otherwise.
     */
    public boolean isCheckmate(Color color, Board board) {
        // If the king is in check and the player has no legal moves, it's checkmate
        if (!board.isKingInCheck(color)) {
            return false;
        }
        return !hasLegalMoves(color, board);
    }

    /**
     * Checks if a player is in stalemate.
     *
     * @param color The color of the player.
     * @param board The current state of the board.
     * @return True if in stalemate, false otherwise.
     */
    public boolean isStalemate(Color color, Board board) {
        // If the king is not in check but the player has no legal moves, it's stalemate
        if (board.isKingInCheck(color)) {
            return false;
        }
        return !hasLegalMoves(color, board);
    }

    /**
     * Checks if a player has any legal moves.
     *
     * @param color The color of the player.
     * @param board The current state of the board.
     * @return True if the player has legal moves, false otherwise.
     */
    private boolean hasLegalMoves(Color color, Board board) {
        for (Piece piece : board.getBoardMap().values()) {
            if (piece.getColor() == color) {
                List<Position> moves = piece.getPossibleMoves(board);
                for (Position move : moves) {
                    boolean moveIsValid = isMoveValid(piece, move, board);

                    // Debugging statement
                    System.out.println("Checking move for " + piece.getType() + " at " + piece.getPosition() + " to " + move + ": " + (moveIsValid ? "Valid" : "Invalid"));
                    if (isMoveValid(piece, move, board)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
