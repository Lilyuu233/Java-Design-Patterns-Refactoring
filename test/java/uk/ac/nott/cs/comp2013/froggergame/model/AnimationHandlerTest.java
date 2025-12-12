package uk.ac.nott.cs.comp2013.froggergame.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import uk.ac.nott.cs.comp2013.froggergame.model.logic.animation.AnimationHandler;

class AnimationHandlerTest {

    @Test
    void testGetCurrentFrameIndex() {
        long animationInterval = 1_000_000_000; // 1 second in nanoseconds
        int frameCount = 4; // Total number of frames

        // Test when `now` is at the start
        assertEquals(0, AnimationHandler.getCurrentFrameIndex(0, animationInterval, frameCount));

        // Test when `now` is within the first frame
        assertEquals(0, AnimationHandler.getCurrentFrameIndex(500_000_000, animationInterval, frameCount));

        // Test when `now` transitions to the second frame
        assertEquals(1, AnimationHandler.getCurrentFrameIndex(1_000_000_000, animationInterval, frameCount));
    }

    @Test
    void testGetCurrentFrameIndexWithDifferentInterval() {
        long animationInterval = 500_000_000; // 0.5 seconds in nanoseconds
        int frameCount = 3;

        assertEquals(0, AnimationHandler.getCurrentFrameIndex(0, animationInterval, frameCount));
        assertEquals(1, AnimationHandler.getCurrentFrameIndex(500_000_000, animationInterval, frameCount));
        assertEquals(2, AnimationHandler.getCurrentFrameIndex(1_000_000_000, animationInterval, frameCount));
        assertEquals(0, AnimationHandler.getCurrentFrameIndex(1_500_000_000, animationInterval, frameCount));
    }

    @Test
    void testGetCurrentFrameIndexWithSingleFrame() {
        long animationInterval = 1_000_000_000; // 1 second in nanoseconds
        int frameCount = 1;

        // All `now` values should return frame 0 since there is only one frame
        assertEquals(0, AnimationHandler.getCurrentFrameIndex(0, animationInterval, frameCount));
        assertEquals(0, AnimationHandler.getCurrentFrameIndex(1_000_000_000, animationInterval, frameCount));
    }
}

