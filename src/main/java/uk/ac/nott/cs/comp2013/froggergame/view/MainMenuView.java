package uk.ac.nott.cs.comp2013.froggergame.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Objects;

import uk.ac.nott.cs.comp2013.froggergame.controller.menu.MainMenuController;

/**
 * Main menu view for the Frogger game, providing options to play the game, view high scores, or quit.
 */
public class MainMenuView {
    private final Stage primaryStage; // The primary stage for displaying the menu
    private static final int WINDOW_WIDTH = 600; // Default width of the menu window
    private static final int WINDOW_HEIGHT = 800; // Default height of the menu window
    private static final String MENU_IMAGE_PATH = "/images/menu.png"; // Path to the menu banner image
    private static final String FROG_ICON_PATH = "/images/froggerUp.png"; // Path to the frog icon image
    private final MainMenuController controller; // Controller handling the menu actions

    /**
     * Constructor to initialize the main menu view with the primary stage and controller.
     *
     * @param primaryStage The primary stage for the application.
     * @param controller   The controller handling menu actions.
     */
    public MainMenuView(Stage primaryStage, MainMenuController controller) {
        this.primaryStage = primaryStage;
        this.controller = controller;
    }

    /**
     * Displays the main menu view.
     */
    public void show() {
        VBox rootLayout = createRootLayout();
        Scene scene = new Scene(rootLayout, WINDOW_WIDTH, WINDOW_HEIGHT);

        // Add the main menu stylesheet
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/mainmenu.css")).toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Creates the root layout for the main menu, containing the banner and menu buttons.
     *
     * @return A VBox containing the main menu layout.
     */
    private VBox createRootLayout() {
        ImageView banner = createHeaderBanner();
        VBox menuLayout = createMenuLayout();

        VBox rootLayout = new VBox();
        rootLayout.getChildren().addAll(banner, menuLayout);
        rootLayout.setAlignment(Pos.TOP_CENTER);
        rootLayout.getStyleClass().add("root-layout");

        return rootLayout;
    }

    /**
     * Creates the layout containing the menu buttons.
     *
     * @return A VBox containing the menu buttons.
     */
    private VBox createMenuLayout() {
        VBox menuLayout = new VBox(
                createButton("PLAY GAME", controller::startGame),
                createButton("HIGH SCORES", controller::showHighScores),
                createButton("QUIT GAME", controller::quitGame)
        );
        menuLayout.getStyleClass().add("menu-layout");
        return menuLayout;
    }

    /**
     * Creates the header banner for the main menu.
     *
     * @return An ImageView containing the banner image.
     */
    private ImageView createHeaderBanner() {
        ImageView banner = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(MENU_IMAGE_PATH)).toExternalForm()));
        banner.getStyleClass().add("header-banner");
        return banner;
    }

    /**
     * Creates a button with an associated action and hover effect.
     *
     * @param text   The text to display on the button.
     * @param action The action to perform when the button is clicked.
     * @return An HBox containing the button and its hover effect.
     */
    private HBox createButton(String text, Runnable action) {
        ImageView frogIcon = createFrogIcon();
        frogIcon.setVisible(false); // Hide the icon initially

        Button button = createStyledButton(text);
        button.setOnMouseEntered(event -> frogIcon.setVisible(true)); // Show icon on hover
        button.setOnMouseExited(event -> frogIcon.setVisible(false)); // Hide icon when not hovering
        button.setOnAction(event -> action.run()); // Perform the action on button click

        HBox hBox = new HBox(frogIcon, button);
        hBox.getStyleClass().add("button-container");

        return hBox;
    }

    /**
     * Creates a styled button for the menu.
     *
     * @param text The text to display on the button.
     * @return A Button styled for the main menu.
     */
    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.getStyleClass().add("menu-button");
        return button;
    }

    /**
     * Creates the frog icon to be displayed next to menu buttons on hover.
     *
     * @return An ImageView containing the frog icon image.
     */
    private ImageView createFrogIcon() {
        ImageView frogIcon = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(FROG_ICON_PATH)).toExternalForm()));
        frogIcon.getStyleClass().add("frog-icon");
        return frogIcon;
    }
}
