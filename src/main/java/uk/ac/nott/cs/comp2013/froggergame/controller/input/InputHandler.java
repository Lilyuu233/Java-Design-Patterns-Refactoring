package uk.ac.nott.cs.comp2013.froggergame.controller.input;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.StateManager;

/**
 * Handles user input (keyboard events) for controlling the player's character (Animal).
 * This class decouples input processing logic from the game logic, making it reusable and modular.
 */
public class InputHandler {
    private final Animal animal; // The main game character controlled by the player

    /**
     * Constructs an InputHandler for the specified Animal.
     *
     * @param animal The game character to be controlled by user input.
     */
    public InputHandler(Animal animal) {
        this.animal = animal;
    }

    /**
     * Handles key press events, triggering corresponding actions for the Animal.
     *
     * @param event The key event representing a key press.
     */
    public void handleKeyPressed(KeyEvent event) {
        processInput(event, true); // Process the key press as an active input
    }

    /**
     * Handles key release events, stopping corresponding actions for the Animal.
     *
     * @param event The key event representing a key release.
     */
    public void handleKeyReleased(KeyEvent event) {
        processInput(event, false); // Process the key release as an inactive input
    }

    /**
     * Processes the input event and determines whether it is a key press or release.
     * Ensures that input is ignored if the Animal is in a "no move" state.
     *
     * @param event     The key event to be processed.
     * @param isPressed Whether the key is pressed (true) or released (false).
     */
    private void processInput(KeyEvent event, boolean isPressed) {
        if (animal.getStateManager().isNoMove()) return; // Ignore input if movement is disabled
        processKeyEvent(event.getCode(), isPressed); // Delegate to specific key processing
    }

    /**
     * Processes the specific key event and triggers movement or actions for the Animal.
     *
     * @param key       The key code of the pressed or released key.
     * @param isPressed Whether the key is pressed (true) or released (false).
     */
    private void processKeyEvent(KeyCode key, boolean isPressed) {
        StateManager stateManager = animal.getStateManager();
        // Updates the animal's state and position based on the input
        stateManager.moveAnimal(animal.getMovementX(key), animal.getMovementY(key), key, isPressed);
    }
}
