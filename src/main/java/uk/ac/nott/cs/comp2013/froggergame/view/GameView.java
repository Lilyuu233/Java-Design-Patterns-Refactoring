package uk.ac.nott.cs.comp2013.froggergame.view;

import javafx.scene.layout.Pane;

import uk.ac.nott.cs.comp2013.froggergame.view.components.Background;
import uk.ac.nott.cs.comp2013.froggergame.view.scenes.MyStage;

/**
 * Represents the main game view, which includes the background and game stage.
 */
public class GameView extends Pane {
    private MyStage background; // The game stage where actors and game logic reside
    private Background staticBackground; // Static visual background for the game

    /**
     * Constructs a GameView and initializes its components and layout.
     */
    public GameView() {
        initializeComponents();
        setupLayout();
    }

    /**
     * Initializes the game stage and static background components.
     */
    private void initializeComponents() {
        background = new MyStage();
        staticBackground = new Background("background.png");
    }

    /**
     * Starts the game stage, enabling game logic and animations.
     */
    public void startBackground() {
        background.start();
    }

    /**
     * Plays the background music for the game.
     */
    public void playBackgroundMusic() {
        background.playMusic();
    }

    /**
     * Sets up the layout of the game view by adding the static background
     * and game stage components to the scene.
     */
    private void setupLayout() {
        if (staticBackground != null) {
            getChildren().add(staticBackground);
        }
        getChildren().add(background);
    }

    /**
     * Provides access to the game stage.
     *
     * @return The game stage (MyStage) instance.
     */
    public MyStage getGameStage() {
        return background;
    }
}
