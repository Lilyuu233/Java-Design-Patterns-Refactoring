package uk.ac.nott.cs.comp2013.froggergame.model.logic.animation;

/**
 * Utility class for handling animation frame calculations.
 */
public class AnimationHandler {

    /**
     * Calculates the current frame index based on the elapsed time.
     *
     * @param now               The current time in nanoseconds.
     * @param animationInterval The duration of each animation frame in nanoseconds.
     * @param frameCount        The total number of frames in the animation.
     * @return The index of the current frame (0-based).
     */
    public static int getCurrentFrameIndex(long now, long animationInterval, int frameCount) {
        return (int) ((now / animationInterval) % frameCount);
    }
}
