package com.aichessgame.utils;

import java.util.Objects;

public class Position {

    private final int row; // Rank (row), values from 0 to 7 representing '1' to '8'
    private final int column; // File (column), values from 0 to 7 representing 'a' to 'h'

    // Constants for board bounds
    public static final int MIN_POSITION = 0;
    public static final int MAX_POSITION = 7;

    // Constructor
    public Position(int row, int column) {
        if (!isValidCoordinate(row) || !isValidCoordinate(column)) {
            throw new IllegalArgumentException("Invalid board position: (" + row + ", " + column + ")");
        }
        this.row = row;
        this.column = column;
    }

    // Getters
    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    // Validation methods
    public static boolean isValidCoordinate(int coord) {
        return coord >= MIN_POSITION && coord <= MAX_POSITION;
    }

    // equals() and hashCode() methods
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Position)) {
            return false;
        }
        Position other = (Position) obj;
        return row == other.row && column == other.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    // toString() method
    @Override
    public String toString() {
        // Convert x and y to chess notation (e.g., 'e4')
        char file = (char) ('a' + row);
        int rank = column + 1;
        return String.format("%c%d", file, rank);
    }
}
