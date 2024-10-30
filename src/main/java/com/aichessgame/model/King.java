package com.aichessgame.model;
import com.aichessgame.utils.Color;
import com.aichessgame.utils.Position;
import java.util.ArrayList;
import java.util.List;


/**
 * Class representing a King piece.
 */
public class King extends Piece {

    /**
     * Constructor for the King class.
     *
     * @param color    The color of the king.
     * @param position The initial position of the king.
     */
    public King(Color color, Position position) {
        super(color, position);
    }



    @Override
    public List<Position> getPossibleMoves(Board board) {
        List<Position> possibleMoves = new ArrayList<>();

        int currentRow = position.getRow();
        int currentColumn = position.getColumn();

        // All possible directions the king can move
        int[] rowOffsets = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] columnOffsets = {-1, 0, 1, -1, 1, -1, 0, 1};

        // Loop through each possible direction
        for (int i = 0; i < 8; i++) {
            int newRow = currentRow + rowOffsets[i];
            int newColumn = currentColumn + columnOffsets[i];

            // Check if the new position is within bounds
            if (Position.isValidCoordinate(newRow) && Position.isValidCoordinate(newColumn)) {
                Position newPosition = new Position(newRow, newColumn);

                // Check if the position is either empty or occupied by an opponent's piece
                if (board.isPositionEmpty(newPosition) || board.isPositionOccupiedByOpponent(newPosition, color)) {
                    possibleMoves.add(newPosition);
                }
            }
        }

        return possibleMoves;
    }

    public List<Position> getPossibleMoves(Board board, boolean includeCastling) {
        List<Position> possibleMoves = new ArrayList<>();

        int currentRow = position.getRow();
        int currentColumn = position.getColumn();

        // All possible directions the king can move
        int[] rowOffsets = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] columnOffsets = {-1, 0, 1, -1, 1, -1, 0, 1};

        // Single square moves
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

        // Castling
        if (includeCastling && !hasMoved && !board.isKingInCheck(color)) {
            // Kingside castling
            if (canCastle(board, true)) {
                Position castlingPosition = new Position(currentRow, currentColumn + 2);
                possibleMoves.add(castlingPosition);
            }
            // Queenside castling
            if (canCastle(board, false)) {
                Position castlingPosition = new Position(currentRow, currentColumn - 2);
                possibleMoves.add(castlingPosition);
            }
        }

        return possibleMoves;
    }



    /**
     * Checks if castling is possible.
     *
     * @param board         The current state of the board.
     * @param isKingside    True for kingside castling, false for queenside.
     * @return True if castling is possible, false otherwise.
     */
    private boolean canCastle(Board board, boolean isKingside) {
        int row = position.getRow();
        int kingColumn = position.getColumn();
        int direction = isKingside ? 1 : -1;

        // Check the spaces between the king and the rook
        for (int offset = 1; offset <= (isKingside ? 2 : 3); offset++) {
            int column = kingColumn + offset * direction;
            Position positionToCheck = new Position(row, column);
            if (!board.isPositionEmpty(positionToCheck) || board.isPositionUnderAttack(positionToCheck, color.opposite())) {
                return false;
            }
        }

        // Check that the rook is in the correct position and hasn't moved
        int rookColumn = isKingside ? 7 : 0;
        Position rookPosition = new Position(row, rookColumn);
        Piece rook = board.getPieceAt(rookPosition);

        return rook instanceof Rook && rook.getColor() == color && !rook.hasMoved();
    }

    @Override
    public String getType() {
        return "King";
    }
}
