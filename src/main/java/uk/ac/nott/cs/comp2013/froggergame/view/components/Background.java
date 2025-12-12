package uk.ac.nott.cs.comp2013.froggergame.view.components;

import javafx.scene.image.ImageView;

import uk.ac.nott.cs.comp2013.froggergame.view.resource.ImageViewFactory;

/**
 * Represents the background of the game.
 * The background is an image that spans the entire game screen.
 */
public class Background extends ImageView {
	private static final double DEFAULT_WIDTH = 600; // Default width of the background
	private static final double DEFAULT_HEIGHT = 800; // Default height of the background

	/**
	 * Constructs a new Background with the specified image.
	 *
	 * @param imageLink The path to the background image file.
	 */
	public Background(String imageLink) {
		super();
		// Use the ImageViewFactory to configure the background image dimensions
		ImageViewFactory.ConfiguredImageView configuredImage =
				ImageViewFactory.createConfiguredImageView(imageLink, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setImage(configuredImage.image());
		setFitWidth(configuredImage.width());
		setFitHeight(configuredImage.height());
	}
}
