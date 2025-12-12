package uk.ac.nott.cs.comp2013.froggergame.controller;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.ac.nott.cs.comp2013.froggergame.JavaFXTestBase;
import uk.ac.nott.cs.comp2013.froggergame.controller.menu.MainMenuController;
import uk.ac.nott.cs.comp2013.froggergame.view.MainMenuView;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MainMenuControllerTest extends JavaFXTestBase {

    private Stage mockStage;
    private MainMenuController mockController;
    private MainMenuView mainMenuView;

    @BeforeEach
    void setUp() {
        mockStage = mock(Stage.class);
        mockController = mock(MainMenuController.class);
        mainMenuView = new MainMenuView(mockStage, mockController);
    }

    @Test
    void testShowMenuInitialization() {
        // Act
        mainMenuView.show();

        // Assert
        ArgumentCaptor<Scene> sceneCaptor = ArgumentCaptor.forClass(Scene.class);
        verify(mockStage).setScene(sceneCaptor.capture());
        Scene scene = sceneCaptor.getValue();
        assertNotNull(scene, "Scene should be initialized");

        VBox rootLayout = (VBox) scene.getRoot();
        assertNotNull(rootLayout, "Root layout should be a VBox");
        assertEquals(2, rootLayout.getChildren().size(), "Root layout should contain two children: banner and menu");
    }

    @Test
    void testPlayButtonAction() throws Exception {
        Platform.runLater(() -> {
            // Arrange
            Stage realStage = new Stage();
            MainMenuView realMainMenuView = new MainMenuView(realStage, mockController);

            realMainMenuView.show();

            VBox rootLayout = (VBox) realStage.getScene().getRoot();
            VBox menuLayout = (VBox) rootLayout.getChildren().get(1);
            HBox playButtonContainer = (HBox) menuLayout.getChildren().get(0);
            Button playButton = (Button) playButtonContainer.getChildren().get(1);

            // Act
            playButton.fire(); // Simulate button click
        });

        Thread.sleep(1000);

        // Assert
        verify(mockController).startGame();
    }

    @Test
    void testHighScoresButtonAction() throws Exception {
        Platform.runLater(() -> {
            // Arrange
            Stage realStage = new Stage();
            MainMenuView realMainMenuView = new MainMenuView(realStage, mockController);
            realMainMenuView.show();
            VBox rootLayout = (VBox) realStage.getScene().getRoot();
            VBox menuLayout = (VBox) rootLayout.getChildren().get(1);
            HBox highScoresButtonContainer = (HBox) menuLayout.getChildren().get(1);
            Button highScoresButton = (Button) highScoresButtonContainer.getChildren().get(1);

            // Act
            highScoresButton.fire();

            // Assert
            verify(mockController).showHighScores();
        });
        Thread.sleep(1000);
    }


    @Test
    void testQuitButtonAction() throws Exception {
        Platform.runLater(() -> {
            // Arrange
            Stage realStage = new Stage();
            MainMenuView realMainMenuView = new MainMenuView(realStage, mockController);
            realMainMenuView.show();
            VBox rootLayout = (VBox) realStage.getScene().getRoot();
            VBox menuLayout = (VBox) rootLayout.getChildren().get(1);
            HBox quitButtonContainer = (HBox) menuLayout.getChildren().get(2);
            Button quitButton = (Button) quitButtonContainer.getChildren().get(1);

            // Act
            quitButton.fire();

            // Assert
            verify(mockController).quitGame();
        });

        Thread.sleep(1000);
    }

    @Test
    void testFrogIconVisibilityOnHover() throws Exception {
        Platform.runLater(() -> {
            // Arrange
            Stage realStage = new Stage();
            MainMenuView realMainMenuView = new MainMenuView(realStage, mockController);
            realMainMenuView.show();
            VBox rootLayout = (VBox) realStage.getScene().getRoot();
            VBox menuLayout = (VBox) rootLayout.getChildren().get(1);
            HBox playButtonContainer = (HBox) menuLayout.getChildren().get(0);
            ImageView frogIcon = (ImageView) playButtonContainer.getChildren().get(0);
            Button playButton = (Button) playButtonContainer.getChildren().get(1);
            assertFalse(frogIcon.isVisible(), "Frog icon should initially be hidden");

            // Act
            playButton.fireEvent(new javafx.scene.input.MouseEvent(
                    javafx.scene.input.MouseEvent.MOUSE_ENTERED, 0, 0, 0, 0,
                    javafx.scene.input.MouseButton.NONE, 1, true, true, true, true,
                    true, true, true, true, true, true, null
            ));
            assertTrue(frogIcon.isVisible(), "Frog icon should be visible on hover");

            playButton.fireEvent(new javafx.scene.input.MouseEvent(
                    javafx.scene.input.MouseEvent.MOUSE_EXITED, 0, 0, 0, 0,
                    javafx.scene.input.MouseButton.NONE, 1, true, true, true, true,
                    true, true, true, true, true, true, null
            ));
            assertFalse(frogIcon.isVisible(), "Frog icon should be hidden after hover ends");
        });

        Thread.sleep(1000);
    }

}
