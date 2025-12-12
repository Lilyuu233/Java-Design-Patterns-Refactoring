package uk.ac.nott.cs.comp2013.froggergame.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import uk.ac.nott.cs.comp2013.froggergame.model.logic.collision.BoundaryManager;

class BoundaryManagerTest {

    private BoundaryManager boundaryManager;

    @BeforeEach
    void setUp() {
        // Initialize BoundaryManager with test bounds and reset positions
        boundaryManager = new BoundaryManager(-100, 100, -50, 50);
    }

    @Test
    void testIsOutOfBounds_PositiveSpeed_OutOfBoundsRight() {
        // Position is beyond right bound, speed is positive
        assertTrue(boundaryManager.isOutOfBounds(150, 10));
    }

    @Test
    void testIsOutOfBounds_NegativeSpeed_OutOfBoundsLeft() {
        // Position is beyond left bound, speed is negative
        assertTrue(boundaryManager.isOutOfBounds(-150, -10));
    }

    @Test
    void testIsOutOfBounds_PositiveSpeed_WithinBounds() {
        // Position is within bounds, speed is positive
        assertFalse(boundaryManager.isOutOfBounds(50, 10));
    }

    @Test
    void testIsOutOfBounds_NegativeSpeed_WithinBounds() {
        // Position is within bounds, speed is negative
        assertFalse(boundaryManager.isOutOfBounds(-50, -10));
    }

    @Test
    void testResetPositionBasedOnSpeed_PositiveSpeed() {
        // Speed is positive, should return resetRightPosition
        assertEquals(50, boundaryManager.resetPositionBasedOnSpeed(10));
    }

    @Test
    void testResetPositionBasedOnSpeed_NegativeSpeed() {
        // Speed is negative, should return resetLeftPosition
        assertEquals(-50, boundaryManager.resetPositionBasedOnSpeed(-10));
    }

    @Test
    void testResetPositionBasedOnSpeed_ZeroSpeed() {
        // Speed is zero, should return 0
        assertEquals(0, boundaryManager.resetPositionBasedOnSpeed(0));
    }
}
