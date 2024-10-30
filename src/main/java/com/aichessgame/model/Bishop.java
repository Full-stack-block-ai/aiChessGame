package com.aichessgame.model;
import com.aichessgame.utils.Color;
import com.aichessgame.utils.Position;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a Bishop piece.
 */
public class Bishop extends Piece {

    /**
     * Constructor for the Bishop class.
     *
     * @param color    The color of the bishop.
     * @param position The initial position of the bishop.
     */
    public Bishop(Color color, Position position) {
        super(color, position);
    }

    @Override
    public List<Position> getPossibleMoves(Board board) {
        List<Position> possibleMoves = new ArrayList<>();

        int currentRow = position.getRow();
        int currentColumn = position.getColumn();

        // Directions the bishop can move: diagonals
        int[] rowDirections = {-1, -1, 1, 1};
        int[] columnDirections = {-1, 1, -1, 1};

        // Iterate over each direction
        for (int i = 0; i < 4; i++) {
            int rowDirection = rowDirections[i];
            int columnDirection = columnDirections[i];
            int newRow = currentRow + rowDirection;
            int newColumn = currentColumn + columnDirection;

            // Continue moving in the current direction until blocked
            while (Position.isValidCoordinate(newRow) && Position.isValidCoordinate(newColumn)) {
                Position newPosition = new Position(newRow, newColumn);

                if (board.isPositionEmpty(newPosition)) {
                    possibleMoves.add(newPosition);
                } else {
                    if (board.isPositionOccupiedByOpponent(newPosition, color)) {
                        possibleMoves.add(newPosition); // Can capture opponent's piece
                    }
                    break; // Blocked by any piece
                }

                newRow += rowDirection;
                newColumn += columnDirection;
            }
        }

        return possibleMoves;
    }

    @Override
    public String getType() {
        return "Bishop";
    }
}
