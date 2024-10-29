package com.aichessgame.tests;
import com.aichessgame.utils.*;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

public class UtilsTest {

    public static void main(String[] args) {
        testPositionClass();
        testColorEnum();
        testHelperFunctions();
        testLoggerClass();
    }

    public static void testPositionClass() {
        System.out.println("Testing Position class...");

        testPositionCreation();
        testPositionEqualsAndHashCode();
        testPositionToString();
        testIsValidCoordinate();

        System.out.println("Position class tests completed.\n");
    }

    // 1. Test Position Creation
    public static void testPositionCreation() {
        System.out.println("Test Position Creation:");

        // Valid position
        try {
            Position validPosition = new Position(4, 3); // e4
            System.out.println("PASS: Valid position created: " + validPosition);
        } catch (IllegalArgumentException e) {
            System.out.println("FAIL: Valid position creation threw an exception.");
        }

        // Invalid position (negative coordinates)
        try {
            Position invalidPosition = new Position(-1, 0);
            System.out.println("FAIL: Invalid position (-1, 0) should have thrown an exception.");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Exception correctly thrown for invalid position (-1, 0).");
        }

        // Invalid position (coordinates out of bounds)
        try {
            Position invalidPosition = new Position(0, 8);
            System.out.println("FAIL: Invalid position (0, 8) should have thrown an exception.");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Exception correctly thrown for invalid position (0, 8).");
        }
    }

