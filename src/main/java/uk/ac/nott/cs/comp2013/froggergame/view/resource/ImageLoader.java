package uk.ac.nott.cs.comp2013.froggergame.view.resource;

import javafx.scene.image.*;
import java.util.Objects;

/**
 * Utility class for loading images with a predefined base path.
 * This class helps streamline image loading for the game by appending a common base path to the file paths.
 */
public class ImageLoader {

    // Base path for all image resources.
    private static final String BASE_PATH = "/images/";

    /**
     * Loads an image from the specified file path with the given dimensions.
     *
     * @param filePath The relative path to the image file, without the base path.
     * @param width    The desired width of the image.
     * @param height   The desired height of the image.
     * @return A new Image object loaded from the specified file path and scaled to the given dimensions.
     * @throws NullPointerException     If the file path is null.
     * @throws IllegalArgumentException If the image resource cannot be found.
     */
    public static Image loadImage(String filePath, double width, double height) {
        // Ensure the file path is not null.
        Objects.requireNonNull(filePath, "Image file path cannot be null.");
        String fullPath = BASE_PATH + filePath;
        try {
            // Load the image using the full path and specified dimensions.
            return new Image(
                    Objects.requireNonNull(ImageLoader.class.getResource(fullPath)).toExternalForm(),
                    width, height, true, true
            );
        } catch (NullPointerException e) {
            // Throw an exception if the image resource cannot be found.
            throw new IllegalArgumentException("Image resource not found: " + fullPath, e);
        }
    }
}
