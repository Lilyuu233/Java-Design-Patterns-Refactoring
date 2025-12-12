package uk.ac.nott.cs.comp2013.froggergame.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Stack;
import static org.junit.jupiter.api.Assertions.*;

import uk.ac.nott.cs.comp2013.froggergame.model.logic.score.ScoreManager;

/**
 * Unit tests for {@link ScoreManager}.
 */
class ScoreManagerTest {

    private ScoreManager scoreManager;

    /**
     * Sets up a new instance of {@link ScoreManager} before each test.
     */
    @BeforeEach
    void setUp() {
        scoreManager = new ScoreManager();
    }

    /**
     * Tests adding positive points to the score.
     */
    @Test
    void testAddPointsPositive() {
        scoreManager.addPoints(20);
        assertEquals(20, scoreManager.getPoints());
        assertTrue(scoreManager.hasScoreChanged());
    }

    /**
     * Tests adding negative points to the score.
     * Ensures that the score does not go below 0.
     */
    @Test
    void testAddPointsNegative() {
        scoreManager.addPoints(-10);
        assertEquals(0, scoreManager.getPoints());
        assertTrue(scoreManager.hasScoreChanged());
    }

    /**
     * Tests adding zero points to the score.
     */
    @Test
    void testAddPointsNoChange() {
        scoreManager.addPoints(0);
        assertEquals(0, scoreManager.getPoints());
        assertFalse(scoreManager.hasScoreChanged());
    }

    /**
     * Tests handling upward movement, which adds points.
     */
    @Test
    void testHandleMovementScoreMoveUp() {
        scoreManager.handleMovementScore(-5.0);
        assertEquals(10, scoreManager.getPoints());
        assertTrue(scoreManager.hasScoreChanged());
    }

    /**
     * Tests handling downward movement, which subtracts points but keeps the score at 0.
     */
    @Test
    void testHandleMovementScoreMoveDown() {
        scoreManager.handleMovementScore(5.0);
        assertEquals(0, scoreManager.getPoints());
        assertTrue(scoreManager.hasScoreChanged());
    }

    /**
     * Tests handling no movement, which leaves the score unchanged.
     */
    @Test
    void testHandleMovementScoreNoMovement() {
        scoreManager.handleMovementScore(0);
        assertEquals(0, scoreManager.getPoints());
        assertFalse(scoreManager.hasScoreChanged());
    }

    /**
     * Tests resetting the score change flag.
     */
    @Test
    void testResetScoreChangedFlag() {
        scoreManager.addPoints(10);
        assertTrue(scoreManager.hasScoreChanged());
        scoreManager.resetScoreChangedFlag();
        assertFalse(scoreManager.hasScoreChanged());
    }

    /**
     * Tests extracting digits from a score of 0.
     */
    @Test
    void testGetDigitsZero() {
        Stack<Integer> digits = scoreManager.getDigits(0);
        assertEquals(1, digits.size());
        assertEquals(0, digits.pop());
    }

    /**
     * Tests extracting digits from a positive score.
     */
    @Test
    void testGetDigitsPositiveNumber() {
        Stack<Integer> digits = scoreManager.getDigits(1234);
        assertEquals(4, digits.size());
        assertEquals(1, digits.pop());
        assertEquals(2, digits.pop());
        assertEquals(3, digits.pop());
        assertEquals(4, digits.pop());
    }

    /**
     * Tests extracting digits from a negative score, which should throw an exception.
     */
    @Test
    void testGetDigitsNegativeNumber() {
        assertThrows(IllegalArgumentException.class, () -> scoreManager.getDigits(-123));
    }
}
