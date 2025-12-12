package uk.ac.nott.cs.comp2013.froggergame.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.animation.DeathAnimationHandler;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.animation.DeathAnimationHandler.AnimationState;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.animation.DeathAnimationContext;
import uk.ac.nott.cs.comp2013.froggergame.view.resource.ImageManager;

class DeathAnimationHandlerTest {
    private DeathAnimationHandler deathAnimationHandler;
    private Animal mockAnimal;
    private ImageManager mockImageManager;
    private DeathAnimationContext context;

    @BeforeEach
    void setUp() {
        deathAnimationHandler = new DeathAnimationHandler(5);

        // Mock dependencies
        mockAnimal = mock(Animal.class);
        mockImageManager = mock(ImageManager.class);
        context = mock(DeathAnimationContext.class);

        // Configure mocks
        when(context.animal()).thenReturn(mockAnimal);
        when(context.imageManager()).thenReturn(mockImageManager);
        when(context.type()).thenReturn("car");
        when(context.frameLimit()).thenReturn(4);
    }

    @Test
    void testAnimateDeathInProgress() {
        // Allow dynamic matching for frame indices
        when(mockImageManager.getDeathImage(eq("car"), anyInt())).thenReturn(null);

        AnimationState state = deathAnimationHandler.animateDeath(5, context);

        assertEquals(AnimationState.IN_PROGRESS, state);

        // Verify the method is called with any frame index
        verify(mockImageManager).getDeathImage(eq("car"), anyInt());
        verify(mockAnimal).setImage(null);
    }

    @Test
    void testReset() {
        // Simulate animation progression
        deathAnimationHandler.animateDeath(5, context);

        // Reset the handler
        deathAnimationHandler.reset();

        // Verify that the frame counter is reset
        AnimationState state = deathAnimationHandler.animateDeath(5, context);
        assertEquals(AnimationState.IN_PROGRESS, state);
    }
}
