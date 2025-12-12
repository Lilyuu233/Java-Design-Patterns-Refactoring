package uk.ac.nott.cs.comp2013.froggergame.model.entities;

/**
 * Represents a "wet turtle" in the game, which alternates between floating and sinking.
 */
public class WetTurtle extends AbstractTurtle {
	private boolean sunk = false; // Indicates if the turtle is currently sunk

	/**
	 * Constructs a WetTurtle with the specified position, speed, and dimensions.
	 *
	 * @param xpos   The initial X position.
	 * @param ypos   The initial Y position.
	 * @param speed  The movement speed.
	 * @param width  The width of the turtle's image.
	 * @param height The height of the turtle's image.
	 */
	public WetTurtle(int xpos, int ypos, int speed, int width, int height) {
		super(xpos, ypos, speed, width, height, new String[]{
				"TurtleAnimation1.png",
				"TurtleAnimation2Wet.png",
				"TurtleAnimation3Wet.png",
				"TurtleAnimation4Wet.png"
		});
	}

	/**
	 * Checks if the turtle is currently sunk.
	 *
	 * @return True if the turtle is sunk, otherwise false.
	 */
	public boolean isSunk() {
		return sunk;
	}

	/**
	 * Updates the turtle's state based on the current animation frame.
	 * Sets the turtle to "sunk" if the last frame is reached.
	 *
	 * @param frame The current animation frame index.
	 */
	@Override
	public void updateStateBasedOnFrame(int frame) {
		sunk = (frame == animationFrames.length - 1);
	}
}
