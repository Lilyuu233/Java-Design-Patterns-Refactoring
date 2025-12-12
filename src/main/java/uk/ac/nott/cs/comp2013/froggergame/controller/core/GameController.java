package uk.ac.nott.cs.comp2013.froggergame.controller.core;

import javafx.scene.Scene;
import java.util.function.Supplier;

import uk.ac.nott.cs.comp2013.froggergame.controller.initializer.GameInitializer;
import uk.ac.nott.cs.comp2013.froggergame.controller.input.InputHandler;
import uk.ac.nott.cs.comp2013.froggergame.controller.loop.GameLoopManager;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Crocodile;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.GameStateChecker;
import uk.ac.nott.cs.comp2013.froggergame.view.components.End;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Level;
import uk.ac.nott.cs.comp2013.froggergame.view.components.TimeBar;
import uk.ac.nott.cs.comp2013.froggergame.view.managers.AlertManager;
import uk.ac.nott.cs.comp2013.froggergame.model.factory.GameObjectFactory;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.level.LevelManager;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.score.ScoreManager;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import uk.ac.nott.cs.comp2013.froggergame.view.scenes.MyStage;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Score;

/**
 * Main controller for managing the Frogger game flow, including initialization,
 * input handling, game loop execution, and game state updates.
 */
public class GameController implements GameEventListener {
    private final MyStage background;
    private final GameInitializer gameInitializer;
    private Animal animal;
    private LevelManager levelManager;
    private final ScoreManager scoreManager;
    private final GameObjectFactory gameObjectFactory;
    private GameLoopManager gameLoopManager;
    private final GameStateChecker gameStateChecker;
    private final Score scoreView;

    private static final int DEFAULT_INITIAL_LEVEL = 1;

    /**
     * Constructs the GameController with necessary dependencies.
     *
     * @param config Configuration object that contains all dependencies.
     */
    public GameController(GameControllerConfig config) {
        this.background = config.background();
        this.scoreManager = config.scoreManager();
        this.gameObjectFactory = config.gameObjectFactory();
        this.gameInitializer = config.gameInitializer();
        this.gameStateChecker = config.gameStateChecker();
        this.scoreView = config.scoreView();
    }

    /**
     * Initializes the game components and settings.
     */
    public void initializeGame() {
        setupAnimal();
        setupLevelManager();
        setupGameLoopManager();
        gameInitializer.initializeComponents(scoreView); // Initializes view and model components
        setupTimeoutListener();
        setupAnimal();
    }

    /**
     * Creates and adds the main player character (Animal) to the stage.
     */
    private void setupAnimal() {
        animal = gameObjectFactory.createAnimal(); // Uses factory to create the player character
        background.addActor(animal); // Adds the animal to the game stage
    }

    /**
     * Sets up the LevelManager responsible for managing game levels and progression.
     */
    private void setupLevelManager() {
        Level levelView = new Level(background);
        TimeBar timeBar = background.getTimeBar();
        levelManager = new LevelManager(animal, levelView, 5, timeBar, this);
        levelManager.setLevel(DEFAULT_INITIAL_LEVEL); // Initializes the default level
        levelView.updateLevelDisplay(DEFAULT_INITIAL_LEVEL); // Updates the UI display
    }

    /**
     * Configures the game loop manager and links it with the game state listener.
     */
    private void setupGameLoopManager() {
        gameLoopManager = new GameLoopManager(animal, gameStateChecker);
        gameLoopManager.setGameEventListener(this); // Sets this controller as the event listener
    }

    /**
     * Configures a listener for handling timeout events.
     */
    private void setupTimeoutListener() {
        background.getTimeBar().setTimeOutListener(() -> {
            stopGame();
            freezeAnimal();
            AlertManager.showGameOverAlert(background); // Shows game-over alert
        });
    }

