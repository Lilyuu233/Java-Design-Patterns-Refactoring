package uk.ac.nott.cs.comp2013.froggergame.model.entities;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import uk.ac.nott.cs.comp2013.froggergame.model.base.Actor;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.collision.AnimalCollisionHandler;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.collision.CollisionHandler;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.AnimalStateManager;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.StateManager;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Score;

/**
 * Represents the player-controlled animal in the game, handling movement, collisions, and state management.
 */
public class Animal extends Actor {

    // Constants for default values
    private static final double INITIAL_HIGHEST_Y = 800.0;
    private static final double MOVEMENT_Y = 26.6667; // Vertical movement step
    private static final double MOVEMENT_X = 21.3333; // Horizontal movement step
    private static final int IMAGE_SIZE = 40; // Size of the animal image
    private static final double DEFAULT_X = 300; // Default X position
    private static final double DEFAULT_Y = 733 + MOVEMENT_Y; // Default Y position
    private static final double DEFAULT_SPEED = 0.0; // Default speed

    // Instance variables
    private Actor currentPlatform; // The platform the animal is currently on
    private boolean active = true; // Whether the animal is active
    private final StateManager stateManager; // Manages the animal's state (e.g., score, deaths)
    private final CollisionHandler collisionHandler; // Handles collision logic
    private boolean carDeath = false; // Whether the animal died due to a car collision
    private boolean waterDeath = false; // Whether the animal died in water
    private Score scoreDisplay; // Displays the score

    /**
     * Constructs an animal with the specified image.
     *
     * @param imageLink The path to the animal's image
     */
    public Animal(String imageLink) {
        super((int) DEFAULT_X, (int) DEFAULT_Y, DEFAULT_SPEED);
        try {
            setImage(new Image(imageLink, IMAGE_SIZE, IMAGE_SIZE, true, true));
        } catch (RuntimeException ignored) {
        }
        this.stateManager = createDefaultStateManager();
        this.collisionHandler = createDefaultCollisionHandler();
        this.scoreDisplay = new Score();
        resetPosition();
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Creates the default state manager for the animal.
     *
     * @return The default StateManager instance
     */
    private StateManager createDefaultStateManager() {
        return new AnimalStateManager(this);
    }

    /**
     * Creates the default collision handler for the animal.
     *
     * @return The default CollisionHandler instance
     */
    private CollisionHandler createDefaultCollisionHandler() {
        if (scoreDisplay == null) {
            scoreDisplay = new Score();
        }
        return new AnimalCollisionHandler(this, stateManager, scoreDisplay);
    }

    /**
     * Resets the animal's position and state to the defaults.
     */
    public void resetPosition() {
        setX(DEFAULT_X);
        setY(DEFAULT_Y);
        carDeath = false;
        waterDeath = false;
        stateManager.reset();
    }

    public void setCurrentPlatform(Actor platform) {
        this.currentPlatform = platform;
    }

    public void clearCurrentPlatform() {
        this.currentPlatform = null;
    }

    public boolean isOnPlatform() {
        return currentPlatform != null;
    }

    public void setCarDeath(boolean carDeath) {
        this.carDeath = carDeath;
    }

    public void setWaterDeath(boolean waterDeath) {
        this.waterDeath = waterDeath;
    }

    public StateManager getStateManager() {
        return stateManager;
    }

    /**
     * Calculates the horizontal movement based on the pressed key.
     *
     * @param key The key code
     * @return The horizontal movement distance
     */
    public double getMovementX(KeyCode key) {
        return switch (key) {
            case A -> -MOVEMENT_X;
            case D -> MOVEMENT_X;
            default -> 0;
        };
    }

    /**
     * Calculates the vertical movement based on the pressed key.
     *
     * @param key The key code
     * @return The vertical movement distance
     */
    public double getMovementY(KeyCode key) {
        return switch (key) {
            case W -> -MOVEMENT_Y;
            case S -> MOVEMENT_Y;
            default -> 0;
        };
    }

    public boolean isDead() {
        return carDeath || waterDeath;
    }

    /**
     * Moves the animal based on the speed of the platform it is on.
     */
    private void applyPlatformMovement() {
        if (isOnPlatform() && currentPlatform != null) {
            double platformSpeed = currentPlatform.getSpeed();
            setX(getX() + platformSpeed);
        }
    }

    /**
     * Handles the death animation of the animal.
     *
     * @param now       The current time in nanoseconds
     * @param deathType The type of death (e.g., "cardeath", "waterdeath")
     * @param frameCount The number of animation frames
     */
    private void handleDeathAnimation(long now, String deathType, int frameCount) {
        stateManager.updateDeathAnimation(now, deathType, frameCount);
    }

    /**
     * Updates the animal's state, including collision checks and platform movement.
     */
    private void updateState() {
        collisionHandler.checkCollisions();
        applyPlatformMovement();
        if (isInWaterWithoutPlatform()) {
            waterDeath = true;
        }
    }

    /**
     * Checks if the animal is in water without being on a platform.
     *
     * @return True if in water without a platform, false otherwise
     */
    public boolean isInWaterWithoutPlatform() {
        return !isOnPlatform() && getY() < 413.0;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public void act(long now) {
        if (!active) {
            return;
        }
        if (isDead()) {
            handleDeathAnimation(now, carDeath ? "cardeath" : "waterdeath", carDeath ? 4 : 5);
        } else {
            updateState();
        }
        stateManager.update(now);
    }
}
