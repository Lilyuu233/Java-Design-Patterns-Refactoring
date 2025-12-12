package uk.ac.nott.cs.comp2013.froggergame.model;

import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import javafx.scene.input.KeyCode;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.animation.DeathAnimationHandler;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.AnimalStateManager;
import uk.ac.nott.cs.comp2013.froggergame.view.resource.ImageManager;

/**
 * Unit tests for {@link AnimalStateManager}.
 * This class ensures the proper functionality of the animal state management logic.
 */
class AnimalStateManagerTest {

    private AnimalStateManager stateManager;
    private Animal mockAnimal;
    private ImageManager mockImageManager;
    private DeathAnimationHandler mockDeathAnimationHandler;

    /**
     * Sets up the test environment before each test case.
     * Uses reflection to inject mocked dependencies.
     */
    @BeforeEach
    void setUp() throws Exception {
        mockAnimal = mock(Animal.class);
        mockImageManager = mock(ImageManager.class);
        mockDeathAnimationHandler = mock(DeathAnimationHandler.class);

        stateManager = new AnimalStateManager(mockAnimal, 5);

        // Use reflection to replace private fields with mocks
        Field imageManagerField = AnimalStateManager.class.getDeclaredField("imageManager");
        imageManagerField.setAccessible(true);
        imageManagerField.set(stateManager, mockImageManager);

        Field handlerField = AnimalStateManager.class.getDeclaredField("deathAnimationHandler");
        handlerField.setAccessible(true);
        handlerField.set(stateManager, mockDeathAnimationHandler);
    }

    /**
     * Tests the reset functionality of {@link AnimalStateManager}.
     * Ensures the state is correctly reset to initial values.
     */
    @Test
    void testReset() {
        stateManager.reset();
        assertFalse(stateManager.isNoMove());
        assertEquals(0, stateManager.getPoints());
    }

    /**
     * Tests updating the death animation state.
     * Verifies that the animation state is updated and `noMove` is set correctly.
     */
    @Test
    void testUpdateDeathAnimation() {
        assertNotNull(mockDeathAnimationHandler);

        when(mockDeathAnimationHandler.animateDeath(anyLong(), any()))
                .thenReturn(DeathAnimationHandler.AnimationState.IN_PROGRESS);

        stateManager.updateDeathAnimation(System.nanoTime(), "cardeath", 4);

        verify(mockDeathAnimationHandler).animateDeath(anyLong(), any());
        assertTrue(stateManager.isNoMove());
    }

    /**
     * Tests the animal's movement logic.
     * Verifies that the image and position are updated correctly.
     */
    @Test
    void testMoveAnimal() {
        KeyCode key = KeyCode.W;
        Image mockImage = mock(Image.class);
        when(mockImageManager.getImageForKey(key, true)).thenReturn(mockImage);

        stateManager.moveAnimal(10, -10, key, true);

        verify(mockAnimal).setImage(mockImage);
        verify(mockAnimal).move(10, -10);
    }

    /**
     * Tests adding points to the state manager.
     * Ensures that the points are accumulated correctly.
     */
    @Test
    void testAddPoints() {
        stateManager.addPoints(20);
        assertEquals(20, stateManager.getPoints());
    }

    /**
     * Tests the score changed flag functionality.
     * Verifies that the flag is updated correctly after adding points and resetting.
     */
    @Test
    void testHasScoreChanged() {
        assertFalse(stateManager.hasScoreChanged());

        stateManager.addPoints(10);
        assertTrue(stateManager.hasScoreChanged());

        stateManager.resetScoreChangedFlag();
        assertFalse(stateManager.hasScoreChanged());
    }
}
