package uk.ac.nott.cs.comp2013.froggergame.model.logic.state;

import javafx.scene.input.KeyCode;

/**
 * Interface defining the state management operations for game entities, such as managing scores,
 * movement, and death animations.
 */
public interface StateManager {

    /**
     * Resets the state to its initial values.
     */
    void reset();

    /**
     * Checks if the victory condition has been met.
     *
     * @return true if the player has achieved victory; otherwise, false.
     */
    boolean isVictory();

    /**
     * Updates the state based on the current game time.
     *
     * @param now The current time in nanoseconds.
     */
    void update(long now);

    /**
     * Updates the death animation state.
     *
     * @param now        The current time in nanoseconds.
     * @param type       The type of death animation.
     * @param frameLimit The maximum number of animation frames.
     */
    void updateDeathAnimation(long now, String type, int frameLimit);

    /**
     * Checks if the entity is currently unable to move.
     *
     * @return true if movement is restricted; otherwise, false.
     */
    boolean isNoMove();

    /**
     * Moves the entity based on the specified direction and key input.
     *
     * @param dx      The change in x position.
     * @param dy      The change in y position.
     * @param key     The key triggering the movement.
     * @param jumping Whether the movement involves jumping.
     */
    void moveAnimal(double dx, double dy, KeyCode key, boolean jumping);

    /**
     * Adds points to the current score.
     *
     * @param points The number of points to add.
     */
    void addPoints(int points);

    /**
     * Retrieves the current score.
     *
     * @return The current score.
     */
    int getPoints();

    /**
     * Checks if the score has changed.
     *
     * @return true if the score has changed; otherwise, false.
     */
    boolean hasScoreChanged();

    /**
     * Resets the flag indicating whether the score has changed.
     */
    void resetScoreChangedFlag();
}
