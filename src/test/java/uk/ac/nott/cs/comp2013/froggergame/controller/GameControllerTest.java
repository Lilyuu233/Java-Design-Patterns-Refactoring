package uk.ac.nott.cs.comp2013.froggergame.controller;

import javafx.scene.Scene;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import uk.ac.nott.cs.comp2013.froggergame.controller.core.GameController;
import uk.ac.nott.cs.comp2013.froggergame.controller.core.GameControllerConfig;
import uk.ac.nott.cs.comp2013.froggergame.controller.initializer.GameInitializer;
import uk.ac.nott.cs.comp2013.froggergame.controller.loop.GameLoopManager;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import uk.ac.nott.cs.comp2013.froggergame.model.factory.GameObjectFactory;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.level.LevelManager;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.score.ScoreManager;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.GameStateChecker;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.StateManager;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Level;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Score;
import uk.ac.nott.cs.comp2013.froggergame.view.components.TimeBar;
import uk.ac.nott.cs.comp2013.froggergame.view.scenes.MyStage;

/**
 * Unit tests for the GameController class, ensuring proper behavior of its methods.
 */
class GameControllerTest {

    private GameController gameController;
    private MyStage mockBackground;
    private GameInitializer mockGameInitializer;
    private Animal mockAnimal;
    private Score mockScoreView;
    private GameLoopManager mockGameLoopManager;

    /**
     * Sets up mock dependencies and initializes the GameController before each test.
     */
    @BeforeEach
    void setUp() {
        // Mock dependencies
        mockBackground = mock(MyStage.class);
        ScoreManager mockScoreManager = mock(ScoreManager.class);
        GameObjectFactory mockGameObjectFactory = mock(GameObjectFactory.class);
        mockGameInitializer = mock(GameInitializer.class);
        GameStateChecker mockGameStateChecker = mock(GameStateChecker.class);
        mockScoreView = mock(Score.class);
        mockAnimal = mock(Animal.class);

        when(mockGameObjectFactory.createAnimal()).thenReturn(mockAnimal);

        TimeBar mockTimeBar = mock(TimeBar.class);
        when(mockBackground.getTimeBar()).thenReturn(mockTimeBar);
        when(mockBackground.getStylesheets()).thenReturn(javafx.collections.FXCollections.observableArrayList());
        when(mockBackground.getChildren()).thenReturn(javafx.collections.FXCollections.observableArrayList());

        // Initialize GameController with a mock configuration
        GameControllerConfig config = new GameControllerConfig(
                mockBackground,
                mockScoreManager,
                mockGameObjectFactory,
                mockGameStateChecker,
                mockScoreView,
                mockGameInitializer
        );
        gameController = new GameController(config);

        // Mock GameLoopManager
        mockGameLoopManager = mock(GameLoopManager.class);
        gameController.setGameLoopManager(mockGameLoopManager);
    }

    /**
     * Tests the initializeGame method to ensure components are initialized correctly.
     */
    @Test
    void testInitializeGame() {
        gameController.initializeGame();

        // Verify that components are initialized as expected
        verify(mockGameInitializer).initializeComponents(mockScoreView);
        verify(mockBackground.getTimeBar()).setTimeOutListener(any());

        assertNotNull(mockBackground.getTimeBar());
    }

    /**
     * Tests the startGameLoop method to ensure the game loop starts correctly.
     */
    @Test
    void testStartGameLoop() {
        gameController.startGameLoop();

        // Verify that the game loop and time bar are started
        verify(mockBackground.getTimeBar()).start();
        verify(mockGameLoopManager).startGameLoopExecution();
    }

    /**
     * Tests the stopGame method to ensure the game loop stops correctly.
     */
    @Test
    void testStopGame() {
        gameController.stopGame();

        // Verify that the game loop and other components are stopped
        verify(mockGameLoopManager).stopGameLoopExecution();
        verify(mockBackground.getTimeBar()).stop();
        verify(mockBackground).stopMusic();
    }

    /**
     * Tests the configureInputHandling method to ensure input handling is configured.
     */
    @Test
    void testConfigureInputHandling() {
        Scene mockScene = mock(Scene.class);
        gameController.configureInputHandling(mockScene);

        // Verify that key press and release handlers are set
        verify(mockScene).setOnKeyPressed(any());
        verify(mockScene).setOnKeyReleased(any());
    }

    /**
     * Tests the updateGameState method to ensure the game state is updated correctly.
     */
    @Test
    void testUpdateGameState() {
        // Mock StateManager
        StateManager mockStateManager = mock(StateManager.class);
        when(mockAnimal.getStateManager()).thenReturn(mockStateManager);
        when(mockStateManager.hasScoreChanged()).thenReturn(true);

        // Mock LevelManager and Level
        LevelManager mockLevelManager = mock(LevelManager.class);
        Level mockLevel = mock(Level.class);
        when(mockLevelManager.hasLevelChanged()).thenReturn(true);
        when(mockLevelManager.getLevelView()).thenReturn(mockLevel);

        // Inject mocks into GameController
        gameController.setAnimal(mockAnimal);
        gameController.setLevelManager(mockLevelManager);

        // Call the method under test
        gameController.updateGameState();

        // Verify interactions with mocks
        verify(mockStateManager).hasScoreChanged();
        verify(mockStateManager).resetScoreChangedFlag();
        verify(mockLevelManager).hasLevelChanged();
        verify(mockLevelManager).resetLevelChangedFlag();
        verify(mockLevel).updateLevelDisplay(anyInt());
    }
}
