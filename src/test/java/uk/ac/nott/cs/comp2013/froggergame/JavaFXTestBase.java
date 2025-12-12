package uk.ac.nott.cs.comp2013.froggergame;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;

/**
 * Base class for JavaFX-based tests.
 * Ensures that the JavaFX toolkit is initialized before any tests are run.
 */
public abstract class JavaFXTestBase {

    // Tracks whether the JavaFX toolkit has been initialized
    private static boolean toolkitInitialized = false;

    /**
     * Initializes the JavaFX toolkit once before all tests in derived classes.
     * This is necessary to avoid toolkit-related errors in JavaFX tests.
     */
    @BeforeAll
    static void initToolkit() {
        if (!toolkitInitialized) {
            Platform.startup(() -> {}); // Starts the JavaFX application thread
            toolkitInitialized = true; // Mark toolkit as initialized
        }
    }
}
