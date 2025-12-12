package uk.ac.nott.cs.comp2013.froggergame.view.components;

import javafx.scene.image.ImageView;

import uk.ac.nott.cs.comp2013.froggergame.view.resource.ImageViewFactory;

/**
 * Represents a digit image displayed in the game UI.
 * This class is used to render a single digit with specific dimensions and position.
 */
public class Digit extends ImageView {

	/**
	 * Constructs a Digit object to display a single digit in the UI.
	 *
	 * @param value     The numeric value of the digit (must be between 0 and 9).
	 * @param dimension The width and height of the digit image.
	 * @param x         The x-coordinate of the digit's position.
	 * @param y         The y-coordinate of the digit's position.
	 * @throws IllegalArgumentException if the digit value is not between 0 and 9.
	 */
	public Digit(int value, int dimension, int x, int y) {
		super();
		if (value < 0 || value > 9) {
			throw new IllegalArgumentException("Digit value must be between 0 and 9.");
		}

		ImageViewFactory.ConfiguredImageView configuredImage =
				ImageViewFactory.createConfiguredImageView(value + ".png", dimension, dimension);
		setImage(configuredImage.image());
		setFitWidth(configuredImage.width());
		setFitHeight(configuredImage.height());
		setX(x);
		setY(y);
	}
}
