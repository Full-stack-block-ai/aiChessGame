package com.aichessgame.model;
import com.aichessgame.utils.Color;
import com.aichessgame.utils.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class representing the chessboard.
 */
public class Board {
    private final Map<Position, Piece> boardMap; // Maps positions to pieces
    private Position enPassantTarget;            // The position where en passant is possible
    private Map<Color, Position> kingPositions;  // Tracks the kings' positions

    /**
     * Constructor for the Board class.
     */
    public Board() {
        boardMap = new HashMap<>();
        kingPositions = new HashMap<>();
        initializeBoard();
    }

    /**
     * Copy constructor for the Board class.
     *
     * @param other The Board instance to copy.
     */
    public Board(Board other) {
        this.boardMap = new HashMap<>();
        for (Map.Entry<Position, Piece> entry : other.boardMap.entrySet()) {
            Position position = entry.getKey();
            Piece piece = entry.getValue();

            // Create a copy of the piece
            Piece pieceCopy = copyPiece(piece);

            this.boardMap.put(position, pieceCopy);
        }

        // Copy enPassantTarget
        this.enPassantTarget = other.enPassantTarget != null ? new Position(other.enPassantTarget.getRow(), other.enPassantTarget.getColumn()) : null;

        // Copy kingPositions
        this.kingPositions = new HashMap<>();
        for (Map.Entry<Color, Position> entry : other.kingPositions.entrySet()) {
            Color color = entry.getKey();
            Position position = entry.getValue();
            this.kingPositions.put(color, new Position(position.getRow(), position.getColumn()));
        }
    }

    /**
     * Helper method to create a copy of a piece.
     *
     * @param piece The piece to copy.
     * @return A new Piece instance with the same properties.
     */
    private Piece copyPiece(Piece piece) {
        Position positionCopy = new Position(piece.getPosition().getRow(), piece.getPosition().getColumn());
        Piece pieceCopy;

        if (piece instanceof Pawn) {
            pieceCopy = new Pawn(piece.getColor(), positionCopy);
        } else if (piece instanceof Rook) {
            pieceCopy = new Rook(piece.getColor(), positionCopy);
        } else if (piece instanceof Knight) {
            pieceCopy = new Knight(piece.getColor(), positionCopy);
        } else if (piece instanceof Bishop) {
            pieceCopy = new Bishop(piece.getColor(), positionCopy);
        } else if (piece instanceof Queen) {
            pieceCopy = new Queen(piece.getColor(), positionCopy);
        } else if (piece instanceof King) {
            pieceCopy = new King(piece.getColor(), positionCopy);
        } else {
            throw new IllegalArgumentException("Unknown piece type");
        }

        // Set hasMoved flag
        if (piece.hasMoved()) {
            pieceCopy.hasMoved = true;
        }

        return pieceCopy;
    }

    /**
     * Initializes the board with pieces at their starting positions.
     */
    private void initializeBoard() {
        // Place pawns
        for (int column = 0; column < 8; column++) {
            boardMap.put(new Position(1, column), new Pawn(Color.WHITE, new Position(1, column)));
            boardMap.put(new Position(6, column), new Pawn(Color.BLACK, new Position(6, column)));
        }

        // Place other pieces for White
        placeBackRow(Color.WHITE, 0);

        // Place other pieces for Black
        placeBackRow(Color.BLACK, 7);
    }

    /**
     * Places the back row pieces for a given color.
     *
     * @param color The color of the pieces.
     * @param row   The row to place the pieces on.
     */
    private void placeBackRow(Color color, int row) {
        boardMap.put(new Position(row, 0), new Rook(color, new Position(row, 0)));
        boardMap.put(new Position(row, 1), new Knight(color, new Position(row, 1)));
        boardMap.put(new Position(row, 2), new Bishop(color, new Position(row, 2)));
        boardMap.put(new Position(row, 3), new Queen(color, new Position(row, 3)));
        King king = new King(color, new Position(row, 4));
        boardMap.put(new Position(row, 4), king);
        kingPositions.put(color, new Position(row, 4));
        boardMap.put(new Position(row, 5), new Bishop(color, new Position(row, 5)));
        boardMap.put(new Position(row, 6), new Knight(color, new Position(row, 6)));
        boardMap.put(new Position(row, 7), new Rook(color, new Position(row, 7)));
    }

    /**
     * Gets the piece at a specific position.
     *
     * @param position The position to check.
     * @return The piece at the position, or null if empty.
     */
    public Piece getPieceAt(Position position) {
        return boardMap.get(position);
    }

