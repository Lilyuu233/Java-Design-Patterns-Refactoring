package uk.ac.nott.cs.comp2013.froggergame.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import uk.ac.nott.cs.comp2013.froggergame.controller.core.GameEventListener;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.GameStateChecker;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.StateManager;

class GameStateCheckerTest {
    private GameStateChecker gameStateChecker;
    private Animal mockAnimal;
    private StateManager mockStateManager;
    private GameEventListener mockEventListener;

    @BeforeEach
    void setUp() {
        gameStateChecker = new GameStateChecker();
        mockAnimal = mock(Animal.class);
        mockStateManager = mock(StateManager.class);
        mockEventListener = mock(GameEventListener.class);

        // Mock the behavior of the animal and its state manager
        when(mockAnimal.getStateManager()).thenReturn(mockStateManager);
    }

    @Test
    void testCheckGameStateVictory() {
        // Simulate victory
        when(mockStateManager.isVictory()).thenReturn(true);

        gameStateChecker.checkGameState(mockAnimal, mockEventListener);

        verify(mockStateManager).isVictory();
        verify(mockEventListener).onGameWon(anyInt()); // Ensure victory is notified
        verify(mockEventListener, never()).onGameOver(); // Ensure game over is not notified
    }

    @Test
    void testCheckGameStateGameOver() {
        // Simulate game over
        when(mockStateManager.isVictory()).thenReturn(false);
        when(mockAnimal.isDead()).thenReturn(true);

        gameStateChecker.checkGameState(mockAnimal, mockEventListener);

        verify(mockStateManager).isVictory();
        verify(mockAnimal).isDead();
        verify(mockEventListener).onGameOver(); // Ensure game over is notified
        verify(mockEventListener, never()).onGameWon(anyInt()); // Ensure victory is not notified
    }

    @Test
    void testCheckGameStateNoEventTriggered() {
        // Simulate neither victory nor game over
        when(mockStateManager.isVictory()).thenReturn(false);
        when(mockAnimal.isDead()).thenReturn(false);

        gameStateChecker.checkGameState(mockAnimal, mockEventListener);

        verify(mockStateManager).isVictory();
        verify(mockAnimal).isDead();
        verify(mockEventListener, never()).onGameOver(); // Ensure game over is not notified
        verify(mockEventListener, never()).onGameWon(anyInt()); // Ensure victory is not notified
    }
}

