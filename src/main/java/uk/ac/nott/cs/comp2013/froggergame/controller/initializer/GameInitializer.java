package uk.ac.nott.cs.comp2013.froggergame.controller.initializer;

import uk.ac.nott.cs.comp2013.froggergame.model.data.GameObjectType;
import uk.ac.nott.cs.comp2013.froggergame.model.factory.GameObjectFactory;
import uk.ac.nott.cs.comp2013.froggergame.model.data.GameObjectData;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.score.ScoreManager;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Score;
import uk.ac.nott.cs.comp2013.froggergame.view.scenes.MyStage;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Background;
import uk.ac.nott.cs.comp2013.froggergame.view.components.End;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * GameInitializer is responsible for initializing various game components,
 * such as the background, game objects, and score display.
 * It leverages dynamic object setup using a map of object types to their setup methods.
 */
public class GameInitializer {
    private static final String BACKGROUND_IMAGE = "background.png"; // Path to the background image
    private static final int INITIAL_SCORE = 0; // Initial player score

    private final MyStage background; // The game stage where components are rendered
    private final ScoreManager scoreManager; // Manages the game's score
    private final Map<GameObjectType, BiConsumer<MyStage, Object[][]>> setupMethods; // Maps object types to setup methods

    /**
     * Constructs a GameInitializer with required dependencies.
     *
     * @param background       The stage where game components are rendered.
     * @param scoreManager     The manager responsible for handling game scores.
     * @param gameObjectFactory The factory for creating game objects such as logs, turtles, and obstacles.
     */
    public GameInitializer(MyStage background, ScoreManager scoreManager, GameObjectFactory gameObjectFactory) {
        this.background = background;
        this.scoreManager = scoreManager;

        // Map object types to their respective setup methods
        this.setupMethods = new HashMap<>();
        setupMethods.put(GameObjectType.LOG, gameObjectFactory::setupLogs);
        setupMethods.put(GameObjectType.TURTLE, gameObjectFactory::setupTurtles);
        setupMethods.put(GameObjectType.OBSTACLE, gameObjectFactory::setupObstacles);
    }

    /**
     * Initializes all game components, including the background, game objects, and score display.
     *
     * @param scoreView The view component responsible for displaying the score.
     */
    public void initializeComponents(Score scoreView) {
        setupBackground();
        setupGameObjects();
        setupScoreDisplay(scoreView);
    }

    /**
     * Sets up the game background by adding a background image to the stage.
     */
    public void setupBackground() {
        Background bg = new Background(BACKGROUND_IMAGE);
        background.getChildren().add(bg);
    }

    /**
     * Sets up all game objects, such as logs, turtles, and obstacles,
     * and initializes the endpoint targets.
     */
    private void setupGameObjects() {
        initializeGameObjects(GameObjectType.LOG, GameObjectData.LOG_DATA);
        initializeGameObjects(GameObjectType.TURTLE, GameObjectData.TURTLE_DATA);
        initializeGameObjects(GameObjectType.OBSTACLE, GameObjectData.OBSTACLE_DATA);
        setupEndPoints();
    }

    /**
     * Dynamically initializes game objects based on their type and associated data.
     *
     * @param type The type of the game object to initialize.
     * @param data The data associated with the object type.
     */
    private void initializeGameObjects(GameObjectType type, Object[][] data) {
        // Dynamically dispatch setup logic using the setupMethods map
        setupMethods.getOrDefault(type, (bg, dt) -> {}).accept(background, data);
    }

    /**
     * Sets up the endpoint targets for the game, adding them to the stage.
     */
    public void setupEndPoints() {
        for (int[] position : GameObjectData.END_POINT_DATA) {
            background.getChildren().add(new End(position[0], position[1]));
        }
    }

    /**
     * Sets up the score display on the stage, initializing labels and updating the score view.
     *
     * @param scoreView The view component for displaying the player's score.
     */
    private void setupScoreDisplay(Score scoreView) {
        scoreView.createScoreLabel(background);
        scoreView.updateScoreDisplay(background, INITIAL_SCORE, scoreManager);
    }
}