    // 2. Test equals() and hashCode()
    public static void testPositionEqualsAndHashCode() {
        System.out.println("\nTest Position equals() and hashCode():");

        Position position1 = new Position(4, 3); // e4
        Position position2 = new Position(4, 3);
        Position differentPosition = new Position(5, 3); // f4

        // Test equals() method
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

        // Test hashCode() consistency with equals()
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

    // 3. Test toString() method
    public static void testPositionToString() {
        System.out.println("\nTest Position toString():");

        Position position = new Position(4, 3); // Should correspond to e4
        String expectedOutput = "e4";
        String actualOutput = position.toString();

        if (actualOutput.equals(expectedOutput)) {
            System.out.println("PASS: toString() returned the correct chess notation (" + actualOutput + ").");
        } else {
            System.out.println("FAIL: toString() should return " + expectedOutput + " but returned " + actualOutput);
        }
    }

    // 4. Test isValidCoordinate()
    public static void testIsValidCoordinate() {
        System.out.println("\nTest Position.isValidCoordinate():");

        // Valid coordinates
        if (Position.isValidCoordinate(0) && Position.isValidCoordinate(7)) {
            System.out.println("PASS: 0 and 7 are valid coordinates.");
        } else {
            System.out.println("FAIL: 0 and 7 should be valid coordinates.");
        }

        // Invalid coordinates
        if (!Position.isValidCoordinate(-1) && !Position.isValidCoordinate(8)) {
            System.out.println("PASS: -1 and 8 are invalid coordinates.");
        } else {
            System.out.println("FAIL: -1 and 8 should be invalid coordinates.");
        }
    }

    public static void testColorEnum() {
        System.out.println("Testing Color enum...");

        testColorEnumValues();
        testColorOppositeMethod();

        System.out.println("Color enum tests completed.\n");
    }

    // 1. Test Color Enum Values
    public static void testColorEnumValues() {
        System.out.println("Test Color Enum Values:");

        // Check that WHITE and BLACK are defined
        Color white = Color.WHITE;
        Color black = Color.BLACK;

        if (white != null && black != null) {
            System.out.println("PASS: Color enum has WHITE and BLACK values.");
        } else {
            System.out.println("FAIL: Color enum should have WHITE and BLACK values.");
        }
    }

    // 2. Test opposite() method
    public static void testColorOppositeMethod() {
        System.out.println("\nTest Color.opposite():");

        if (Color.WHITE.opposite() == Color.BLACK) {
            System.out.println("PASS: WHITE.opposite() returns BLACK.");
        } else {
            System.out.println("FAIL: WHITE.opposite() should return BLACK.");
        }

        if (Color.BLACK.opposite() == Color.WHITE) {
            System.out.println("PASS: BLACK.opposite() returns WHITE.");
        } else {
            System.out.println("FAIL: BLACK.opposite() should return WHITE.");
        }
    }

    public static void testHelperFunctions() {
        System.out.println("Testing HelperFunctions class...");

        testIsValidPosition();
        testNotationToPosition();
        testPositionToNotation();

        System.out.println("HelperFunctions tests completed.\n");
    }

    // 1. Test isValidPosition()
    public static void testIsValidPosition() {
        System.out.println("Test HelperFunctions.isValidPosition():");

        // Valid positions
        if (HelperFunctions.isValidPosition(0, 0) && HelperFunctions.isValidPosition(7, 7)) {
            System.out.println("PASS: (0,0) and (7,7) are valid positions.");
        } else {
            System.out.println("FAIL: (0,0) and (7,7) should be valid positions.");
        }

        // Invalid positions
        if (!HelperFunctions.isValidPosition(-1, 0) && !HelperFunctions.isValidPosition(0, 8)) {
            System.out.println("PASS: (-1,0) and (0,8) are invalid positions.");
        } else {
            System.out.println("FAIL: (-1,0) and (0,8) should be invalid positions.");
        }

        // Valid Position object
        Position validPosition = new Position(3, 3);
        if (HelperFunctions.isValidPosition(validPosition)) {
            System.out.println("PASS: Position(3,3) is valid.");
        } else {
            System.out.println("FAIL: Position(3,3) should be valid.");
        }

        // Invalid Position object
        Position invalidPosition = null;
        if (!HelperFunctions.isValidPosition(invalidPosition)) {
            System.out.println("PASS: null Position is invalid.");
        } else {
            System.out.println("FAIL: null Position should be invalid.");
        }
    }

    // 2. Test notationToPosition()
    public static void testNotationToPosition() {
        System.out.println("\nTest HelperFunctions.notationToPosition():");

        String notation = "e4";
        Position expectedPosition = new Position(4, 3); // e4
        try {
            Position actualPosition = HelperFunctions.notationToPosition(notation);
            if (actualPosition.equals(expectedPosition)) {
                System.out.println("PASS: notationToPosition(\"" + notation + "\") returned correct position.");
            } else {
                System.out.println("FAIL: notationToPosition(\"" + notation + "\") returned incorrect position.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("FAIL: notationToPosition(\"" + notation + "\") threw an exception.");
        }

        // Test invalid notation
        String invalidNotation = "z9";
        try {
            HelperFunctions.notationToPosition(invalidNotation);
            System.out.println("FAIL: notationToPosition(\"" + invalidNotation + "\") should have thrown an exception.");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Exception correctly thrown for invalid notation \"" + invalidNotation + "\".");
        }
    }

    // 3. Test positionToNotation()
    public static void testPositionToNotation() {
        System.out.println("\nTest HelperFunctions.positionToNotation():");

        Position position = new Position(4, 3); // e4
        String expectedNotation = "e4";
        try {
            String actualNotation = HelperFunctions.positionToNotation(position);
            if (actualNotation.equals(expectedNotation)) {
                System.out.println("PASS: positionToNotation(Position(4,3)) returned \"" + actualNotation + "\".");
            } else {
                System.out.println("FAIL: positionToNotation(Position(4,3)) returned incorrect notation.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("FAIL: positionToNotation(Position(4,3)) threw an exception.");
        }

        // Test invalid Position
        Position invalidPosition = null;
        try {
            HelperFunctions.positionToNotation(invalidPosition);
            System.out.println("FAIL: positionToNotation(null) should have thrown an exception.");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Exception correctly thrown for null position.");
        }
    }

    public static void testLoggerClass() {
        System.out.println("Testing ChessLogger class...");

        testLoggerSingleton();
        testLogMethods();

        System.out.println("ChessLogger class tests completed.\n");
    }

    // 1. Test ChessLogger Singleton
    public static void testLoggerSingleton() {
        System.out.println("Test ChessLogger Singleton:");

        ChessLogger chessLogger1 = ChessLogger.getInstance();
        ChessLogger chessLogger2 = ChessLogger.getInstance();

        if (chessLogger1 == chessLogger2) {
            System.out.println("PASS: ChessLogger.getInstance() returns the same instance.");
        } else {
            System.out.println("FAIL: ChessLogger.getInstance() should return the same instance.");
        }
    }

    // 2. Test log methods
    public static void testLogMethods() {
        System.out.println("\nTest ChessLogger log methods:");

        ChessLogger chessLogger = ChessLogger.getInstance();

        // Access the underlying logger
        java.util.logging.Logger underlyingLogger = java.util.logging.Logger.getLogger("ChessGameLogger");
        underlyingLogger.setLevel(Level.ALL);

        // Check if ConsoleHandler is already present to avoid adding duplicates
        boolean hasConsoleHandler = false;
        for (var handler : underlyingLogger.getHandlers()) {
            if (handler instanceof ConsoleHandler) {
                hasConsoleHandler = true;
                break;
            }
        }

        // Only add a ConsoleHandler if it's not already there
        if (!hasConsoleHandler) {
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new SimpleFormatter());
            consoleHandler.setLevel(Level.INFO); // Adjust as needed
            underlyingLogger.addHandler(consoleHandler);
        }

        // Test logging
        chessLogger.logMove("e2e4");
        System.out.println("Check console or log file for move log.");

        chessLogger.logError("Test error message");
        System.out.println("Check console or log file for error log.");

        chessLogger.logEvent("Test event message");
        System.out.println("Check console or log file for event log.");
    }

}

