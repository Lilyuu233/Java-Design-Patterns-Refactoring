package uk.ac.nott.cs.comp2013.froggergame.controller;

import javafx.animation.AnimationTimer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.function.BiConsumer;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import uk.ac.nott.cs.comp2013.froggergame.controller.timer.TimerManager;
import uk.ac.nott.cs.comp2013.froggergame.model.base.Actor;
import uk.ac.nott.cs.comp2013.froggergame.view.scenes.World;

class TimerManagerTest {

    private TimerManager timerManager;
    private BiConsumer<Long, Actor> mockActorProcessor;

    static class TestWorld extends World {
        @Override
        public void act(long now) {
            // Mock or empty implementation
        }
    }

    @BeforeEach
    void setUp() {
        TestWorld testWorld = new TestWorld();
        mockActorProcessor = mock(BiConsumer.class);
        timerManager = new TimerManager(testWorld, mockActorProcessor);
    }

    @Test
    void testInitializeTimer() {
        timerManager.initializeTimer();
        assertNotNull(getPrivateTimer(timerManager));
    }

    @Test
    void testStartTimerWithoutInitialization() {
        // Expect an IllegalStateException
        Exception exception = assertThrows(IllegalStateException.class, timerManager::startTimer);
        assertEquals("Timer not initialized. Call initializeTimer() first.", exception.getMessage());
    }

    @Test
    void testStartAndStopTimer() {
        timerManager.initializeTimer();
        AnimationTimer timer = spy(getPrivateTimer(timerManager));
        setPrivateTimer(timerManager, timer);

        timerManager.startTimer();
        verify(timer).start();

        timerManager.stopTimer();
        verify(timer).stop();
    }

    @Test
    void testProcessActors() {
        Actor actor1 = new Actor(0, 0, 0) {
            @Override
            public void act(long now) {}
        };
        Actor actor2 = new Actor(0, 0, 0) {
            @Override
            public void act(long now) {}
        };
        List<Actor> actors = List.of(actor1, actor2);

        timerManager.processActors(100L, actors);

        verify(mockActorProcessor).accept(100L, actor1);
        verify(mockActorProcessor).accept(100L, actor2);
    }

    @Test
    void testProcessActorsWithNullActors() {
        timerManager.processActors(100L, null);
        // Ensure no interaction with the actor processor
        verifyNoInteractions(mockActorProcessor);
    }

    @Test
    void testProcessActorsWithEmptyList() {
        timerManager.processActors(100L, List.of());
        // Ensure no interaction with the actor processor
        verifyNoInteractions(mockActorProcessor);
    }

    // Helper methods to access private fields

    private AnimationTimer getPrivateTimer(TimerManager timerManager) {
        try {
            var field = TimerManager.class.getDeclaredField("timer");
            field.setAccessible(true);
            return (AnimationTimer) field.get(timerManager);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setPrivateTimer(TimerManager timerManager, AnimationTimer timer) {
        try {
            var field = TimerManager.class.getDeclaredField("timer");
            field.setAccessible(true);
            field.set(timerManager, timer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
