package uk.ac.nott.cs.comp2013.froggergame.controller.menu;

import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.List;

import uk.ac.nott.cs.comp2013.froggergame.controller.core.GameController;
import uk.ac.nott.cs.comp2013.froggergame.controller.core.GameControllerFactory;
import uk.ac.nott.cs.comp2013.froggergame.view.GameView;
import uk.ac.nott.cs.comp2013.froggergame.view.HighScoresView;

/**
 * Controller for the main menu, handling actions such as starting the game, showing high scores, and quitting the game.
 * This class acts as the entry point for navigating between the main menu and other parts of the application.
 */
public class MainMenuController {
    private final Stage primaryStage; // The primary application window
    private GameView gameView; // The view component for the main game screen
    private GameController gameController; // The controller managing the game logic

    private static final int WINDOW_WIDTH = 600;  // Default window width for the game scene
    private static final int WINDOW_HEIGHT = 800; // Default window height for the game scene

    /**
     * Constructs the MainMenuController with the primary stage.
     *
     * @param primaryStage The main application stage where scenes are displayed.
     */
    public MainMenuController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Starts the game by initializing the game view, controller, and input handling.
     */
    public void startGame() {
        initializeGame(); // Sets up the game view and controller

        gameView.startBackground(); // Starts the game background animation
        gameView.playBackgroundMusic(); // Plays background music
        gameController.startGameLoop(); // Starts the main game loop
    }

    /**
     * Initializes the game components, including the view, controller, and input handling.
     * Configures the game scene and displays it in the primary stage.
     */
    private void initializeGame() {
        gameView = new GameView(); // Creates the game view
        gameController = GameControllerFactory.createGameController(gameView.getGameStage()); // Creates and configures the game controller

        Scene scene = new Scene(gameView, WINDOW_WIDTH, WINDOW_HEIGHT); // Sets up the scene with specified dimensions
        gameController.configureInputHandling(scene); // Links input handling to the scene

        primaryStage.setScene(scene); // Displays the scene on the primary stage
        primaryStage.setResizable(false); // Prevents resizing of the game window
        primaryStage.show(); // Shows the game window
    }

    /**
     * Displays the high scores screen with a list of top players and their scores.
     */
    public void showHighScores() {
        List<String[]> scores = List.of(
                new String[]{"PLAYER1", "1200"}, // Example score entries
                new String[]{"PLAYER2", "950"},
                new String[]{"PLAYER3", "800"}
        );

        HighScoresView highScoresView = new HighScoresView(primaryStage, scores); // Creates the high scores view
        highScoresView.show(); // Displays the high scores screen
    }

    /**
     * Exits the application by closing the primary stage.
     */
    public void quitGame() {
        primaryStage.close(); // Closes the application window
    }
}
