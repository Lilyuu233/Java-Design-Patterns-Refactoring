package uk.ac.nott.cs.comp2013.froggergame.controller.loop;

import javafx.animation.AnimationTimer;

import uk.ac.nott.cs.comp2013.froggergame.controller.core.GameEventListener;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.GameStateChecker;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;

/**
 * Manages the main game loop, which updates game state and renders frames.
 * Uses an AnimationTimer to repeatedly execute the game loop.
 */
public class GameLoopManager {
    private final Animal animal; // The main game character being monitored
    private AnimationTimer gameLoop; // Timer for executing the game loop
    private GameEventListener eventListener; // Listener for game state updates
    private long lastUpdateTime = 0; // Tracks the last update time for delta calculations
    private final GameStateChecker gameStateChecker; // Checks and updates the current game state

    /**
     * Constructs a GameLoopManager with the specified game character and state checker.
     *
     * @param animal            The game character to be monitored during the game loop.
     * @param gameStateChecker  The component responsible for checking the current game state.
     */
    public GameLoopManager(Animal animal, GameStateChecker gameStateChecker) {
        this.animal = animal;
        this.gameStateChecker = gameStateChecker;
    }

    /**
     * Checks the game state (e.g., victory, game over) and triggers appropriate events via the listener.
     */
    private void checkGameState() {
        gameStateChecker.checkGameState(animal, eventListener);
    }

    /**
     * Sets the event listener for handling game state changes.
     *
     * @param listener The listener to be notified of game events.
     */
    public void setGameEventListener(GameEventListener listener) {
        this.eventListener = listener;
    }

    /**
     * Stops the execution of the game loop.
     */
    public void stopGameLoopExecution() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }

    /**
     * Starts the execution of the game loop using an AnimationTimer.
     * Handles exceptions to gracefully stop the game loop if an error occurs.
     */
    public void startGameLoopExecution() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    executeGameLoop(now);
                } catch (Exception e) {
                    stopGameLoopExecution();
                }
            }
        };
        gameLoop.start();
    }

    /**
     * Executes a single iteration of the game loop.
     * Updates the game state and notifies the event listener if necessary.
     *
     * @param now The current timestamp (in nanoseconds) provided by the AnimationTimer.
     */
    private void executeGameLoop(long now) {
        if (lastUpdateTime == 0) {
            lastUpdateTime = now;
            return;
        }

        // Skip processing if the game character is not active
        if (!animal.isActive()) {
            return;
        }

        // Notify the event listener to update the game state
        if (eventListener != null) {
            eventListener.update(now);
        }

        // Check the game state for win/loss conditions
        checkGameState();

        // Update the last update time
        lastUpdateTime = now;
    }
}
