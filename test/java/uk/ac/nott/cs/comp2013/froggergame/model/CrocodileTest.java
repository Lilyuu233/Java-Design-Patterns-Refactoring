package uk.ac.nott.cs.comp2013.froggergame.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import uk.ac.nott.cs.comp2013.froggergame.model.entities.Crocodile;

class CrocodileTest {
    private Crocodile crocodile;

    @BeforeEach
    void setUp() {
        crocodile = new Crocodile(100, 50, 1.0);
    }

    @Test
    void testInitialPosition() {
        assertEquals(100, crocodile.getX());
        assertEquals(50, crocodile.getY());
        assertTrue(crocodile.isMovingRight());
    }

    @Test
    void testMoveRightWithinBounds() {
        crocodile.act(0); // Simulate one "tick"
        assertEquals(101.0, crocodile.getX()); // Moves right by SPEED (1.0)
        assertEquals(50.0, crocodile.getY()); // Y-coordinate remains unchanged
        assertTrue(crocodile.isMovingRight());
    }

    @Test
    void testChangeDirectionAtRightBound() {
        crocodile.setX(500); // Set crocodile at the right bound
        crocodile.act(0); // Should reverse direction
        assertFalse(crocodile.isMovingRight());
    }

    @Test
    void testMoveLeft() {
        crocodile.setX(500); // Place at the right bound
        crocodile.setMovingRight(false); // Start moving left
        crocodile.act(0); // Simulate one "tick"
        assertEquals(499.0, crocodile.getX()); // Moves left by SPEED (1.0)
        assertFalse(crocodile.isMovingRight());
    }

    @Test
    void testChangeDirectionAtLeftBound() {
        crocodile.setX(100); // Set crocodile at the initial position (left bound)
        crocodile.setMovingRight(false); // Start moving left
        crocodile.act(0); // Should reverse direction
        assertTrue(crocodile.isMovingRight());
    }

    @Test
    void testYPositionUnchanged() {
        double initialY = crocodile.getY();
        crocodile.act(0); // Simulate movement
        assertEquals(initialY, crocodile.getY()); // Y-coordinate should remain the same
    }
}

