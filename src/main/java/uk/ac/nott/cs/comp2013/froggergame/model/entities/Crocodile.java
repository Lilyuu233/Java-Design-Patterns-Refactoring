package uk.ac.nott.cs.comp2013.froggergame.model.entities;

import uk.ac.nott.cs.comp2013.froggergame.model.base.Actor;

/**
 * Represents a crocodile that moves horizontally back and forth within a specified range.
 */
public class Crocodile extends Actor {
    private static final double MOVE_DISTANCE = 400; // Maximum horizontal distance the crocodile can move
    private final double initialX; // Initial X position of the crocodile
    private boolean movingRight = true; // Indicates whether the crocodile is moving to the right
    private static final double SPEED = 1.0; // Speed of the crocodile's movement

    /**
     * Constructs a crocodile at the specified position with the given speed.
     *
     * @param x     Initial X position
     * @param y     Initial Y position
     * @param speed Movement speed
     */
    public Crocodile(double x, double y, double speed) {
        super((int) x, (int) y, speed);
        this.initialX = x;
    }

    /**
     * Checks whether the crocodile is moving to the right.
     *
     * @return True if moving right, otherwise false
     */
    public boolean isMovingRight() {
        return movingRight;
    }

    /**
     * Sets the movement direction of the crocodile.
     *
     * @param movingRight True to move right, false to move left
     */
    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    /**
     * Defines the crocodile's behavior during each game tick.
     * The crocodile moves horizontally within a predefined range, reversing direction at the boundaries.
     *
     * @param now Current game time in nanoseconds
     */
    @Override
    public void act(long now) {
        if (movingRight) {
            move(SPEED, 0); // Move to the right
            if (getX() >= initialX + MOVE_DISTANCE) {
                movingRight = false; // Reverse direction when reaching the right boundary
            }
        } else {
            move(-SPEED, 0); // Move to the left
            if (getX() <= initialX) {
                movingRight = true; // Reverse direction when reaching the left boundary
            }
        }
    }
}
