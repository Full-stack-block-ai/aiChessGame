package com.aichessgame.model;
import com.aichessgame.utils.*;
import java.util.List;

/**
 * Abstract class representing a chess piece.
 */
public abstract class Piece {
    protected final Color color;       // Color of the piece (WHITE or BLACK)
    protected Position position;       // Current position of the piece on the board
    protected boolean hasMoved;        // Indicates if the piece has moved (for castling and initial pawn moves)

    /**
     * Constructor to initialize the piece with its color and position.
     *
     * @param color    The color of the piece.
     * @param position The initial position of the piece.
     */
    public Piece(Color color, Position position) {
        this.color = color;
        this.position = position;
        this.hasMoved = false;
    }

    /**
     * Gets the color of the piece.
     *
     * @return The color of the piece.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Gets the current position of the piece.
     *
     * @return The current position of the piece.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Sets the new position of the piece.
     *
     * @param position The new position of the piece.
     */
    public void setPosition(Position position) {
        this.position = position;
        this.hasMoved = true;
    }

    /**
     * Checks if the piece has moved.
     *
     * @return True if the piece has moved, false otherwise.
     */
    public boolean hasMoved() {
        return hasMoved;
    }

    /**
     * Abstract method to get all possible legal moves for the piece.
     *
     * @param board The current state of the board.
     * @return A list of positions representing legal moves.
     */
    public abstract List<Position> getPossibleMoves(Board board);

    /**
     * Returns the type of the piece (e.g., "Pawn", "Knight").
     *
     * @return The type of the piece.
     */
    public abstract String getType();
}