    /**
     * Configures key press and release events for player input handling.
     *
     * @param scene The JavaFX scene where the game is rendered.
     */
    public void configureInputHandling(Scene scene) {
        if (scene != null && animal != null) {
            InputHandler inputHandler = new InputHandler(animal);

            scene.setOnKeyPressed(event -> {
                if (animal.isActive()) {
                    inputHandler.handleKeyPressed(event); // Handles key press
                }
            });

            scene.setOnKeyReleased(event -> {
                if (animal.isActive()) {
                    inputHandler.handleKeyReleased(event); // Handles key release
                }
            });
        } else if (scene != null) {
            scene.setOnKeyPressed(null); // Clears key event handlers
            scene.setOnKeyReleased(null);
        }
    }

    /**
     * Starts the game loop and timer for the current level.
     */
    public void startGameLoop() {
        gameLoopManager.startGameLoopExecution(); // Starts the game loop
        TimeBar timeBar = background.getTimeBar();
        if (timeBar != null) {
            timeBar.start(); // Starts the timer
        }
    }

    /**
     * Stops the game loop and timer, and halts background music.
     */
    public void stopGame() {
        gameLoopManager.stopGameLoopExecution();
        TimeBar timeBar = background.getTimeBar();
        if (timeBar != null) {
            timeBar.stop();
        }
        background.stopMusic();
    }

    /**
     * Updates the game state, including scores and levels, if there are changes.
     */
    public void updateGameState() {
        updateIfChanged(
                () -> animal.getStateManager().hasScoreChanged(),
                () -> {
                    int currentPoints = animal.getStateManager().getPoints();
                    scoreView.updateScoreDisplay(background, currentPoints, scoreManager);
                },
                () -> animal.getStateManager().resetScoreChangedFlag()
        );

        updateIfChanged(
                () -> levelManager.hasLevelChanged(),
                () -> {
                    int currentLevel = levelManager.getCurrentLevel();
                    levelManager.getLevelView().updateLevelDisplay(currentLevel);
                },
                () -> levelManager.resetLevelChangedFlag()
        );
    }

    public void setGameLoopManager(GameLoopManager gameLoopManager) {
        this.gameLoopManager = gameLoopManager;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public void setLevelManager(LevelManager levelManager) {
        this.levelManager = levelManager;
    }

    /**
     * Updates a property only if it has changed.
     *
     * @param hasChanged Supplier to check if the value has changed.
     * @param update     Runnable to execute the update logic.
     * @param resetFlag  Runnable to reset the change flag.
     */
    private void updateIfChanged(Supplier<Boolean> hasChanged, Runnable update, Runnable resetFlag) {
        if (hasChanged.get()) {
            update.run();
            resetFlag.run();
        }
    }

    /**
     * Disables the player character and clears input handling.
     */
    private void freezeAnimal() {
        if (animal != null) {
            animal.setActive(false); // Deactivates the player
        }
        configureInputHandling(null); // Clears input handling
    }

    @Override
    public void onGameWon(int points) {
        stopGame();
        AlertManager.showGameWonAlert(background, scoreManager.getPoints());
    }

    @Override
    public void onGameOver() {
        stopGame();
        background.stopTimeBar();
        freezeAnimal();
        AlertManager.showGameOverAlert(background);
    }

    @Override
    public void update(long now) {
        background.act(now);
        if (animal == null || !animal.isActive()) {
            return; // Stops updating if the player is inactive
        }

        TimeBar timeBar = background.getTimeBar();
        if (timeBar != null) {
            timeBar.getRectangle().toFront();
            timeBar.getText().toFront();
        }

        background.getObjects(Crocodile.class).forEach(actor -> actor.act(System.nanoTime()));

        for (End end : background.getObjects(End.class)) {
            if (!end.isActivated() && animal.isActive() && animal.getBoundsInParent().intersects(end.getBoundsInParent())) {
                end.setEnd();
                levelManager.onTargetReached();
            }
        }

        updateGameState();
    }
}
