package uk.ac.nott.cs.comp2013.froggergame.view.components;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import uk.ac.nott.cs.comp2013.froggergame.model.base.Actor;
import uk.ac.nott.cs.comp2013.froggergame.model.data.LevelConfig;
import uk.ac.nott.cs.comp2013.froggergame.view.managers.LevelComposite;

/**
 * Represents a game level, managing its layout, actors, and visual components.
 */
public class Level {
    private static final String CSS_PATH = "/styles/level.css"; // Path to the level stylesheet

    private final Pane background; // Pane representing the level's visual background
    private Text levelLabel; // Label displaying the current level number
    private final List<End> ends; // List of endpoints in the level

    /**
     * Constructor to initialize the level with the specified background pane.
     *
     * @param background The background pane for the level.
     */
    public Level(Pane background) {
        if (background == null) {
            throw new IllegalArgumentException("Background pane cannot be null");
        }
        this.background = background;
        this.levelLabel = null;
        this.ends = new ArrayList<>();

        // Apply CSS styles to the background
        background.getStylesheets().add(Objects.requireNonNull(getClass().getResource(CSS_PATH)).toExternalForm());
    }

    /**
     * Adds an actor to the level's background.
     *
     * @param actor The actor to add.
     */
    public void addActor(Actor actor) {
        if (actor != null) {
            background.getChildren().add(actor);
            System.out.println("Actor added: " + actor.getClass().getSimpleName() + " at position (" + actor.getX() + ", " + actor.getY() + ")");
        }
    }

    /**
     * Updates the level display by adding a label with the current level number.
     *
     * @param level The current level number.
     */
    public void updateLevelDisplay(int level) {
        removeExistingLevelLabel();
        levelLabel = createLevelLabel(level);
        background.getChildren().add(levelLabel);
        levelLabel.toFront();
    }

    /**
     * Resets the level layout based on the provided level configuration.
     *
     * @param config The configuration for the level layout.
     */
    public void resetLevelLayout(LevelConfig config) {
        ends.clear();
        LevelComposite levelComposite = new LevelComposite();
        for (int i = 0; i < config.totalEnds(); i++) {
            End end = new End(config.startX() + i * config.gap(), config.y());
            levelComposite.addComponent(end);
            ends.add(end);
        }
        levelComposite.render(background);
    }

    /**
     * Removes the existing level label from the background.
     */
    private void removeExistingLevelLabel() {
        if (levelLabel != null) {
            background.getChildren().remove(levelLabel);
            levelLabel = null;
        }
    }

    /**
     * Creates a level label displaying the current level number.
     *
     * @param level The current level number.
     * @return A Text object representing the level label.
     */
    private Text createLevelLabel(int level) {
        Text text = new Text("LEVEL: " + level);
        text.getStyleClass().add("level-label");
        return text;
    }

    /**
     * Gets all actors currently in the level's background.
     *
     * @return A list of actors in the level.
     */
    public List<Actor> getActors() {
        return background.getChildren().stream()
                .filter(node -> node instanceof Actor)
                .map(node -> (Actor) node)
                .collect(Collectors.toList());
    }

    /**
     * Gets the endpoints in the level.
     *
     * @return A list of endpoints in the level.
     */
    public List<End> getEnds() {
        return ends;
    }
}
