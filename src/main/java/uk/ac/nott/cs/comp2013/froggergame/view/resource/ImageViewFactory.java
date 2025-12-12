package uk.ac.nott.cs.comp2013.froggergame.view.resource;

import javafx.scene.image.Image;

/**
 * Factory class for creating pre-configured ImageView objects.
 * Provides utility methods to load images and encapsulate them with metadata.
 */
public class ImageViewFactory {

    /**
     * Creates a ConfiguredImageView with the specified file path, width, and height.
     *
     * @param filePath The file path to the image resource.
     * @param width    The desired width of the image.
     * @param height   The desired height of the image.
     * @return A ConfiguredImageView containing the loaded image and its dimensions.
     */
    public static ConfiguredImageView createConfiguredImageView(String filePath, double width, double height) {
        Image image = ImageLoader.loadImage(filePath, width, height);
        return new ConfiguredImageView(image, width, height);
    }

    /**
     * Record to encapsulate an Image along with its configured dimensions.
     *
     * @param image  The loaded Image object.
     * @param width  The configured width of the image.
     * @param height The configured height of the image.
     */
    public record ConfiguredImageView(Image image, double width, double height) {
    }
}
