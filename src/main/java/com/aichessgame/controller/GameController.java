package com.aichessgame.controller;
import com.aichessgame.model.*;
import com.aichessgame.utils.*;


/**
 * Controller class that manages game flow and rules.
 */
public class GameController {
    private Board board;                 // The game board
    private Player whitePlayer;          // White player
    private Player blackPlayer;          // Black player
    private Player currentPlayer;        // The player whose turn it is
    private GameState gameState;         // The current state of the game
    private MoveValidator moveValidator; // Validates moves according to game rules

    /**
     * Constructor for the GameController class.
     */
    public GameController() {
        initializeGame();
    }

    /**
     * Initializes the game by setting up the board and players.
     */
    private void initializeGame() {
        board = new Board();
        whitePlayer = new Player(Color.WHITE, true);
        blackPlayer = new Player(Color.BLACK, true); // Change to false if AI
        currentPlayer = whitePlayer;
        gameState = GameState.ONGOING;
        moveValidator = new MoveValidator();
    }

    /**
     * Processes a move from one position to another.
     *
     * @param fromPosition The starting position.
     * @param toPosition   The ending position.
     * @return True if the move was successful, false otherwise.
     */
    public boolean processMove(Position fromPosition, Position toPosition) {
        Piece piece = board.getPieceAt(fromPosition);

        if (piece == null) {
            // No piece at the starting position
            ChessLogger.getInstance().logError("No piece at starting position: " + fromPosition);
            return false;
        }

        if (piece.getColor() != currentPlayer.getColor()) {
            // The piece does not belong to the current player
            ChessLogger.getInstance().logError("Attempted to move opponent's piece from: " + fromPosition);
            return false;
        }

        if (moveValidator.isMoveValid(piece, toPosition, board)) {
            // Move is valid
            board.movePiece(fromPosition, toPosition);
            ChessLogger.getInstance().logMove(currentPlayer.getColor() + " moved from " + fromPosition + " to " + toPosition);

            // Update game state (e.g., check for check, checkmate)
            updateGameState();

            // Switch to the next player
            switchPlayer();
            return true;
        } else {
            // Move is invalid
            ChessLogger.getInstance().logError("Invalid move from " + fromPosition + " to " + toPosition);
            return false;
        }
    }

    /**
     * Updates the game state after a move.
     */
    private void updateGameState() {
        if (board.isKingInCheck(currentPlayer.getColor().opposite())) {
            if (moveValidator.isCheckmate(currentPlayer.getColor().opposite(), board)) {
                gameState = GameState.CHECKMATE;
                ChessLogger.getInstance().logEvent("Checkmate! " + currentPlayer.getColor() + " wins.");
            } else {
                gameState = GameState.CHECK;
                ChessLogger.getInstance().logEvent(currentPlayer.getColor().opposite() + " is in check.");
            }
        } else if (moveValidator.isStalemate(currentPlayer.getColor().opposite(), board)) {
            gameState = GameState.STALEMATE;
            ChessLogger.getInstance().logEvent("Stalemate! The game is a draw.");
        } else {
            gameState = GameState.ONGOING;
        }
    }

    /**
     * Switches the current player to the next player.
     */
    private void switchPlayer() {
        currentPlayer = (currentPlayer == whitePlayer) ? blackPlayer : whitePlayer;
    }

    /**
     * Gets the current board.
     *
     * @return The current board.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Gets the current player.
     *
     * @return The current player.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Gets the current game state.
     *
     * @return The current game state.
     */
    public GameState getGameState() {
        return gameState;
    }
}
