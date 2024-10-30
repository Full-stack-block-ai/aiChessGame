package com.aichessgame.model;
import com.aichessgame.utils.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a Pawn piece.
 */
public class Pawn extends Piece {

    /**
     * Constructor for the Pawn class.
     *
     * @param color    The color of the pawn.
     * @param position The initial position of the pawn.
     */
    public Pawn(Color color, Position position) {
        super(color, position);
    }

    @Override
    public List<Position> getPossibleMoves(Board board) {
        List<Position> possibleMoves = new ArrayList<>();
        int direction = (color == Color.WHITE) ? 1 : -1; // White moves up, Black moves down

        int currentRow = position.getRow();
        int currentColumn = position.getColumn();

        // Forward move by one square
        int nextRow = currentRow + direction;
        Position forwardPosition = new Position(nextRow, currentColumn);

        if (Position.isValidCoordinate(nextRow) && board.isPositionEmpty(forwardPosition)) {
            possibleMoves.add(forwardPosition);

            // Initial two-square move
            boolean isStartingRow = (color == Color.WHITE && currentRow == 1) || (color == Color.BLACK && currentRow == 6);
            int twoStepsRow = currentRow + 2 * direction;
            Position twoStepsForward = new Position(twoStepsRow, currentColumn);

            if (isStartingRow && board.isPositionEmpty(twoStepsForward)) {
                possibleMoves.add(twoStepsForward);
            }
        }

        // Diagonal captures
        int[] columnOffsets = {-1, 1}; // Left and right diagonals
        for (int offset : columnOffsets) {
            int diagonalRow = currentRow + direction;
            int diagonalColumn = currentColumn + offset;
            if (Position.isValidCoordinate(diagonalRow) && Position.isValidCoordinate(diagonalColumn)) {
                Position diagonalPosition = new Position(diagonalRow, diagonalColumn);
                if (board.isPositionOccupiedByOpponent(diagonalPosition, color)) {
                    possibleMoves.add(diagonalPosition);
                }
                // En passant
                if (board.isEnPassantPossible(diagonalPosition, color)) {
                    possibleMoves.add(diagonalPosition);
                }
            }
        }

        // Note: Promotion logic should be handled after the move is made.

        return possibleMoves;
    }

    @Override
    public String getType() {
        return "Pawn";
    }
}
