package uk.ac.nott.cs.comp2013.froggergame.model.entities;

import javafx.scene.image.Image;
import uk.ac.nott.cs.comp2013.froggergame.model.base.Actor;
import uk.ac.nott.cs.comp2013.froggergame.view.resource.ImageLoader;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.animation.AnimationHandler;

/**
 * Abstract base class for turtles, providing shared animation and movement logic.
 * Subclasses must implement specific behavior through {@link #updateStateBasedOnFrame(int)}.
 */
public abstract class AbstractTurtle extends Actor {
    private static final long ANIMATION_INTERVAL = 900_000_000; // Time interval for frame updates
    protected final Image[] animationFrames; // Array of images used for animation

    /**
     * Constructor for initializing the turtle's position, speed, size, and animation frames.
     *
     * @param xpos       The initial x position
     * @param ypos       The initial y position
     * @param speed      The movement speed of the turtle
     * @param width      The width of the turtle's image
     * @param height     The height of the turtle's image
     * @param framePaths Array of file paths for animation frames
     */
    public AbstractTurtle(int xpos, int ypos, int speed, int width, int height, String[] framePaths) {
        super(xpos, ypos, speed);
        this.animationFrames = loadAnimationFrames(framePaths, width, height);
        configureBounds(-75, 600, 600, -200); // Default boundaries for movement
    }

    /**
     * Loads the animation frames from file paths, resizing them to the specified dimensions.
     *
     * @param framePaths Array of file paths for the animation frames
     * @param width      The width to resize the frames
     * @param height     The height to resize the frames
     * @return Array of loaded and resized images
     */
    private Image[] loadAnimationFrames(String[] framePaths, int width, int height) {
        Image[] frames = new Image[framePaths.length];
        for (int i = 0; i < framePaths.length; i++) {
            frames[i] = ImageLoader.loadImage(framePaths[i], width, height);
        }
        return frames;
    }

    /**
     * Updates the turtle's state based on the current animation frame.
     * Subclasses must provide their own implementation.
     *
     * @param frame The current animation frame index
     */
    protected abstract void updateStateBasedOnFrame(int frame);

    /**
     * Defines the behavior of the turtle for each game tick, including animation updates and movement.
     *
     * @param now The current time in nanoseconds
     */
    @Override
    public void act(long now) {
        int frame = AnimationHandler.getCurrentFrameIndex(now, ANIMATION_INTERVAL, animationFrames.length);
        updateStateBasedOnFrame(frame);
        setImage(animationFrames[frame]); // Set the current frame image
        move(speed, 0); // Move the turtle horizontally
        resetPositionIfOutOfBounds(speed); // Reset position if out of screen bounds
    }
}
