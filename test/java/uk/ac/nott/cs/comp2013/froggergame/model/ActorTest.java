package uk.ac.nott.cs.comp2013.froggergame.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uk.ac.nott.cs.comp2013.froggergame.model.base.Actor;

/**
 * Unit tests for the {@link Actor} class.
 * Ensures correct initialization and behavior of core methods such as movement,
 * speed adjustment, bounds checking, and interaction with the world.
 */
class ActorTest {
    private Actor actor;

    /**
     * Sets up a test instance of {@link Actor} before each test.
     * Uses an anonymous subclass since {@link Actor} is abstract.
     */
    @BeforeEach
    void setUp() {
        actor = new Actor(10, 20, 5.0) {
            @Override
            public void act(long now) {
                // No behavior for testing
            }
        };
    }

    /**
     * Tests that the {@link Actor} is correctly initialized with the given parameters.
     */
    @Test
    void testInitialization() {
        assertEquals(10, actor.getX(), "Actor X-coordinate should be initialized correctly.");
        assertEquals(20, actor.getY(), "Actor Y-coordinate should be initialized correctly.");
        assertEquals(5.0, actor.getSpeed(), "Actor speed should be initialized correctly.");
    }

    /**
     * Tests the movement functionality of the {@link Actor}.
     * Ensures the actor's position is updated correctly.
     */
    @Test
    void testMove() {
        actor.move(5, -10); // Move right by 5 and up by 10
        assertEquals(15, actor.getX(), "Actor X-coordinate should be updated after moving.");
        assertEquals(10, actor.getY(), "Actor Y-coordinate should be updated after moving.");
    }

    /**
     * Tests the speed factor adjustment of the {@link Actor}.
     * Ensures the actor's speed is updated proportionally.
     */
    @Test
    void testSetSpeedFactor() {
        actor.setSpeedFactor(2.0); // Double the speed
        assertEquals(10.0, actor.getSpeed(), "Actor speed should be updated based on the speed factor.");
    }

    /**
     * Tests bounds configuration and automatic reset functionality of the {@link Actor}.
     * Ensures that the actor is reset to the correct position when it goes out of bounds.
     */
    @Test
    void testConfigureBoundsAndReset() {
        actor.configureBounds(0, 100, 0, 0); // Configure bounds
        actor.setX(150); // Set position beyond the right bound
        actor.resetPositionIfOutOfBounds(1.0); // Simulate movement outside bounds
        assertEquals(0, actor.getX(), "Actor should reset to the left bound when out of bounds.");
    }

    /**
     * Tests that an exception is thrown if {@link Actor#getWorld()} is called
     * without attaching the actor to a world.
     */
    @Test
    void testGetWorldWithoutSettingThrowsException() {
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                actor::getWorld,
                "Calling getWorld without setting a world should throw an exception."
        );
        assertEquals("Actor is not attached to any World instance.", exception.getMessage(),
                "Exception message should indicate that the actor is not attached to a world.");
    }
}
