package uk.ac.nott.cs.comp2013.froggergame.controller.core;

import uk.ac.nott.cs.comp2013.froggergame.controller.initializer.GameInitializer;
import uk.ac.nott.cs.comp2013.froggergame.model.factory.GameObjectFactory;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.score.ScoreManager;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.GameStateChecker;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Score;
import uk.ac.nott.cs.comp2013.froggergame.view.scenes.MyStage;

/**
 * Factory class for creating and initializing a GameController instance.
 * This class encapsulates the creation and configuration of all dependencies
 * required by the GameController, ensuring a clean and centralized initialization process.
 */
public class GameControllerFactory {

    /**
     * Creates a fully initialized GameController instance with all required dependencies.
     *
     * @param background The main stage where the game elements will be rendered.
     * @return A fully configured and initialized GameController instance.
     */
    public static GameController createGameController(MyStage background) {
        // Creates a ScoreManager to handle game scoring logic
        ScoreManager scoreManager = new ScoreManager();

        // Creates a Score view component to display the player's score
        Score scoreView = new Score();

        // Creates a factory for generating game objects like animals, obstacles, etc.
        GameObjectFactory gameObjectFactory = new GameObjectFactory();

        // Creates a GameStateChecker to monitor the game's current state (e.g., win or lose)
        GameStateChecker gameStateChecker = new GameStateChecker();

        // Initializes the game's components such as obstacles and levels
        GameInitializer gameInitializer = new GameInitializer(background, scoreManager, gameObjectFactory);

        // Configures all dependencies for the GameController
        GameControllerConfig config = new GameControllerConfig(
                background, scoreManager, gameObjectFactory, gameStateChecker, scoreView, gameInitializer
        );

        // Creates the GameController with the provided configuration and initializes the game
        GameController controller = new GameController(config);
        controller.initializeGame();
        return controller;
    }
}
