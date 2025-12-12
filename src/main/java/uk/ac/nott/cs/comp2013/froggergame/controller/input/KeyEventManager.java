package uk.ac.nott.cs.comp2013.froggergame.controller.input;

import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import uk.ac.nott.cs.comp2013.froggergame.view.scenes.World;

/**
 * Manages key event handling by coordinating key press and release events across multiple providers.
 * This class ensures a modular approach to input handling by dynamically dispatching events to registered providers.
 */
public class KeyEventManager {
    public final List<KeyEventHandlerProvider> providers = new ArrayList<>(); // List of key event providers

    /**
     * Constructs a KeyEventManager and links it to the specified world.
     * Automatically configures key event listeners whenever the scene changes.
     *
     * @param world The game world whose scene property is monitored for key events.
     */
    public KeyEventManager(World world) {
        world.sceneProperty().addListener(this::configureKeyEvents); // Monitors scene changes to set up key event handlers
    }

    /**
     * Configures key event handlers for the new scene.
     * Listens for key press and release events and delegates them to the appropriate handlers.
     *
     * @param observable The observed value (scene property of the world).
     * @param oldScene   The previous scene (if any).
     * @param newScene   The new scene to which key events are bound.
     */
    private void configureKeyEvents(ObservableValue<? extends Scene> observable, Scene oldScene, Scene newScene) {
        if (newScene != null) {
            newScene.setOnKeyPressed(event -> processKeyEvent(event, true)); // Binds key press events
            newScene.setOnKeyReleased(event -> processKeyEvent(event, false)); // Binds key release events
        }
    }

    /**
     * Processes a key event (press or release) and dispatches it to all registered providers.
     *
     * @param event        The key event to be processed.
     * @param isKeyPressed Indicates whether the event is a key press (true) or key release (false).
     */
    public void processKeyEvent(KeyEvent event, boolean isKeyPressed) {
        for (KeyEventHandlerProvider provider : providers) {
            dispatchKeyEvent(event, isKeyPressed, provider); // Dispatches the event to each provider
        }
    }

    /**
     * Dispatches a key event to a specific provider, invoking the appropriate handler.
     *
     * @param event        The key event to be dispatched.
     * @param isKeyPressed Indicates whether the event is a key press (true) or key release (false).
     * @param provider     The provider responsible for handling the event.
     */
    private void dispatchKeyEvent(KeyEvent event, boolean isKeyPressed, KeyEventHandlerProvider provider) {
        EventHandler<? super KeyEvent> handler = isKeyPressed
                ? provider.getKeyPressedHandler() // Gets the key press handler
                : provider.getKeyReleasedHandler(); // Gets the key release handler
        if (handler != null) {
            handler.handle(event); // Handles the event if a handler is provided
        }
    }
}
