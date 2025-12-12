package uk.ac.nott.cs.comp2013.froggergame.controller.timer;

import javafx.animation.AnimationTimer;
import java.util.List;
import java.util.function.BiConsumer;

import uk.ac.nott.cs.comp2013.froggergame.model.base.Actor;
import uk.ac.nott.cs.comp2013.froggergame.view.scenes.World;

/**
 * Manages a timer for updating game actors and the world at regular intervals.
 * The TimerManager uses an AnimationTimer to drive game logic updates and processes actors dynamically.
 */
public class TimerManager {
    private final World world; // The game world containing all actors
    private AnimationTimer timer; // Timer used to trigger updates
    private final BiConsumer<Long, Actor> actorProcessor; // Processor for handling actor updates

    /**
     * Constructs a TimerManager with the specified game world and actor processing logic.
     *
     * @param world          The game world to be updated by the timer.
     * @param actorProcessor A consumer that defines the logic for processing each actor.
     */
    public TimerManager(World world, BiConsumer<Long, Actor> actorProcessor) {
        this.world = world;
        this.actorProcessor = actorProcessor;
    }

    /**
     * Initializes the timer, creating it if it does not already exist.
     */
    public void initializeTimer() {
        if (timer == null) {
            timer = createTimer();
        }
    }

    /**
     * Starts the timer. Ensures that the timer is initialized before starting.
     *
     * @throws IllegalStateException If the timer has not been initialized.
     */
    public void startTimer() {
        if (timer == null) {
            throw new IllegalStateException("Timer not initialized. Call initializeTimer() first.");
        }
        timer.start();
    }

    /**
     * Stops the timer, halting all updates.
     */
    public void stopTimer() {
        if (timer != null) {
            timer.stop();
        }
    }

    /**
     * Creates an AnimationTimer that updates the world and processes actors at regular intervals.
     *
     * @return The created AnimationTimer instance.
     */
    private AnimationTimer createTimer() {
        return new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    if (world != null) {
                        // Update the game world
                        world.act(now);

                        // Process all actors in the world
                        List<Actor> actors = world.getActors();
                        processActors(now, actors);
                    }
                } catch (Exception e) {
                    stopTimer(); // Stop the timer if an exception occurs
                }
            }
        };
    }

    /**
     * Processes the provided list of actors using the specified actor processing logic.
     *
     * @param now    The current timestamp in nanoseconds.
     * @param actors The list of actors to process.
     */
    public void processActors(long now, List<Actor> actors) {
        if (actors == null || actors.isEmpty()) {
            return; // Skip processing if there are no actors
        }
        for (Actor actor : actors) {
            if (actor == null) {
                continue; // Skip null actors
            }
            actorProcessor.accept(now, actor); // Process each actor
        }
    }
}
