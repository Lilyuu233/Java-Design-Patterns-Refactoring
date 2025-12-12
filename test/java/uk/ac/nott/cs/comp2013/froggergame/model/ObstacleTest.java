package uk.ac.nott.cs.comp2013.froggergame.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import uk.ac.nott.cs.comp2013.froggergame.model.entities.Obstacle;

/**
 * Unit tests for {@link Obstacle}.
 * Ensures that the Obstacle class behaves as expected during initialization, movement, and boundary checks.
 */
class ObstacleTest {

    private Obstacle obstacle;

    /**
     * Sets up an obstacle instance with predefined attributes before each test.
     */
    @BeforeEach
    void setUp() {
        obstacle = new Obstacle("default.png", 100, 200, 1.0, 80, 40);
    }

    /**
     * Tests that the obstacle is initialized with the correct attributes.
     */
    @Test
    void testObstacleInitialization() {
        assertEquals(100, obstacle.getX(), "Obstacle X position should be initialized correctly.");
        assertEquals(200, obstacle.getY(), "Obstacle Y position should be initialized correctly.");
        assertEquals(1.0, obstacle.getSpeed(), "Obstacle speed should be initialized correctly.");
    }

    /**
     * Tests that the obstacle moves correctly within bounds during an update cycle.
     */
    @Test
    void testMoveWithinBounds() {
        obstacle.act(0); // Simulate one act cycle
        assertEquals(101, obstacle.getX(), "Obstacle X position should update based on speed.");
        assertEquals(200, obstacle.getY(), "Obstacle Y position should remain unchanged.");
    }

    /**
     * Tests that the obstacle resets its position when it moves out of the defined bounds.
     */
    @Test
    void testResetPositionWhenOutOfBounds() {
        obstacle.setX(700); // Move the obstacle beyond the right boundary
        obstacle.act(0); // Simulate one act cycle
        assertTrue(obstacle.getX() <= 600, "Obstacle should reset position when out of bounds.");
    }
}
