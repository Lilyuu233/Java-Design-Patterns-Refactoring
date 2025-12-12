package uk.ac.nott.cs.comp2013.froggergame.model.entities;

import javafx.scene.image.Image;

import uk.ac.nott.cs.comp2013.froggergame.model.base.Actor;

/**
 * Represents a moving obstacle in the game.
 */
public class Obstacle extends Actor {

	/**
	 * Constructs an obstacle with the given properties.
	 *
	 * @param imageLink Path to the obstacle image.
	 * @param xpos      Initial X position.
	 * @param ypos      Initial Y position.
	 * @param speed     Movement speed.
	 * @param width     Width of the image.
	 * @param height    Height of the image.
	 */
	public Obstacle(String imageLink, int xpos, int ypos, double speed, int width, int height) {
		super(xpos, ypos, speed);
		try {
			setImage(new Image(imageLink, width, height, true, true));
		} catch (RuntimeException ignored) {
		}
		configureBounds(-50, 600, 600, -200);
	}

	/**
	 * Moves the obstacle and resets its position if out of bounds.
	 *
	 * @param now Current game time in nanoseconds.
	 */
	@Override
	public void act(long now) {
		move(speed, 0);
		resetPositionIfOutOfBounds(speed);
	}
}
