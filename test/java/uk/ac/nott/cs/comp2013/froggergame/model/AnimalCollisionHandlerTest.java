package uk.ac.nott.cs.comp2013.froggergame.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import uk.ac.nott.cs.comp2013.froggergame.JavaFXTestBase;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.*;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.collision.AnimalCollisionHandler;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.StateManager;
import uk.ac.nott.cs.comp2013.froggergame.view.components.End;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Score;

/**
 * Unit tests for {@link AnimalCollisionHandler}.
 * Validates collision handling logic for different entity types and scenarios.
 */
class AnimalCollisionHandlerTest extends JavaFXTestBase {
    private Animal mockAnimal;
    private StateManager mockStateManager;
    private Score mockScore;
    private AnimalCollisionHandler collisionHandler;

    /**
     * Sets up the test environment before each test case.
     * Creates mock objects and initializes the collision handler.
     */
    @BeforeEach
    void setUp() {
        mockAnimal = mock(Animal.class);
        mockStateManager = mock(StateManager.class);
        mockScore = mock(Score.class);
        collisionHandler = new AnimalCollisionHandler(mockAnimal, mockStateManager, mockScore);
    }

    /**
     * Tests collision handling with {@link Obstacle}.
     * Verifies that the animal is marked as dead and points are deducted.
     */
    @Test
    void testObstacleCollision() {
        Obstacle mockObstacle = mock(Obstacle.class);
        when(mockAnimal.getIntersectingObjects(Obstacle.class)).thenReturn(List.of(mockObstacle));

        collisionHandler.checkCollisions();

        verify(mockAnimal).setCarDeath(true);
        verify(mockStateManager).addPoints(-50);
    }

    /**
     * Tests collision handling with {@link Log}.
     * Verifies that the animal's platform is set correctly.
     */
    @Test
    void testLogCollision() {
        Log mockLog = mock(Log.class);
        when(mockAnimal.getIntersectingObjects(Log.class)).thenReturn(List.of(mockLog));

        collisionHandler.checkCollisions();

        verify(mockAnimal).setCurrentPlatform(mockLog);
    }

    /**
     * Tests collision handling with {@link WetTurtle} when it is sunk.
     * Verifies that the animal dies and the score is updated.
     */
    @Test
    void testWetTurtleCollisionWhenSunk() {
        WetTurtle mockWetTurtle = mock(WetTurtle.class);
        when(mockAnimal.getIntersectingObjects(WetTurtle.class)).thenReturn(List.of(mockWetTurtle));
        when(mockWetTurtle.isSunk()).thenReturn(true);

        collisionHandler.checkCollisions();

        verify(mockAnimal).clearCurrentPlatform();
        verify(mockAnimal).setWaterDeath(true);
        verify(mockStateManager).addPoints(-50);
        verify(mockAnimal).resetPosition();
    }

    /**
     * Tests collision handling with {@link End}.
     * Verifies that the endpoint is activated and points are awarded.
     */
    @Test
    void testEndCollision() {
        End mockEnd = mock(End.class);
        when(mockAnimal.getIntersectingObjects(End.class)).thenReturn(List.of(mockEnd));
        when(mockEnd.isActivated()).thenReturn(false);

        collisionHandler.checkCollisions();

        verify(mockEnd).setEnd();
        verify(mockStateManager).addPoints(50);
        verify(mockAnimal).resetPosition();
    }

    /**
     * Tests collision handling with {@link Spider}.
     * Verifies that points are awarded and the spider is removed from its parent pane.
     */
    @Test
    void testSpiderCollision() {
        Spider mockSpider = mock(Spider.class);
        Pane mockPane = mock(Pane.class);
        Log mockLog = mock(Log.class);

        ObservableList<Node> mockChildren = FXCollections.observableArrayList();
        when(mockPane.getChildren()).thenReturn(mockChildren);

        when(mockAnimal.getIntersectingObjects(Spider.class)).thenReturn(List.of(mockSpider));
        when(mockSpider.getParentPane()).thenReturn(mockPane);
        when(mockSpider.getParentLog()).thenReturn(mockLog);

        mockChildren.add(mockSpider);

        collisionHandler.checkCollisions();

        verify(mockStateManager).addPoints(20);
        verify(mockScore).updateScore(anyInt());
        assertFalse(mockChildren.contains(mockSpider), "Spider should be removed from parent pane.");
        verify(mockLog).clearSpider();
    }

    /**
     * Tests behavior when there are no collisions.
     * Verifies that the animal's platform is cleared.
     */
    @Test
    void testNoCollisionClearsPlatform() {
        when(mockAnimal.isOnPlatform()).thenReturn(true);
        when(mockAnimal.getIntersectingObjects(any())).thenReturn(List.of());

        collisionHandler.checkCollisions();

        verify(mockAnimal).clearCurrentPlatform();
    }
}
