package uk.ac.nott.cs.comp2013.froggergame.controller;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import uk.ac.nott.cs.comp2013.froggergame.controller.input.InputHandler;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.StateManager;

class InputHandlerTest {

    private InputHandler inputHandler;
    private Animal mockAnimal;
    private StateManager mockStateManager;

    @BeforeEach
    void setUp() {
        mockAnimal = mock(Animal.class);
        mockStateManager = mock(StateManager.class);

        // Mock Animal's StateManager
        when(mockAnimal.getStateManager()).thenReturn(mockStateManager);

        inputHandler = new InputHandler(mockAnimal);
    }

    @Test
    void testHandleKeyPressed_NoMove() {
        // Simulate "no move" state
        when(mockStateManager.isNoMove()).thenReturn(true);

        KeyEvent keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.W, false, false, false, false);
        inputHandler.handleKeyPressed(keyEvent);

        // Verify no interaction happens when isNoMove() is true
        verify(mockAnimal, never()).getMovementX(any());
        verify(mockAnimal, never()).getMovementY(any());
        verify(mockStateManager, never()).moveAnimal(anyDouble(), anyDouble(), any(), anyBoolean());
    }

    @Test
    void testHandleKeyPressed_NormalMove() {
        // Simulate "can move" state
        when(mockStateManager.isNoMove()).thenReturn(false);
        when(mockAnimal.getMovementX(KeyCode.W)).thenReturn(0.0);
        when(mockAnimal.getMovementY(KeyCode.W)).thenReturn(-10.0);

        KeyEvent keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.W, false, false, false, false);
        inputHandler.handleKeyPressed(keyEvent);

        // Verify the movement logic is triggered
        verify(mockAnimal).getMovementX(KeyCode.W);
        verify(mockAnimal).getMovementY(KeyCode.W);
        verify(mockStateManager).moveAnimal(0.0, -10.0, KeyCode.W, true);
    }

    @Test
    void testHandleKeyReleased_NoMove() {
        // Simulate "no move" state
        when(mockStateManager.isNoMove()).thenReturn(true);

        KeyEvent keyEvent = new KeyEvent(KeyEvent.KEY_RELEASED, "", "", KeyCode.W, false, false, false, false);
        inputHandler.handleKeyReleased(keyEvent);

        // Verify no interaction happens when isNoMove() is true
        verify(mockAnimal, never()).getMovementX(any());
        verify(mockAnimal, never()).getMovementY(any());
        verify(mockStateManager, never()).moveAnimal(anyDouble(), anyDouble(), any(), anyBoolean());
    }

    @Test
    void testHandleKeyReleased_NormalMove() {
        // Simulate "can move" state
        when(mockStateManager.isNoMove()).thenReturn(false);
        when(mockAnimal.getMovementX(KeyCode.W)).thenReturn(0.0);
        when(mockAnimal.getMovementY(KeyCode.W)).thenReturn(-10.0);

        KeyEvent keyEvent = new KeyEvent(KeyEvent.KEY_RELEASED, "", "", KeyCode.W, false, false, false, false);
        inputHandler.handleKeyReleased(keyEvent);

        // Verify the movement logic is triggered
        verify(mockAnimal).getMovementX(KeyCode.W);
        verify(mockAnimal).getMovementY(KeyCode.W);
        verify(mockStateManager).moveAnimal(0.0, -10.0, KeyCode.W, false);
    }
}

