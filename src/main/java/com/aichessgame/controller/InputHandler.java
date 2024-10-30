package com.aichessgame.controller;
import com.aichessgame.utils.Position;
import javafx.scene.input.MouseEvent;

/**
 * Class responsible for handling player input.
 */
public class InputHandler {

    private Position selectedPosition; // The currently selected position
    private GameController gameController;

    /**
     * Constructor for the InputHandler class.
     *
     * @param gameController The GameController instance.
     */
    public InputHandler(GameController gameController) {
        this.gameController = gameController;
    }

    /**
     * Handles mouse click events on the board.
     *
     * @param event The MouseEvent triggered by the click.
     */
    public void handleMouseClick(MouseEvent event) {
        // Determine the position clicked based on event coordinates
        Position clickedPosition = getPositionFromMouseEvent(event);

        if (selectedPosition == null) {
            // No piece selected yet, select the piece at the clicked position
            if (gameController.getBoard().getPieceAt(clickedPosition) != null &&
                    gameController.getBoard().getPieceAt(clickedPosition).getColor() == gameController.getCurrentPlayer().getColor()) {
                selectedPosition = clickedPosition;
            }
        } else {
            // Attempt to move the selected piece to the clicked position
            boolean moveSuccessful = gameController.processMove(selectedPosition, clickedPosition);
            if (moveSuccessful) {
                // Move was successful, reset selected position
                selectedPosition = null;
            } else {
                // Move failed, keep the selected position
                // Optionally, provide feedback to the player
                selectedPosition = null; // Deselect after invalid move
            }
        }
    }

    /**
     * Converts mouse event coordinates to a board position.
     *
     * @param event The MouseEvent triggered by the click.
     * @return The corresponding Position on the board.
     */
    private Position getPositionFromMouseEvent(MouseEvent event) {
        // Implement logic to map mouse coordinates to board positions
        // This depends on your UI layout and scaling
        int column = (int) (event.getX() / tileSize);
        int row = 7 - (int) (event.getY() / tileSize); // Adjust for coordinate system
        return new Position(row, column);
    }

    // Define tileSize based on your board view dimensions
    private final int tileSize = 100; // Example value; adjust according to your UI
}
