package com.aichessgame.utils;

public class HelperFunctions {

    // Checks if the given coordinates are within the chessboard bounds
    public static boolean isValidPosition(int row, int column) {
        return Position.isValidCoordinate(row) && Position.isValidCoordinate(column);
    }

    // Overloaded method to check a Position object
    public static boolean isValidPosition(Position position) {
        if (position == null) {
            return false;
        }
        return isValidPosition(position.getRow(), position.getColumn());
    }

    // Converts algebraic notation (e.g., "e4") to a Position object
    public static Position notationToPosition(String notation) {
        if (notation == null || notation.length() != 2) {
            throw new IllegalArgumentException("Invalid notation: " + notation);
        }

        char fileChar = notation.charAt(0);
        char rankChar = notation.charAt(1);

        int x = fileChar - 'a';
        int y = Character.getNumericValue(rankChar) - 1;

        if (!isValidPosition(x, y)) {
            throw new IllegalArgumentException("Notation out of bounds: " + notation);
        }

        return new Position(x, y);
    }

    // Converts a Position object to algebraic notation (e.g., "e4")
    public static String positionToNotation(Position position) {
        if (!isValidPosition(position)) {
            throw new IllegalArgumentException("Invalid position: " + position);
        }

        char file = (char) ('a' + position.getRow());
        int rank = position.getColumn() + 1;

        return "" + file + rank;
    }



}