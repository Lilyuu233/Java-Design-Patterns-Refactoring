package uk.ac.nott.cs.comp2013.froggergame.view.components;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import java.util.Objects;
import java.util.Stack;

import uk.ac.nott.cs.comp2013.froggergame.model.logic.score.ScoreManager;

/**
 * Handles the display and management of the game's score.
 */
public class Score {
    private static final String SCORE_LABEL_CLASS = "score-label"; // CSS class for the score label
    private static final String DIGIT_CLASS = "score-digit"; // CSS class for score digits

    private final ScoreManager scoreManager; // Manages the game's score logic

    /**
     * Constructor to initialize the Score instance.
     */
    public Score() {
        this.scoreManager = new ScoreManager();
    }

    /**
     * Updates the score display in the given pane.
     *
     * @param pane         The pane to update the score display on.
     * @param score        The current score value.
     * @param scoreManager The score manager providing score-related operations.
     */
    public void updateScoreDisplay(Pane pane, int score, ScoreManager scoreManager) {
        if (pane == null) return;
        Platform.runLater(() -> {
            // Remove existing digit nodes
            pane.getChildren().removeIf(node -> node.getStyleClass().contains(DIGIT_CLASS));
            // Add updated score digits
            addScoreDigitsToPane(pane, scoreManager.getDigits(score));
        });
    }

    /**
     * Adds digit nodes representing the score to the given pane.
     *
     * @param pane   The pane to add the digit nodes to.
     * @param digits A stack of digits representing the score.
     */
    private void addScoreDigitsToPane(Pane pane, Stack<Integer> digits) {
        int shift = 0;
        while (!digits.isEmpty()) {
            int digit = digits.pop();
            Digit digitNode = new Digit(digit, 30, 120 + shift, 5);
            digitNode.getStyleClass().add(DIGIT_CLASS);
            pane.getChildren().add(digitNode);
            shift += 30;
        }
    }

    /**
     * Creates and adds a score label to the given background pane.
     *
     * @param background The background pane to add the score label to.
     */
    public void createScoreLabel(Pane background) {
        Text scoreLabel = new Text("SCORE:");
        scoreLabel.getStyleClass().add(SCORE_LABEL_CLASS);
        background.getChildren().add(scoreLabel);

        // Load and apply the CSS stylesheet for score styling
        background.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/styles/score.css")).toExternalForm()
        );
    }

    /**
     * Updates the score with a new value.
     *
     * @param newScore The new score value to display.
     */
    public void updateScore(int newScore) {
        updateScoreDisplay(null, newScore, scoreManager);
    }
}
