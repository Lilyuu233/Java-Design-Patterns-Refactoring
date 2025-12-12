package uk.ac.nott.cs.comp2013.froggergame.model.entities;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import uk.ac.nott.cs.comp2013.froggergame.model.base.Actor;

/**
 * Represents a spider that appears on a log and moves along with it.
 */
public class Spider extends Actor {
    private final Log parentLog; // The log to which this spider is attached

    /**
     * Constructs a spider attached to a specific log with the given image.
     *
     * @param parentLog The log the spider is attached to.
     * @param imagePath Path to the spider's image.
     */
    public Spider(Log parentLog, String imagePath) {
        super((int) parentLog.getX(), (int) parentLog.getY(), 0);
        this.parentLog = parentLog;
        try {
            setImage(new Image(imagePath, 30, 30, true, true));
        } catch (RuntimeException ignored) {
        }
    }

    /**
     * Gets the log the spider is attached to.
     *
     * @return The parent log.
     */
    public Log getParentLog() {
        return parentLog;
    }

    /**
     * Gets the parent pane containing this spider, if any.
     *
     * @return The parent pane or null if not present.
     */
    public Pane getParentPane() {
        if (this.getParent() instanceof Pane pane) {
            return pane;
        }
        return null;
    }

    /**
     * Updates the position of the spider based on the parent log's position.
     */
    public void updatePosition() {
        double xOffset = 50; // Horizontal offset from the log
        setX(parentLog.getX() + xOffset);
        double yOffset = 2; // Vertical offset from the log
        setY(parentLog.getY() + yOffset);
    }

    /**
     * Defines the behavior of the spider during each game tick.
     * Currently does nothing but is included for future extension.
     *
     * @param now Current time in nanoseconds.
     */
    @Override
    public void act(long now) {
    }
}
