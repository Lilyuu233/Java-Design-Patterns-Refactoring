package uk.ac.nott.cs.comp2013.froggergame.view.resource;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Manages and preloads images used in the game.
 * Provides methods to retrieve images for specific states or directions.
 */
public class ImageManager {

    // Constants for image dimensions and naming conventions.
    private static final int IMAGE_WIDTH = 40;
    private static final int IMAGE_HEIGHT = 40;
    private static final String JUMP_SUFFIX = "Jump";

    // Stores preloaded images mapped by their keys.
    private final Map<String, Image> images = new HashMap<>();

    /**
     * Initializes the ImageManager and preloads all necessary images.
     */
    public ImageManager() {
        preloadImages();
    }

    /**
     * Preloads images for all directional states (e.g., Up, Down, Left, Right) and jumping states.
     */
    private void preloadImages() {
        loadDirectionalImages("frogger", ""); // Normal directional images
        loadDirectionalImages("frogger", JUMP_SUFFIX); // Jumping directional images
    }

    /**
     * Loads images for each direction (Up, Down, Left, Right) with the specified suffix.
     *
     * @param baseName The base name of the image (e.g., "frogger").
     * @param suffix   The suffix to append to the image name (e.g., "Jump").
     */
    private void loadDirectionalImages(String baseName, String suffix) {
        for (String direction : new String[]{"Up", "Left", "Down", "Right"}) {
            String key = baseName + direction + suffix;
            String filePath = key + ".png";
            loadImage(key, filePath);
        }
    }

    /**
     * Loads an image into the manager's cache.
     *
     * @param key      The unique key for the image.
     * @param filePath The file path to the image.
     */
    private void loadImage(String key, String filePath) {
        images.put(key, ImageLoader.loadImage(filePath, IMAGE_WIDTH, IMAGE_HEIGHT));
    }

    /**
     * Retrieves the image for a specific direction and jumping state.
     *
     * @param key     The KeyCode representing the direction (e.g., W, A, S, D).
     * @param jumping Whether the frog is in a jumping state.
     * @return The corresponding Image, or null if no image matches.
     */
    public Image getImageForKey(KeyCode key, boolean jumping) {
        String direction = switch (key) {
            case W -> "Up";
            case A -> "Left";
            case S -> "Down";
            case D -> "Right";
            default -> null;
        };
        if (direction == null) {
            return null;
        }
        String imageKey = "frogger" + direction + (jumping ? JUMP_SUFFIX : "");
        return images.get(imageKey);
    }

    /**
     * Retrieves the death animation image for a specific type and frame.
     *
     * @param type  The type of death (e.g., "cardeath", "waterdeath").
     * @param frame The animation frame number.
     * @return The corresponding Image.
     * @throws NullPointerException if the type is null.
     */
    public Image getDeathImage(String type, int frame) {
        Objects.requireNonNull(type, "Type cannot be null");
        return ImageLoader.loadImage(type + frame + ".png", IMAGE_WIDTH, IMAGE_HEIGHT);
    }

    /**
     * Retrieves the default image for the frog.
     *
     * @return The default Image for the frog in the "Up" direction.
     */
    public Image getDefaultImage() {
        return images.get("froggerUp");
    }
}
