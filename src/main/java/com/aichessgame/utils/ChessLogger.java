package com.aichessgame.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ChessLogger {
    private static final ChessLogger instance = new ChessLogger();
    private static final Logger logger = Logger.getLogger("ChessGameLogger");

    // Static initialization block for handler setup
    static {
        setupLogger();
    }

    private ChessLogger() {
        // Private constructor to enforce singleton
    }

    public static ChessLogger getInstance() {
        return instance;
    }

    private static void setupLogger() {
        try {
            // Ensure the 'logs' directory exists
            if (!Files.exists(Paths.get("logs"))) {
                Files.createDirectory(Paths.get("logs"));
            }

            // Set logger level and disable parent handlers to avoid duplicate logging from root
            logger.setLevel(Level.ALL);
            logger.setUseParentHandlers(false);

            // Add FileHandler if not already present
            FileHandler fileHandler = new FileHandler("logs/ChessGame.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.ALL);
            logger.addHandler(fileHandler);

            // Add ConsoleHandler if not already present
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new SimpleFormatter());
            consoleHandler.setLevel(Level.INFO); // Adjust as needed
            logger.addHandler(consoleHandler);

        } catch (IOException e) {
            System.err.println("Failed to initialize ChessLogger: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void logMove(String move) {
        logger.info("Move: " + move);
    }

    public void logError(String errorMessage) {
        logger.severe("Error: " + errorMessage);
    }

    public void logEvent(String eventMessage) {
        logger.fine("Event: " + eventMessage);
    }
}
