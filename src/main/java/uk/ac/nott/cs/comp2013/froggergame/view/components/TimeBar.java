package uk.ac.nott.cs.comp2013.froggergame.view.components;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.util.Objects;

/**
 * Represents a time bar component with a countdown timer, visual updates,
 * and a timeout callback.
 */
public class TimeBar {
    private static final double DEFAULT_BAR_WIDTH = 100.0; // Default width of the time bar
    private static final double DEFAULT_BAR_HEIGHT = 20.0; // Default height of the time bar
    private static final int DEFAULT_TOTAL_TIME = 90; // Default total countdown time in seconds

    private final Rectangle timeBar; // The visual representation of the time bar
    private final Text timeLabel; // Label displaying time information
    private Timeline timeline; // Animation timeline for the countdown
    private double remainingTime; // Remaining time in seconds
    private final double barWidth; // Width of the time bar
    private final double totalTime; // Total countdown time in seconds

    /**
     * Listener interface to handle timeout events.
     */
    public interface TimeOutListener {
        void onTimeOut();
    }

    private TimeOutListener timeOutListener; // Callback for timeout events

    /**
     * Constructor for TimeBar with default size and countdown settings.
     *
     * @param parentPane The parent pane to which the time bar will be added.
     */
    public TimeBar(Pane parentPane) {
        this(parentPane, DEFAULT_BAR_WIDTH, DEFAULT_BAR_HEIGHT, DEFAULT_TOTAL_TIME);
    }

    /**
     * Constructor for TimeBar with custom size and countdown settings.
     *
     * @param parentPane The parent pane to which the time bar will be added.
     * @param barWidth   The width of the time bar.
     * @param barHeight  The height of the time bar.
     * @param totalTime  The total countdown time in seconds.
     */
    public TimeBar(Pane parentPane, double barWidth, double barHeight, double totalTime) {
        this.barWidth = barWidth;
        this.totalTime = totalTime;
        this.remainingTime = totalTime;

        // Initialize the time bar
        timeBar = new Rectangle(barWidth, barHeight);
        timeBar.getStyleClass().add("time-bar");

        // Initialize the time label
        timeLabel = new Text("TIME");
        timeLabel.getStyleClass().add("time-label");

        // Add components to the parent pane
        parentPane.getChildren().addAll(timeBar, timeLabel);

        // Apply CSS to the parent pane
        parentPane.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/styles/timebar.css")).toExternalForm()
        );
    }

    /**
     * Sets the listener for timeout events.
     *
     * @param listener The listener to be notified on timeout.
     */
    public void setTimeOutListener(TimeOutListener listener) {
        this.timeOutListener = listener;
    }

    /**
     * Returns the rectangle representing the time bar.
     *
     * @return The rectangle object of the time bar.
     */
    public Rectangle getRectangle() {
        return timeBar;
    }

    /**
     * Returns the text label of the time bar.
     *
     * @return The text label of the time bar.
     */
    public Text getText() {
        return timeLabel;
    }

    /**
     * Starts the countdown animation.
     */
    public void start() {
        if (timeline == null) {
            timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1), event -> updateTimeBar())
            );
            timeline.setCycleCount((int) totalTime);
            timeline.setOnFinished(event -> onTimeOut());
        }
        timeline.play();
    }

    /**
     * Updates the time bar's width and visual state based on the remaining time.
     */
    private void updateTimeBar() {
        remainingTime--;

        double newWidth = (remainingTime / totalTime) * barWidth;
        timeBar.setWidth(newWidth);

        if (remainingTime < 10) {
            timeBar.getStyleClass().removeAll("time-bar", "time-bar-warning", "time-bar-danger");
            timeBar.getStyleClass().add("time-bar-danger");
        } else if (remainingTime < 20) {
            timeBar.getStyleClass().removeAll("time-bar", "time-bar-warning", "time-bar-danger");
            timeBar.getStyleClass().add("time-bar-warning");
        }
    }

    /**
     * Handles actions to be performed when the timer reaches zero.
     */
    private void onTimeOut() {
        timeLabel.setText("TIME: 0");
        timeBar.getStyleClass().removeAll("time-bar", "time-bar-warning", "time-bar-danger");
        timeBar.getStyleClass().add("time-bar-timeout");

        if (timeOutListener != null) {
            timeOutListener.onTimeOut();
        }
    }

    /**
     * Resets the time bar to its initial state.
     */
    public void reset() {
        stop();
        remainingTime = totalTime;
        timeBar.setWidth(barWidth);
        timeBar.getStyleClass().removeAll("time-bar-warning", "time-bar-danger", "time-bar-timeout");
        timeBar.getStyleClass().add("time-bar");
        timeLabel.setText("TIME");
        start();
    }

    /**
     * Stops the countdown animation.
     */
    public void stop() {
        if (timeline != null) {
            timeline.stop();
        }
    }
}
