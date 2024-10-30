
package com.aichessgame.model;

import com.aichessgame.utils.Color;

/**
 * Class representing a player in the game.
 */
public class Player {
    private final Color color;     // The color assigned to the player
    private final boolean isHuman; // Indicates if the player is human or AI

    /**
     * Constructor for the Player class.
     *
     * @param color   The color of the player.
     * @param isHuman True if the player is human, false if AI.
     */
    public Player(Color color, boolean isHuman) {
        this.color = color;
        this.isHuman = isHuman;
    }

    /**
     * Gets the color of the player.
     *
     * @return The color of the player.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Checks if the player is human.
     *
     * @return True if the player is human, false otherwise.
     */
    public boolean isHuman() {
        return isHuman;
    }
}
