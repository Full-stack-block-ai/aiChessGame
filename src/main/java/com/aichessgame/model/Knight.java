package com.aichessgame.model;

import com.aichessgame.utils.Color;
import com.aichessgame.utils.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a Knight piece.
 */
public class Knight extends Piece {

    /**
     * Constructor for the Knight class.
     *
     * @param color    The color of the knight.
     * @param position The initial position of the knight.
     */
    public Knight(Color color, Position position) {
        super(color, position);
    }

    @Override
    public List<Position> getPossibleMoves(Board board) {
        List<Position> possibleMoves = new ArrayList<>();

        int currentRow = position.getRow();
        int currentColumn = position.getColumn();

        // All possible moves a knight can make
        int[] rowOffsets = {-2, -2, -1, -1, 1, 1, 2, 2};
        int[] columnOffsets = {-1, 1, -2, 2, -2, 2, -1, 1};

        for (int i = 0; i < 8; i++) {
            int newRow = currentRow + rowOffsets[i];
            int newColumn = currentColumn + columnOffsets[i];

            if (Position.isValidCoordinate(newRow) && Position.isValidCoordinate(newColumn)) {
                Position newPosition = new Position(newRow, newColumn);

                if (board.isPositionEmpty(newPosition) || board.isPositionOccupiedByOpponent(newPosition, color)) {
                    possibleMoves.add(newPosition);
                }
            }
        }

        return possibleMoves;
    }

    @Override
    public String getType() {
        return "Knight";
    }
}
