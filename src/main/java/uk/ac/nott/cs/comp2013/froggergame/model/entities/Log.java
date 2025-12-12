package uk.ac.nott.cs.comp2013.froggergame.model.entities;

import java.util.Random;

import uk.ac.nott.cs.comp2013.froggergame.model.base.Actor;
import uk.ac.nott.cs.comp2013.froggergame.view.scenes.World;

/**
 * Represents a log in the game that moves horizontally and can randomly generate spiders on it.
 */
public class Log extends Actor {
	private Spider spider; // Spider associated with the log
	private static final Random RANDOM = new Random(); // Random generator for spider appearance

	/**
	 * Constructs a log with the specified image, size, position, and speed.
	 *
	 * @param imageLink Path to the log image
	 * @param size      Size of the log image
	 * @param xPos      Initial X position
	 * @param yPos      Initial Y position
	 * @param speed     Movement speed of the log
	 */
	public Log(String imageLink, int size, int xPos, int yPos, double speed) {
		super(xPos, yPos, speed);
		try {
			setImage(new javafx.scene.image.Image(imageLink, size, size, true, true));
		} catch (RuntimeException ignored) {
		}
		configureBounds(-300, 600, 700, -180);
	}

	/**
	 * Randomly adds a spider to the log with a 50% chance.
	 *
	 * @param spiderImagePath Path to the spider image
	 */
	public void addRandomSpider(String spiderImagePath) {
		if (spider == null && RANDOM.nextBoolean()) { // 50% chance
			spider = new Spider(this, spiderImagePath);
			if (this.getParent() instanceof World world) {
				world.addActor(spider); // Add spider to the game world
			}
		}
	}

	/**
	 * Gets the spider associated with the log.
	 *
	 * @return The spider on the log, or null if none exists
	 */
	public Spider getSpider() {
		return spider;
	}

	/**
	 * Clears the spider from the log.
	 */
	public void clearSpider() {
		this.spider = null;
	}

	/**
	 * Defines the behavior of the log during each game tick.
	 * Moves the log horizontally and updates the spider's position if present.
	 *
	 * @param now The current time in nanoseconds
	 */
	@Override
	public void act(long now) {
		move(speed, 0); // Move the log horizontally
		resetPositionIfOutOfBounds(speed); // Reset position if the log goes out of bounds
		if (spider != null) {
			spider.updatePosition(); // Update spider's position to match the log
		}
	}
}
