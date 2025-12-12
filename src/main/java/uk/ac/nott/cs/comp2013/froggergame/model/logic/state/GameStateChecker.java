package uk.ac.nott.cs.comp2013.froggergame.model.logic.state;

import uk.ac.nott.cs.comp2013.froggergame.controller.core.GameEventListener;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;

/**
 * Handles the logic for checking the current game state, such as whether the player has won or lost.
 */
public class GameStateChecker {

    /**
     * Checks the current state of the game based on the player's status.
     *
     * @param animal        The player's character.
     * @param eventListener Listener to notify when the game state changes.
     */
    public void checkGameState(Animal animal, GameEventListener eventListener) {
        StateManager stateManager = animal.getStateManager();
        if (stateManager.isVictory()) {
            notifyGameWon(eventListener, stateManager.getPoints());
        } else if (animal.isDead()) {
            notifyGameOver(eventListener);
        }
    }

    /**
     * Notifies the listener that the player has won the game.
     *
     * @param eventListener Listener to notify.
     * @param points        The points scored by the player.
     */
    private void notifyGameWon(GameEventListener eventListener, int points) {
        if (eventListener != null) {
            eventListener.onGameWon(points);
        }
    }

    /**
     * Notifies the listener that the player has lost the game.
     *
     * @param eventListener Listener to notify.
     */
    private void notifyGameOver(GameEventListener eventListener) {
        if (eventListener != null) {
            eventListener.onGameOver();
        }
    }
}
