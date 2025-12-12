package uk.ac.nott.cs.comp2013.froggergame.controller.core;

/**
 * Interface for listening to game events such as winning, game over, and periodic updates.
 */
public interface GameEventListener {
    void onGameWon(int points);
    void onGameOver();

    void update(long now);
}
