package uk.ac.nott.cs.comp2013.froggergame;

import javafx.application.Application;
import javafx.stage.Stage;

import uk.ac.nott.cs.comp2013.froggergame.controller.menu.MainMenuController;
import uk.ac.nott.cs.comp2013.froggergame.view.MainMenuView;

/**
 * Entry point for the Frogger game application.
 * This class initializes the application and displays the main menu.
 */
public class Main extends Application {

	/**
	 * Main method to launch the application.
	 *
	 * @param args Command-line arguments (not used).
	 */
	public static void main(String[] args) {
		Application.launch(args); // Launches the JavaFX application.
	}

	/**
	 * Starts the JavaFX application by initializing the primary stage and displaying the main menu.
	 *
	 * @param primaryStage The primary stage for the application.
	 */
	@Override
	public void start(Stage primaryStage) {
		// Initialize the main menu controller
		MainMenuController mainMenuController = new MainMenuController(primaryStage);
		// Create and show the main menu view
		MainMenuView mainMenu = new MainMenuView(primaryStage, mainMenuController);
		mainMenu.show();
	}
}
