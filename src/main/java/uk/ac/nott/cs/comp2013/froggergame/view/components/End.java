package uk.ac.nott.cs.comp2013.froggergame.view.components;

import javafx.animation.PauseTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import java.util.Objects;

import uk.ac.nott.cs.comp2013.froggergame.view.managers.GameComponent;
import uk.ac.nott.cs.comp2013.froggergame.view.resource.ImageLoader;

/**
 * Represents an endpoint in the game where the player can reach to complete objectives.
 * This class manages activation states, Easter egg spawning, and rendering the endpoint.
 */
public class End extends Pane implements GameComponent {
	// Image paths for the default, activated, and Easter egg states
	private static final String DEFAULT_IMAGE_PATH = "End.png";
	private static final String ACTIVATED_IMAGE_PATH = "FrogEnd.png";
	private static final String EASTER_EGG_IMAGE_PATH = "/images/egg.png";

	// Constants for size and timings
	private static final int IMAGE_SIZE = 60;
	private static final int EASTER_EGG_SIZE = 40;
	private static final double EASTER_EGG_DURATION_SECONDS = 5.0;
	private static final double EASTER_EGG_SPAWN_CHANCE = 0.3;
	private static final double MIN_RANDOM_DELAY_SECONDS = 1.0;
	private static final double MAX_RANDOM_DELAY_SECONDS = 10.0;

	// State and UI components
	private boolean isActivated; // Whether the endpoint has been activated
	private final ImageView endImageView; // The image view for the endpoint
	private boolean hasEasterEgg; // Whether an Easter egg is currently displayed
	private ImageView easterEggImageView; // The image view for the Easter egg
	private PauseTransition easterEggTimer; // Timer to handle Easter egg visibility duration
	private PauseTransition spawnTimer; // Timer to handle scheduling of Easter egg spawns

	/**
	 * Constructor to initialize the endpoint at the given position.
	 *
	 * @param x The x-coordinate of the endpoint.
	 * @param y The y-coordinate of the endpoint.
	 */
	public End(int x, int y) {
		// Load and set the default image for the endpoint
		Image defaultImage = ImageLoader.loadImage(DEFAULT_IMAGE_PATH, IMAGE_SIZE, IMAGE_SIZE);
		endImageView = new ImageView(defaultImage);
		endImageView.setFitWidth(IMAGE_SIZE);
		endImageView.setFitHeight(IMAGE_SIZE);
		setLayoutX(x);
		setLayoutY(y);
		getChildren().add(endImageView);

		// Initialize state and timers
		this.isActivated = false;
		this.hasEasterEgg = false;
		initializeTimers();
		scheduleNextEasterEgg();
	}

	/**
	 * Activates the endpoint, changing its appearance and stopping any timers.
	 */
	public void setEnd() {
		if (!isActivated) {
			stopTimers();
			clearEasterEgg();
			Image activatedImage = ImageLoader.loadImage(ACTIVATED_IMAGE_PATH, IMAGE_SIZE, IMAGE_SIZE);
			endImageView.setImage(activatedImage);
			isActivated = true;
		}
	}

	/**
	 * Initializes timers for managing Easter egg visibility and spawning.
	 */
	private void initializeTimers() {
		easterEggTimer = new PauseTransition(Duration.seconds(EASTER_EGG_DURATION_SECONDS));
		easterEggTimer.setOnFinished(event -> {
			clearEasterEgg();
			scheduleNextEasterEgg();
		});
	}

	/**
	 * Resets the endpoint to its default state, clearing any activation or Easter egg.
	 */
	public void reset() {
		stopTimers();
		resetToDefaultImage();
		isActivated = false;
		hasEasterEgg = false;
		if (easterEggImageView != null) {
			getChildren().remove(easterEggImageView);
			easterEggImageView = null;
		}
		initializeTimers();
		scheduleNextEasterEgg();
	}

	/**
	 * Stops all active timers for Easter egg visibility and spawning.
	 */
	private void stopTimers() {
		if (easterEggTimer != null) {
			easterEggTimer.stop();
		}
		if (spawnTimer != null) {
			spawnTimer.stop();
		}
	}

	/**
	 * Resets the endpoint's image to the default appearance.
	 */
	private void resetToDefaultImage() {
		Image defaultImage = ImageLoader.loadImage(DEFAULT_IMAGE_PATH, IMAGE_SIZE, IMAGE_SIZE);
		endImageView.setImage(defaultImage);
	}

	/**
	 * Checks if the endpoint has been activated.
	 *
	 * @return True if the endpoint is activated, false otherwise.
	 */
	public boolean isActivated() {
		return isActivated;
	}

	/**
	 * Schedules the next attempt to spawn an Easter egg after a random delay.
	 */
	private void scheduleNextEasterEgg() {
		if (isActivated) return;

		double randomDelay = MIN_RANDOM_DELAY_SECONDS + Math.random() * (MAX_RANDOM_DELAY_SECONDS - MIN_RANDOM_DELAY_SECONDS);
		if (spawnTimer != null) {
			spawnTimer.stop();
		}
		this.spawnTimer = new PauseTransition(Duration.seconds(randomDelay));
		this.spawnTimer.setOnFinished(event -> {
			if (!isActivated) {
				trySpawnEasterEgg();
			}
		});
		this.spawnTimer.play();
	}

	/**
	 * Attempts to spawn an Easter egg at the endpoint based on a random chance.
	 */
	private void trySpawnEasterEgg() {
		if (!hasEasterEgg && !isActivated && Math.random() < EASTER_EGG_SPAWN_CHANCE) {
			initializeEasterEgg();
		} else {
			scheduleNextEasterEgg();
		}
	}

	/**
	 * Initializes and displays an Easter egg at the endpoint.
	 */
	private void initializeEasterEgg() {
		if (isActivated) return;

		String easterEggImagePath = Objects.requireNonNull(getClass().getResource(EASTER_EGG_IMAGE_PATH)).toExternalForm();
		Image easterEggImage = new Image(easterEggImagePath, EASTER_EGG_SIZE, EASTER_EGG_SIZE, true, true);
		easterEggImageView = new ImageView(easterEggImage);
		easterEggImageView.setFitWidth(EASTER_EGG_SIZE);
		easterEggImageView.setFitHeight(EASTER_EGG_SIZE);
		double centerX = (IMAGE_SIZE - EASTER_EGG_SIZE) / 2.0;
		double centerY = (IMAGE_SIZE - EASTER_EGG_SIZE) / 2.0;
		easterEggImageView.setLayoutX(centerX);
		easterEggImageView.setLayoutY(centerY);
		getChildren().add(easterEggImageView);
		hasEasterEgg = true;

		if (easterEggTimer != null) {
			easterEggTimer.playFromStart();
		}
	}

	/**
	 * Clears the Easter egg from the endpoint if it exists.
	 */
	public void clearEasterEgg() {
		if (hasEasterEgg && easterEggImageView != null) {
			getChildren().remove(easterEggImageView);
			easterEggImageView = null;
			hasEasterEgg = false;
		}
	}

	/**
	 * Checks if the endpoint currently has an Easter egg.
	 *
	 * @return True if an Easter egg is present, false otherwise.
	 */
	public boolean hasEasterEgg() {
		return hasEasterEgg;
	}

	/**
	 * Renders the endpoint onto the specified pane.
	 *
	 * @param pane The pane where the endpoint will be added.
	 */
	@Override
	public void render(Pane pane) {
		pane.getChildren().add(this);
	}
}
