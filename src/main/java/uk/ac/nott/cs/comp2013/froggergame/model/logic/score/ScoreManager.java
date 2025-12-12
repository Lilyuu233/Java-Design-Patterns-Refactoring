package uk.ac.nott.cs.comp2013.froggergame.model.logic.score;

import java.util.Stack;

/**
 * Manages the scoring system for the game, including point additions,
 * score changes, and score digit extraction for display purposes.
 */
public class ScoreManager {
    private int points = 0; // Current score
    private boolean scoreChanged = false; // Flag indicating if the score has changed
    private static final int POINTS_PER_MOVE_UP = 10; // Points awarded for moving up
    private static final int POINTS_PER_MOVE_DOWN = -10; // Points deducted for moving down

    /**
     * Adds points to the current score. Ensures score does not go below zero.
     *
     * @param value The number of points to add (can be negative).
     */
    public void addPoints(int value) {
        if (value != 0) {
            points = Math.max(0, points + value);
            scoreChanged = true;
        }
    }

    /**
     * Returns the current score.
     *
     * @return The current score.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Updates the score based on vertical movement of the player.
     *
     * @param dy The vertical displacement.
     */
    public void handleMovementScore(double dy) {
        if (dy < 0) {
            addPoints(POINTS_PER_MOVE_UP);
        } else if (dy > 0) {
            addPoints(POINTS_PER_MOVE_DOWN);
        }
    }

    /**
     * Checks if the score has changed since the last update.
     *
     * @return True if the score has changed, false otherwise.
     */
    public boolean hasScoreChanged() {
        return scoreChanged;
    }

    /**
     * Resets the score changed flag.
     */
    public void resetScoreChangedFlag() {
        scoreChanged = false;
    }

    /**
     * Converts the score into a stack of digits for display.
     *
     * @param score The score to convert.
     * @return A stack containing the digits of the score.
     * @throws IllegalArgumentException If the score is negative.
     */
    public Stack<Integer> getDigits(int score) {
        if (score < 0) {
            throw new IllegalArgumentException("Score cannot be negative.");
        }

        Stack<Integer> digits = new Stack<>();
        if (score == 0) {
            digits.push(0);
        } else {
            while (score > 0) {
                digits.push(score % 10);
                score /= 10;
            }
        }
        return digits;
    }
}
