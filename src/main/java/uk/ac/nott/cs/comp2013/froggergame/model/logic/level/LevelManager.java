package uk.ac.nott.cs.comp2013.froggergame.model.logic.level;

import uk.ac.nott.cs.comp2013.froggergame.controller.core.GameEventListener;
import uk.ac.nott.cs.comp2013.froggergame.model.base.Actor;
import uk.ac.nott.cs.comp2013.froggergame.model.data.LevelConfig;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Log;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Crocodile;
import uk.ac.nott.cs.comp2013.froggergame.view.components.End;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Level;
import uk.ac.nott.cs.comp2013.froggergame.view.components.TimeBar;

import java.util.Map;
import java.util.Objects;

/**
 * Manages the progression and state of game levels.
 * Handles level transitions, updates object speeds, and manages level-specific actors.
 */
public class LevelManager {
    // Constants for default positions and level parameters
    private static final int DEFAULT_ANIMAL_START_X = 300;
    private static final int DEFAULT_ANIMAL_START_Y = 733;
    private static final int MIN_LEVEL = 1;
    private static final int MAX_LEVEL = 2;
    private static final double SPEED_FACTOR_NORMAL = 1.0;
    private static final double SPEED_FACTOR_ADVANCED = 1.5;

    private int level = MIN_LEVEL; // Current level number
    private int reachedEnds = 0; // Number of reached endpoints
    private final GameEventListener eventListener; // Listener for game events

    private int totalEnds; // Total endpoints in the current level
    private final Animal animal; // The player's character
    private final Level levelView; // UI component for level display
    private boolean levelChanged = false; // Indicates if the level has changed
    private final TimeBar timeBar; // Time bar for the level
    private boolean levelTransitioning = false; // Indicates if a level transition is in progress

    /**
     * Constructor to initialize the level manager.
     *
     * @param animal        The player's character
     * @param levelView     The level UI component
     * @param totalEnds     Total endpoints for the level
     * @param timeBar       Time bar for the level
     * @param eventListener Listener for game events
     */
    public LevelManager(Animal animal, Level levelView, int totalEnds, TimeBar timeBar, GameEventListener eventListener) {
        if (animal == null || levelView == null) {
            throw new IllegalArgumentException("Animal and Level view cannot be null");
        }
        this.animal = animal;
        this.levelView = levelView;
        this.totalEnds = totalEnds;
        this.timeBar = timeBar;
        this.levelView.updateLevelDisplay(this.level);
        this.eventListener = eventListener;
    }

    public boolean hasLevelChanged() {
        return this.levelChanged;
    }

    public void resetLevelChangedFlag() {
        this.levelChanged = false;
    }

    public void setLevel(int level) {
        this.level = level;
        this.levelChanged = true;
    }

    public int getCurrentLevel() {
        return this.level;
    }

    /**
     * Increases the level and resets relevant state for the new level.
     */
    public void increaseLevel() {
        this.level++;
        this.levelView.updateLevelDisplay(this.level);
        this.resetLevel();
    }

    public boolean isAllLevelsComplete() {
        return this.level >= MAX_LEVEL;
    }

    /**
     * Handles logic when the player reaches an endpoint.
     */
    public void onTargetReached() {
        if (levelTransitioning) {
            return;
        }
        levelTransitioning = true;
        this.reachedEnds++;
        if (this.reachedEnds == this.totalEnds) {
            if (isAllLevelsComplete()) {
                if (eventListener != null) {
                    int points = animal.getStateManager().getPoints();
                    eventListener.onGameWon(points);
                }
            } else {
                this.increaseLevel();
                if (animal != null) {
                    animal.resetPosition();
                }
                if (this.timeBar != null) {
                    this.timeBar.reset();
                }
            }
        }
        levelTransitioning = false;
    }

    public Level getLevelView() {
        return this.levelView;
    }

    /**
     * Resets the level's state, including layout and actor speeds.
     */
    private void resetLevel() {
        this.reachedEnds = 0;
        LevelConfig config = this.getLevelConfig(this.level);
        this.levelView.resetLevelLayout(config);
        this.resetAnimalPosition();

        resetEnds();
        this.totalEnds = config.totalEnds();

        if (this.level == MAX_LEVEL) {
            addLevelSpecificActors(this.level);
        }

        double speedFactor = config.speedFactor();
        this.updateObjectsSpeed(speedFactor);
    }

    private void resetEnds() {
        for (End end : this.levelView.getEnds()) {
            end.reset();
        }
    }

    private static final Map<Integer, LevelActorConfig> LEVEL_ACTOR_CONFIGS = Map.of(
            MAX_LEVEL, new LevelActorConfig("/images/crocodile.png", 140, 440, 2.0, "/images/spider.png")
    );

    private void addLevelSpecificActors(int level) {
        LevelActorConfig config = LEVEL_ACTOR_CONFIGS.get(level);
        if (config == null) {
            return;
        }

        String crocodileImagePath = Objects.requireNonNull(getClass().getResource(config.crocodileImagePath())).toExternalForm();
        uk.ac.nott.cs.comp2013.froggergame.model.entities.Crocodile crocodile = new uk.ac.nott.cs.comp2013.froggergame.model.entities.Crocodile(
                config.crocodileX(),
                config.crocodileY(),
                config.crocodileSpeed()
        );

        Crocodile.initializeImage(crocodile, crocodileImagePath);

        this.levelView.addActor(crocodile);

        String spiderImagePath = Objects.requireNonNull(getClass().getResource(config.spiderImagePath())).toExternalForm();
        for (Actor actor : levelView.getActors()) {
            if (actor instanceof Log log) {
                log.addRandomSpider(spiderImagePath);
            }
        }
    }

    /**
     * Resets the player's position to the default start position.
     */
    public void resetAnimalPosition() {
        this.animal.setX(DEFAULT_ANIMAL_START_X);
        this.animal.setY(DEFAULT_ANIMAL_START_Y);
    }

    public LevelConfig getLevelConfig(int currentLevel) {
        return LevelConfig.createDefault(currentLevel == MAX_LEVEL ? SPEED_FACTOR_ADVANCED : SPEED_FACTOR_NORMAL);
    }

    /**
     * Updates the speed of all objects in the level based on the provided factor.
     *
     * @param speedFactor The factor to apply to object speeds
     */
    public void updateObjectsSpeed(double speedFactor) {
        for (Actor actor : levelView.getActors()) {
            actor.setSpeedFactor(speedFactor);
        }
    }
}
