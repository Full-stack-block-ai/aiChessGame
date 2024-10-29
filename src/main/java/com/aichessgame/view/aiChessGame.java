package com.aichessgame.view;

import com.aichessgame.utils.Position;

public class aiChessGame {

    public static void main(String[] args) {
        testPositionEqualsAndHashCode();
        testPositionToString();
    }

    // Method to test equals() and hashCode() of Position class
    public static void testPositionEqualsAndHashCode() {
        Position position1 = new Position(4, 3); // e4 in chess notation
        Position position2 = new Position(4, 3);
        Position differentPosition = new Position(5, 3); // f4

        // Test equals method
        if (position1.equals(position2)) {
            System.out.println("PASS: position1 is equal to position2.");
        } else {
            System.out.println("FAIL: position1 should be equal to position2.");
        }

        if (!position1.equals(differentPosition)) {
            System.out.println("PASS: position1 is not equal to differentPosition.");
        } else {
            System.out.println("FAIL: position1 should not be equal to differentPosition.");
        }

        // Test hashCode method consistency with equals
        if (position1.hashCode() == position2.hashCode()) {
            System.out.println("PASS: position1 and position2 have the same hashCode.");
        } else {
            System.out.println("FAIL: position1 and position2 should have the same hashCode.");
        }

        if (position1.hashCode() != differentPosition.hashCode()) {
            System.out.println("PASS: position1 and differentPosition have different hashCodes.");
        } else {
            System.out.println("FAIL: position1 and differentPosition should have different hashCodes.");
        }
    }

    // Method to test toString() of Position class
    public static void testPositionToString() {
        Position position = new Position(4, 3); // Should correspond to e4 in chess notation
        String expectedOutput = "e4";
        String actualOutput = position.toString();

        if (actualOutput.equals(expectedOutput)) {
            System.out.println("PASS: toString() returned the correct chess notation for position.");
        } else {
            System.out.println("FAIL: toString() should return " + expectedOutput + " but returned " + actualOutput);
        }
    }
}
