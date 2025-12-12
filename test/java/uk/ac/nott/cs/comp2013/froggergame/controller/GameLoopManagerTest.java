package uk.ac.nott.cs.comp2013.froggergame.controller;

import javafx.animation.AnimationTimer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.nott.cs.comp2013.froggergame.JavaFXTestBase;
import uk.ac.nott.cs.comp2013.froggergame.controller.core.GameEventListener;
import uk.ac.nott.cs.comp2013.froggergame.controller.loop.GameLoopManager;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.GameStateChecker;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class GameLoopManagerTest extends JavaFXTestBase {

    private GameLoopManager gameLoopManager;
    private Animal mockAnimal;
    private GameStateChecker mockGameStateChecker;
    private GameEventListener mockEventListener;

    @BeforeEach
    void setUp() {
        mockAnimal = mock(Animal.class);
        mockGameStateChecker = mock(GameStateChecker.class);
        mockEventListener = mock(GameEventListener.class);

        gameLoopManager = new GameLoopManager(mockAnimal, mockGameStateChecker);
        gameLoopManager.setGameEventListener(mockEventListener);
    }

    @Test
    void testStartGameLoopExecution() {
        gameLoopManager.startGameLoopExecution();
        assertNotNull(getPrivateField(gameLoopManager, "gameLoop"), "GameLoop should be initialized");
    }

    @Test
    void testStopGameLoopExecution() {
        gameLoopManager.startGameLoopExecution();
        AnimationTimer gameLoop = (AnimationTimer) getPrivateField(gameLoopManager, "gameLoop");
        assertNotNull(gameLoop, "GameLoop should be initialized");
        gameLoopManager.stopGameLoopExecution();
        verify(mockAnimal, never()).isActive(); // Ensures no unintended logic during stop
    }

    @Test
    void testCheckGameState() {
        // Arrange
        when(mockAnimal.isActive()).thenReturn(true);

        // Act
        gameLoopManager.startGameLoopExecution();
        callPrivateMethod(gameLoopManager, "checkGameState");

        // Assert
        verify(mockGameStateChecker).checkGameState(mockAnimal, mockEventListener);
    }

    @Test
    void testExecuteGameLoop() {
        long now = System.nanoTime();
        setPrivateField(gameLoopManager, "lastUpdateTime", now);

        when(mockAnimal.isActive()).thenReturn(true);

        callPrivateMethod(gameLoopManager, "executeGameLoop", now);

        // Verify that `update` and `checkGameState` are called correctly
        verify(mockEventListener).update(now);
        verify(mockGameStateChecker).checkGameState(mockAnimal, mockEventListener);
    }

    // Utility methods for accessing private fields and methods
    private Object getPrivateField(Object object, String fieldName) {
        try {
            var field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setPrivateField(Object object, String fieldName, Object value) {
        try {
            var field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void callPrivateMethod(Object object, String methodName, Object... args) {
        try {
            Class<?>[] parameterTypes = new Class<?>[args.length];
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof Long) {
                    parameterTypes[i] = long.class;
                } else {
                    parameterTypes[i] = args[i].getClass();
                }
            }

            var method = object.getClass().getDeclaredMethod(methodName, parameterTypes);
            method.setAccessible(true);
            method.invoke(object, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
