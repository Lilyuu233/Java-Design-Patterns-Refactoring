package uk.ac.nott.cs.comp2013.froggergame.view.scenes;

import uk.ac.nott.cs.comp2013.froggergame.model.base.Actor;
import uk.ac.nott.cs.comp2013.froggergame.model.data.LevelConfig;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Level;
import uk.ac.nott.cs.comp2013.froggergame.view.components.TimeBar;
import uk.ac.nott.cs.comp2013.froggergame.view.resource.MusicManager;

/**
 * Represents the main stage of the Frogger game.
 * Manages game components like music, level layout, and time bar.
 */
public class MyStage extends World {
	private static final String DEFAULT_MUSIC_PATH = "/music/Snake_Charmer.mp3";

	private final MusicManager musicManager = new MusicManager(DEFAULT_MUSIC_PATH);
	private final Level level = new Level(this);
	private TimeBar timeBar;

	/**
	 * Constructs the main stage and initializes the game components.
	 */
	public MyStage() {
		initializeGame();
	}

	/**
	 * Initializes the game components, including the level layout and time bar.
	 */
	private void initializeGame() {
		level.updateLevelDisplay(1);

		// Use a default LevelConfig for initialization
		LevelConfig defaultLevelConfig = createDefaultLevelConfig();
		level.resetLevelLayout(defaultLevelConfig);

		this.timeBar = new TimeBar(this);
		this.timeBar.start();
	}

	/**
	 * Creates a default level configuration for the stage.
	 *
	 * @return The default LevelConfig instance.
	 */
	private LevelConfig createDefaultLevelConfig() {
		return new LevelConfig(1, 100, 50, 120, 2);
	}

	/**
	 * Starts playing background music.
	 */
	public void playMusic() {
		musicManager.playMusic();
	}

	/**
	 * Stops the background music.
	 */
	public void stopMusic() {
		musicManager.stopMusic();
	}

	/**
	 * Retrieves the time bar component.
	 *
	 * @return The time bar instance.
	 */
	public TimeBar getTimeBar() {
		return timeBar;
	}

	/**
	 * Stops the time bar countdown.
	 */
	public void stopTimeBar() {
		if (timeBar != null) {
			timeBar.stop();
		}
	}

	/**
	 * Updates the game state for all actors in the stage.
	 *
	 * @param now The current time in nanoseconds.
	 */
	public void act(long now) {
		for (Actor actor : getObjects(Actor.class)) {
			actor.act(now);
		}
	}
}
