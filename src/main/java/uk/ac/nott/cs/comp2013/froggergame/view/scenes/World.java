package uk.ac.nott.cs.comp2013.froggergame.view.scenes;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.List;

import uk.ac.nott.cs.comp2013.froggergame.controller.input.KeyEventHandlerProvider;
import uk.ac.nott.cs.comp2013.froggergame.controller.input.KeyEventManager;
import uk.ac.nott.cs.comp2013.froggergame.controller.timer.TimerManager;
import uk.ac.nott.cs.comp2013.froggergame.model.base.Actor;

/**
 * Represents the main abstract world in the game.
 * This class manages actors, input events, and a game timer.
 */
public abstract class World extends Pane implements KeyEventHandlerProvider {

    private final List<Actor> actors = new ArrayList<>(); // List of actors in the world
    private final TimerManager timerManager; // Manages the game's timer
    private EventHandler<? super KeyEvent> keyPressedHandler; // Handler for key pressed events
    private EventHandler<? super KeyEvent> keyReleasedHandler; // Handler for key released events

    /**
     * Constructs a new World instance, initializing the timer and key event manager.
     */
    public World() {
        timerManager = new TimerManager(this, (now, actor) -> actor.act(now));
        // Handles key events
        KeyEventManager keyEventManager = new KeyEventManager(this);
    }

    /**
     * Retrieves all actors in the world.
     *
     * @return A list of actors.
     */
    public List<Actor> getActors() {
        return actors;
    }

    /**
     * Starts the game timer.
     */
    public void start() {
        timerManager.initializeTimer();
        timerManager.startTimer();
    }

    /**
     * Adds an actor to the world.
     *
     * @param actor The actor to be added.
     */
    public void addActor(Actor actor) {
        actors.add(actor);
        getChildren().add(actor);
        actor.setWorld(this);
    }

    /**
     * Retrieves all objects of a specific class from the world.
     *
     * @param cls The class of objects to retrieve.
     * @param <A> The type of objects.
     * @return A list of objects of the specified class.
     */
    public <A> List<A> getObjects(Class<A> cls) {
        return getChildren().stream()
                .filter(cls::isInstance)
                .map(cls::cast)
                .toList();
    }

    /**
     * Retrieves the key pressed event handler.
     *
     * @return The key pressed event handler.
     */
    @Override
    public EventHandler<? super KeyEvent> getKeyPressedHandler() {
        return keyPressedHandler;
    }

    /**
     * Retrieves the key released event handler.
     *
     * @return The key released event handler.
     */
    @Override
    public EventHandler<? super KeyEvent> getKeyReleasedHandler() {
        return keyReleasedHandler;
    }

    /**
     * Abstract method to define the behavior of the world at each game tick.
     *
     * @param now The current time in nanoseconds.
     */
    public abstract void act(long now);
}
