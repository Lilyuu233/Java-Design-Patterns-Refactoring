package uk.ac.nott.cs.comp2013.froggergame.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uk.ac.nott.cs.comp2013.froggergame.JavaFXTestBase;
import uk.ac.nott.cs.comp2013.froggergame.controller.initializer.GameInitializer;
import uk.ac.nott.cs.comp2013.froggergame.model.data.GameObjectData;
import uk.ac.nott.cs.comp2013.froggergame.model.factory.GameObjectFactory;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.score.ScoreManager;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Score;
import uk.ac.nott.cs.comp2013.froggergame.view.scenes.MyStage;
import static org.mockito.Mockito.*;

class GameInitializerTest extends JavaFXTestBase {

    private GameInitializer gameInitializer;
    private MyStage mockBackground;
    private ScoreManager mockScoreManager;
    private GameObjectFactory mockGameObjectFactory;
    private Score mockScoreView;

    @BeforeEach
    void setUp() {
        // Mock dependencies
        mockBackground = mock(MyStage.class);
        mockScoreManager = mock(ScoreManager.class);
        mockGameObjectFactory = mock(GameObjectFactory.class);
        mockScoreView = mock(Score.class);

        // Mock getChildren() to return an observable list
        ObservableList<Node> mockChildren = FXCollections.observableArrayList();
        when(mockBackground.getChildren()).thenReturn(mockChildren);

        // Initialize GameInitializer
        gameInitializer = new GameInitializer(mockBackground, mockScoreManager, mockGameObjectFactory);
    }

    @Test
    void testInitializeComponents() {
        // Act
        gameInitializer.initializeComponents(mockScoreView);

        // Verify setupBackground interaction
        verify(mockBackground, atLeastOnce()).getChildren();

        // Verify setupGameObjects interaction
        verify(mockGameObjectFactory).setupLogs(eq(mockBackground), any());
        verify(mockGameObjectFactory).setupTurtles(eq(mockBackground), any());
        verify(mockGameObjectFactory).setupObstacles(eq(mockBackground), any());

        // Verify setupScoreDisplay interaction
        verify(mockScoreView).createScoreLabel(eq(mockBackground));
        verify(mockScoreView).updateScoreDisplay(eq(mockBackground), eq(0), eq(mockScoreManager));
    }

    @Test
    void testSetupBackground() {
        gameInitializer.setupBackground();
        verify(mockBackground, times(1)).getChildren();
    }

    @Test
    void testSetupEndPoints() {
        gameInitializer.setupEndPoints();
        verify(mockBackground, atLeast(GameObjectData.END_POINT_DATA.length)).getChildren();
    }

    @Test
    void testSetupGameObjects() {
        gameInitializer.initializeComponents(mockScoreView);

        // Verify interactions with GameObjectFactory
        verify(mockGameObjectFactory).setupLogs(eq(mockBackground), any());
        verify(mockGameObjectFactory).setupTurtles(eq(mockBackground), any());
        verify(mockGameObjectFactory).setupObstacles(eq(mockBackground), any());
    }

    @Test
    void testSetupScoreDisplay() {
        gameInitializer.initializeComponents(mockScoreView);

        verify(mockScoreView).createScoreLabel(eq(mockBackground));
        verify(mockScoreView).updateScoreDisplay(eq(mockBackground), eq(0), eq(mockScoreManager));
    }
}
