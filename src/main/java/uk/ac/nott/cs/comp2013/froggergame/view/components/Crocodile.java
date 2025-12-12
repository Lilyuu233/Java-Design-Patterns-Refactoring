package uk.ac.nott.cs.comp2013.froggergame.view.components;

import javafx.scene.image.Image;

/**
 * Utility class for initializing the image representation of a Crocodile.
 */
public class Crocodile {
    /**
     * Sets the image for a Crocodile entity with specified dimensions.
     *
     * @param crocodile The Crocodile entity to configure.
     * @param imagePath The path to the crocodile's image file.
     */
    public static void initializeImage(uk.ac.nott.cs.comp2013.froggergame.model.entities.Crocodile crocodile, String imagePath) {
        crocodile.setImage(new Image(imagePath, 80, 80, true, true)); // Set the image with specified width and height
    }
}
