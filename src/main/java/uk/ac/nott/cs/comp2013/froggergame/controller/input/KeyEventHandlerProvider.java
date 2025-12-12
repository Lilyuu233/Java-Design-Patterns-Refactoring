package uk.ac.nott.cs.comp2013.froggergame.controller.input;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

/**
 * Interface for providing key event handlers.
 * Implementing classes define logic for handling key press and release events.
 * This interface allows for flexible and modular input handling by enabling
 * multiple providers to register their own key event logic.
 */
public interface KeyEventHandlerProvider {
    EventHandler<? super KeyEvent> getKeyPressedHandler();
    EventHandler<? super KeyEvent> getKeyReleasedHandler();
}

