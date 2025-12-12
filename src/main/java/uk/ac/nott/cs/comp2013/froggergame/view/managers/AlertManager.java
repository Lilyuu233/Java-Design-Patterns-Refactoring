package uk.ac.nott.cs.comp2013.froggergame.view.managers;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import uk.ac.nott.cs.comp2013.froggergame.view.scenes.MyStage;

/**
 * Utility class for creating and displaying alert messages such as "Game Won" or "Game Over" on the game stage.
 */
public class AlertManager {

    // Constants for default alert dimensions
    private static final double DEFAULT_ALERT_WIDTH = 300;
    private static final double DEFAULT_ALERT_HEIGHT = 100;

    // Layout coordinates for "Game Won" and "Game Over" alerts
    private static final double GAME_WON_LAYOUT_X = 170;
    private static final double GAME_WON_LAYOUT_Y = 320;

    private static final double GAME_OVER_LAYOUT_X = 200;
    private static final double GAME_OVER_LAYOUT_Y = 430;

    /**
     * Displays a "Game Won" alert with the player's score.
     *
     * @param primaryStage The primary stage where the alert will be displayed.
     * @param points       The player's final score to be displayed in the alert.
     */
    public static void showGameWonAlert(MyStage primaryStage, int points) {
        StackPane alert = createAlert(
                "YOU WON!",
                "Your High Score: " + points,
                Color.DARKGREEN,
                Color.LIGHTYELLOW,
                GAME_WON_LAYOUT_X,
                GAME_WON_LAYOUT_Y
        );
        primaryStage.getChildren().add(alert);
        primaryStage.requestLayout();
    }

    /**
     * Displays a "Game Over" alert.
     *
     * @param primaryStage The primary stage where the alert will be displayed.
     */
    public static void showGameOverAlert(MyStage primaryStage) {
        StackPane alert = createAlert(
                "GAME OVER",
                null,
                Color.BLACK,
                Color.RED,
                GAME_OVER_LAYOUT_X,
                GAME_OVER_LAYOUT_Y
        );
        primaryStage.getChildren().add(alert);
        primaryStage.requestLayout();
    }

    /**
     * Creates a generic alert component with a title, optional subtitle, and specified styles.
     *
     * @param title            The main title of the alert (e.g., "YOU WON!" or "GAME OVER").
     * @param subtitle         An optional subtitle (e.g., the player's score). Can be null.
     * @param backgroundColor  The background color of the alert box.
     * @param textColor        The color of the text.
     * @param layoutX          The x-coordinate for the alert's position.
     * @param layoutY          The y-coordinate for the alert's position.
     * @return A configured StackPane containing the alert components.
     */
    private static StackPane createAlert(String title, String subtitle, Color backgroundColor, Color textColor, double layoutX, double layoutY) {
        // Root container for the alert
        StackPane root = new StackPane();
        root.setAlignment(Pos.CENTER);

        // Background rectangle for the alert
        Rectangle background = new Rectangle(DEFAULT_ALERT_WIDTH, DEFAULT_ALERT_HEIGHT);
        background.setFill(backgroundColor);
        background.setArcWidth(15);
        background.setArcHeight(15);

        // Main title text
        Text titleText = new Text(title);
        titleText.setFill(textColor);
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 30));

        // Add background and title to the alert
        root.getChildren().addAll(background, titleText);

        // Add optional subtitle text if provided
        if (subtitle != null) {
            Text subtitleText = new Text(subtitle);
            subtitleText.setFill(textColor.brighter());
            subtitleText.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
            StackPane.setAlignment(subtitleText, Pos.BOTTOM_CENTER);
            root.getChildren().add(subtitleText);
        }

        // Set the layout position of the alert
        root.setLayoutX(layoutX);
        root.setLayoutY(layoutY);
        return root;
    }
}
