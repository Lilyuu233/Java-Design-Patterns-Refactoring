package uk.ac.nott.cs.comp2013.froggergame.model.logic.collision;

/**
 * Manages boundary conditions for game objects, determining if they are out of bounds
 * and resetting their position based on movement direction.
 */
public class BoundaryManager {
    private final int screenLeftBound; // The left boundary of the screen
    private final int screenRightBound; // The right boundary of the screen
    private final int resetLeftPosition; // Position to reset to when moving left out of bounds
    private final int resetRightPosition; // Position to reset to when moving right out of bounds

    /**
     * Constructor to initialize boundary and reset positions.
     *
     * @param screenLeftBound   The left boundary of the screen.
     * @param screenRightBound  The right boundary of the screen.
     * @param resetLeftPosition The reset position when moving left out of bounds.
     * @param resetRightPosition The reset position when moving right out of bounds.
     */
    public BoundaryManager(int screenLeftBound, int screenRightBound, int resetLeftPosition, int resetRightPosition) {
        this.screenLeftBound = screenLeftBound;
        this.screenRightBound = screenRightBound;
        this.resetLeftPosition = resetLeftPosition;
        this.resetRightPosition = resetRightPosition;
    }

    /**
     * Determines if a position is out of bounds based on its direction and speed.
     *
     * @param posX  The current x-position of the object.
     * @param speed The speed of the object.
     * @return True if the object is out of bounds; otherwise, false.
     */
    public boolean isOutOfBounds(double posX, double speed) {
        return (posX > screenRightBound && speed > 0) || (posX < screenLeftBound && speed < 0);
    }

    /**
     * Resets the position of the object based on its speed and direction.
     *
     * @param speed The speed of the object.
     * @return The new reset position.
     */
    public double resetPositionBasedOnSpeed(double speed) {
        if (speed > 0) {
            return resetRightPosition;
        } else if (speed < 0) {
            return resetLeftPosition;
        }
        return 0; // No reset if speed is zero
    }
}
