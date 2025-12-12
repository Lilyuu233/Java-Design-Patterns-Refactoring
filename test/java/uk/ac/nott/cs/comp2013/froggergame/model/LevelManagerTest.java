package uk.ac.nott.cs.comp2013.froggergame.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import uk.ac.nott.cs.comp2013.froggergame.controller.core.GameEventListener;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.level.LevelManager;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.StateManager;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Level;
import uk.ac.nott.cs.comp2013.froggergame.view.components.TimeBar;

/**
 * Unit tests for {@link LevelManager}.
 * Ensures proper handling of level progression, game win conditions, and state resets.
 */
class LevelManagerTest {

    private Animal mockAnimal;
    private Level mockLevelView;
    private TimeBar mockTimeBar;
    private GameEventListener mockEventListener;
    private LevelManager levelManager;

    /**
     * Sets up mock objects and initializes the {@link LevelManager} before each test.
     */
    @BeforeEach
    void setUp() {
        mockAnimal = mock(Animal.class);
        StateManager mockStateManager = mock(StateManager.class);

        when(mockAnimal.getStateManager()).thenReturn(mockStateManager);
        when(mockStateManager.getPoints()).thenReturn(100);

        mockLevelView = mock(Level.class);
        mockTimeBar = mock(TimeBar.class);
        mockEventListener = mock(GameEventListener.class);

        levelManager = new LevelManager(mockAnimal, mockLevelView, 3, mockTimeBar, mockEventListener);
    }

    /**
     * Verifies the initial state of the level manager.
     */
    @Test
    void testInitialLevelState() {
        assertEquals(1, levelManager.getCurrentLevel());
        assertFalse(levelManager.hasLevelChanged());
    }

    /**
     * Tests setting a new level and ensures the level changed flag is updated.
     */
    @Test
    void testSetLevel() {
        levelManager.setLevel(2);
        assertTrue(levelManager.hasLevelChanged());
        assertEquals(2, levelManager.getCurrentLevel());
    }

    /**
     * Tests incrementing the level and ensures the level view is updated.
     */
    @Test
    void testIncreaseLevel() {
        levelManager.increaseLevel();
        assertEquals(2, levelManager.getCurrentLevel());
        verify(mockLevelView).updateLevelDisplay(2);
        verify(mockLevelView).resetLevelLayout(any());
    }

    /**
     * Simulates reaching all targets in a level and verifies proper handling.
     */
    @Test
    void testOnTargetReached_LevelComplete() {
        for (int i = 0; i < 3; i++) {
            levelManager.onTargetReached();
        }

        verify(mockLevelView).updateLevelDisplay(2);
        verify(mockAnimal).resetPosition();
        verify(mockTimeBar).reset();
    }

    /**
     * Simulates completing the game by reaching all targets in the final level.
     */
    @Test
    void testOnTargetReached_GameWon() {
        levelManager.setLevel(2);
        for (int i = 0; i < 3; i++) {
            levelManager.onTargetReached();
        }

        verify(mockEventListener).onGameWon(anyInt());
    }

    /**
     * Tests resetting the animal's position.
     */
    @Test
    void testResetAnimalPosition() {
        levelManager.resetAnimalPosition();
        verify(mockAnimal).setX(300);
        verify(mockAnimal).setY(733);
    }

    /**
     * Verifies that the speed of objects is updated correctly.
     */
    @Test
    void testUpdateObjectsSpeedSimplified() {
        when(mockLevelView.getActors()).thenReturn(List.of());

        levelManager.updateObjectsSpeed(1.5);

        verify(mockLevelView).getActors(); // Ensures the method attempts to fetch actors
    }
}
