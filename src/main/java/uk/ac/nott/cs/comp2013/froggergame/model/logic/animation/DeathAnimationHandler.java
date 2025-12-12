package uk.ac.nott.cs.comp2013.froggergame.model.logic.animation;

import javafx.scene.image.Image;

import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import uk.ac.nott.cs.comp2013.froggergame.view.resource.ImageManager;

/**
 * Handles the animation logic for an animal's death, including updating frames and resetting states.
 */
public class DeathAnimationHandler {
    private int deathAnimationFrame = 0;
    private static final int DEFAULT_FRAME_INTERVAL = 5;
    private final int frameInterval;

    /**
     * Default constructor using the default frame interval.
     */
    public DeathAnimationHandler() {
        this(DEFAULT_FRAME_INTERVAL);
    }

    /**
     * Constructor that allows customizing the frame interval.
     *
     * @param frameInterval The interval between animation frames.
     */
    public DeathAnimationHandler(int frameInterval) {
        this.frameInterval = frameInterval;
    }

    /**
     * Animates the death of an animal based on the provided context.
     *
     * @param now     The current time in nanoseconds.
     * @param context The animation context containing the animal, image manager, type, and frame limit.
     * @return The current state of the animation: IN_PROGRESS or COMPLETE.
     */
    public AnimationState animateDeath(long now, DeathAnimationContext context) {
        if (shouldUpdateFrame(now)) {
            deathAnimationFrame++;
        }

        if (deathAnimationFrame < context.frameLimit()) {
            updateDeathImage(context.animal(), context.imageManager(), context.type());
            return AnimationState.IN_PROGRESS;
        }

        resetAfterDeath(context.animal(), context.imageManager());
        return AnimationState.COMPLETE;
    }

    /**
     * Determines whether the frame should be updated based on the current time.
     *
     * @param now The current time in nanoseconds.
     * @return True if the frame should be updated, false otherwise.
     */
    private boolean shouldUpdateFrame(long now) {
        return now % frameInterval == 0;
    }

    /**
     * Updates the animal's image to the current death animation frame.
     *
     * @param animal       The animal to update.
     * @param imageManager The image manager for retrieving death images.
     * @param type         The type of death animation.
     */
    private void updateDeathImage(Animal animal, ImageManager imageManager, String type) {
        Image deathImage = imageManager.getDeathImage(type, deathAnimationFrame);
        animal.setImage(deathImage);
    }

    /**
     * Resets the animal's state and image after the death animation is complete.
     *
     * @param animal       The animal to reset.
     * @param imageManager The image manager for retrieving the default image.
     */
    private void resetAfterDeath(Animal animal, ImageManager imageManager) {
        animal.resetPosition();
        animal.setImage(imageManager.getDefaultImage());
        deathAnimationFrame = 0;
    }

    /**
     * Resets the death animation to its initial state.
     */
    public void reset() {
        deathAnimationFrame = 0;
    }

    /**
     * Enum representing the current state of the animation.
     */
    public enum AnimationState {
        IN_PROGRESS,
        COMPLETE
    }
}
