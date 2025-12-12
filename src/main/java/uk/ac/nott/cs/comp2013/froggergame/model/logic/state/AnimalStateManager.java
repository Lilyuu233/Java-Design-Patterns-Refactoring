package uk.ac.nott.cs.comp2013.froggergame.model.logic.state;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.animation.DeathAnimationContext;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.score.ScoreManager;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.animation.DeathAnimationHandler;
import uk.ac.nott.cs.comp2013.froggergame.view.resource.ImageManager;

/**
 * Manages the state of the player's character (Animal), including movement,
 * scoring, and animation updates.
 */
public class AnimalStateManager implements StateManager {

    private final Animal animal; // Reference to the player's character
    private boolean noMove = false; // Indicates if movement is currently disabled
    private int endCount = 0; // Counter for reached endpoints

    private static final int DEFAULT_GAME_WIN_END_COUNT = 5; // Default required ends to win
    private static final String DEFAULT_DEATH_TYPE = "cardeath"; // Default death animation type
    private static final int DEFAULT_DEATH_FRAMES = 4; // Default death animation frame count

    private final int gameWinEndCount; // Configurable win condition
    private final ImageManager imageManager; // Manages images for the character
    private final ScoreManager scoreManager; // Handles scoring logic
    private final DeathAnimationHandler deathAnimationHandler; // Handles death animations

    /**
     * Constructor with default win condition.
     *
     * @param animal The player's character.
     */
    public AnimalStateManager(Animal animal) {
        this(animal, DEFAULT_GAME_WIN_END_COUNT);
    }

    /**
     * Constructor with customizable win condition.
     *
     * @param animal          The player's character.
     * @param gameWinEndCount The required endpoint count to win the game.
     */
    public AnimalStateManager(Animal animal, int gameWinEndCount) {
        this.animal = animal;
        this.gameWinEndCount = gameWinEndCount;
        this.imageManager = new ImageManager();
        this.scoreManager = new ScoreManager();
        this.deathAnimationHandler = new DeathAnimationHandler();
    }

    /**
     * Resets the state to its initial values.
     */
    public void reset() {
        noMove = false;
        endCount = 0;
        deathAnimationHandler.reset();
        scoreManager.resetScoreChangedFlag();
    }

    /**
     * Checks if the player has met the win condition.
     *
     * @return True if the player has won, false otherwise.
     */
    public boolean isVictory() {
        return endCount >= gameWinEndCount;
    }

    /**
     * Updates the death animation state based on the current frame.
     *
     * @param now        Current time in nanoseconds.
     * @param type       The type of death animation.
     * @param frameLimit The total frames for the animation.
     */
    public void updateDeathAnimation(long now, String type, int frameLimit) {
        DeathAnimationHandler.AnimationState state = deathAnimationHandler.animateDeath(
                now, new DeathAnimationContext(animal, imageManager, type, frameLimit)
        );
        noMove = (state == DeathAnimationHandler.AnimationState.IN_PROGRESS);
    }

    /**
     * Checks if movement is disabled.
     *
     * @return True if movement is disabled, false otherwise.
     */
    public boolean isNoMove() {
        return noMove;
    }

    /**
     * Handles the movement of the player's character and updates the score.
     *
     * @param dx      Horizontal displacement.
     * @param dy      Vertical displacement.
     * @param key     The key associated with the movement.
     * @param jumping Indicates if the character is jumping.
     */
    public void moveAnimal(double dx, double dy, KeyCode key, boolean jumping) {
        Image img = imageManager.getImageForKey(key, jumping);
        if (img != null) {
            animal.setImage(img);
            animal.move(dx, dy);
            scoreManager.handleMovementScore(dy);
        }
    }

    /**
     * Adds points to the score.
     *
     * @param points The points to add.
     */
    public void addPoints(int points) {
        scoreManager.addPoints(points);
    }

    /**
     * Retrieves the current score.
     *
     * @return The current score.
     */
    public int getPoints() {
        return scoreManager.getPoints();
    }

    /**
     * Checks if the score has changed.
     *
     * @return True if the score has changed, false otherwise.
     */
    public boolean hasScoreChanged() {
        return scoreManager.hasScoreChanged();
    }

    /**
     * Resets the score change flag.
     */
    public void resetScoreChangedFlag() {
        scoreManager.resetScoreChangedFlag();
    }

    /**
     * Updates the character's state based on the current game state.
     *
     * @param now Current time in nanoseconds.
     */
    @Override
    public void update(long now) {
        if (noMove) {
            DeathAnimationHandler.AnimationState state = deathAnimationHandler.animateDeath(
                    now, new DeathAnimationContext(animal, imageManager, DEFAULT_DEATH_TYPE, DEFAULT_DEATH_FRAMES)
            );
            noMove = (state == DeathAnimationHandler.AnimationState.IN_PROGRESS);
        }
    }
}
