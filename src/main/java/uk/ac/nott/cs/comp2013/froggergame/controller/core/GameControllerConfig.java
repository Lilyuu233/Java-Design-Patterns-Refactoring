package uk.ac.nott.cs.comp2013.froggergame.controller.core;

import uk.ac.nott.cs.comp2013.froggergame.controller.initializer.GameInitializer;
import uk.ac.nott.cs.comp2013.froggergame.model.factory.GameObjectFactory;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.score.ScoreManager;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.GameStateChecker;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Score;
import uk.ac.nott.cs.comp2013.froggergame.view.scenes.MyStage;

/**
 * A configuration class for the GameController.
 * This record provides a convenient way to encapsulate all dependencies required
 * to initialize and manage the GameController.
 *
 * @param background       The main stage where the game elements are rendered.
 * @param scoreManager      Manages the game scoring system.
 * @param gameObjectFactory A factory for creating game objects such as animals, obstacles, etc.
 * @param gameStateChecker  Checks the current game state (e.g., win, lose, or ongoing).
 * @param scoreView         The component responsible for displaying the player's score.
 * @param gameInitializer   Initializes game components like obstacles, levels, and more.
 */
public record GameControllerConfig(
        MyStage background,
        ScoreManager scoreManager,
        GameObjectFactory gameObjectFactory,
        GameStateChecker gameStateChecker,
        Score scoreView,
        GameInitializer gameInitializer) {
}