    /**
     * Moves a piece from one position to another.
     *
     * @param fromPosition The starting position.
     * @param toPosition   The ending position.
     */
    public void movePiece(Position fromPosition, Position toPosition) {
        Piece piece = boardMap.get(fromPosition);
        if (piece != null) {
            // Handle special moves
            if (piece instanceof Pawn) {
                handlePawnMove((Pawn) piece, fromPosition, toPosition);
            } else if (piece instanceof King) {
                handleCastling((King) piece, fromPosition, toPosition);
                // Update king's position
                kingPositions.put(piece.getColor(), toPosition);
            }

            piece.setPosition(toPosition);
            boardMap.remove(fromPosition);
            boardMap.put(toPosition, piece);

            // Reset en passant target unless a pawn moved two squares
            if (!(piece instanceof Pawn && Math.abs(toPosition.getRow() - fromPosition.getRow()) == 2)) {
                enPassantTarget = null;
            }
        }
    }

    /**
     * Handles pawn-specific movement logic, including en passant.
     *
     * @param pawn          The pawn being moved.
     * @param fromPosition  The starting position.
     * @param toPosition    The ending position.
     */
    private void handlePawnMove(Pawn pawn, Position fromPosition, Position toPosition) {
        int rowDifference = toPosition.getRow() - fromPosition.getRow();
        // Set en passant target if pawn moves two squares
        if (Math.abs(rowDifference) == 2) {
            enPassantTarget = new Position((fromPosition.getRow() + toPosition.getRow()) / 2, fromPosition.getColumn());
        }

        // Handle en passant capture
        if (toPosition.equals(enPassantTarget)) {
            int capturedPawnRow = fromPosition.getRow();
            int capturedPawnColumn = toPosition.getColumn();
            Position capturedPawnPosition = new Position(capturedPawnRow, capturedPawnColumn);
            boardMap.remove(capturedPawnPosition);
        }

        // Handle pawn promotion (for simplicity, promote to Queen)
        if ((pawn.getColor() == Color.WHITE && toPosition.getRow() == 7) ||
                (pawn.getColor() == Color.BLACK && toPosition.getRow() == 0)) {
            boardMap.put(toPosition, new Queen(pawn.getColor(), toPosition));
        }
    }

    /**
     * Handles castling logic when the king moves.
     *
     * @param king          The king being moved.
     * @param fromPosition  The starting position.
     * @param toPosition    The ending position.
     */
    private void handleCastling(King king, Position fromPosition, Position toPosition) {
        int columnDifference = toPosition.getColumn() - fromPosition.getColumn();
        if (Math.abs(columnDifference) == 2) {
            // Castling move
            int rookFromColumn = columnDifference > 0 ? 7 : 0;
            int rookToColumn = columnDifference > 0 ? fromPosition.getColumn() + 1 : fromPosition.getColumn() - 1;

            Position rookFromPosition = new Position(fromPosition.getRow(), rookFromColumn);
            Position rookToPosition = new Position(fromPosition.getRow(), rookToColumn);

            Piece rook = boardMap.get(rookFromPosition);
            if (rook instanceof Rook) {
                rook.setPosition(rookToPosition);
                boardMap.remove(rookFromPosition);
                boardMap.put(rookToPosition, rook);
            }
        }
    }

    /**
     * Checks if a position is empty.
     *
     * @param position The position to check.
     * @return True if the position is empty, false otherwise.
     */
    public boolean isPositionEmpty(Position position) {
        return !boardMap.containsKey(position);
    }

    /**
     * Checks if a position is occupied by an opponent's piece.
     *
     * @param position The position to check.
     * @param color    The color of the current player.
     * @return True if occupied by an opponent, false otherwise.
     */
    public boolean isPositionOccupiedByOpponent(Position position, Color color) {
        Piece piece = boardMap.get(position);
        return piece != null && piece.getColor() != color;
    }

    /**
     * Checks if en passant is possible at a given position.
     *
     * @param position The position to check.
     * @param color    The color of the pawn attempting en passant.
     * @return True if en passant is possible, false otherwise.
     */
    public boolean isEnPassantPossible(Position position, Color color) {
        return position.equals(enPassantTarget) && enPassantTarget != null;
    }

    /**
     * Checks if the king of a given color is in check.
     *
     * @param color The color of the king.
     * @return True if the king is in check, false otherwise.
     */
    public boolean isKingInCheck(Color color) {
        Position kingPosition = kingPositions.get(color);
        return isPositionUnderAttack(kingPosition, color.opposite());
    }

    /**
     * Checks if a position is under attack by any pieces of a given color.
     *
     * @param position       The position to check.
     * @param attackingColor The color of the attacking pieces.
     * @return True if under attack, false otherwise.
     */
    public boolean isPositionUnderAttack(Position position, Color attackingColor) {
        for (Piece piece : boardMap.values()) {
            if (piece.getColor() == attackingColor) {
                List<Position> moves;
                if (piece instanceof King) {
                    moves = ((King) piece).getPossibleMoves(this, false); // Exclude castling moves
                } else {
                    moves = piece.getPossibleMoves(this);
                }
                if (moves.contains(position)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Provides access to the current state of the board.
     *
     * @return A map representing the board state.
     */
    public Map<Position, Piece> getBoardMap() {
        return boardMap;
    }
}
