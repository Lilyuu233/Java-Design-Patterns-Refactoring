package uk.ac.nott.cs.comp2013.froggergame.view.resource;

import java.util.Objects;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Manages background music playback for the game.
 * Handles music loading, playing, and stopping functionality.
 */
public class MusicManager {

    private MediaPlayer mediaPlayer; // The media player instance
    private final String musicFilePath; // Path to the music file resource

    /**
     * Constructs a MusicManager for the specified music file.
     *
     * @param musicFilePath The relative path to the music file resource.
     */
    public MusicManager(String musicFilePath) {
        this.musicFilePath = musicFilePath;
        initializeMediaPlayer();
    }

    /**
     * Initializes the MediaPlayer with the provided music file.
     */
    private void initializeMediaPlayer() {
        try {
            String musicFile = Objects.requireNonNull(getClass().getResource(musicFilePath))
                    .toExternalForm();
            Media sound = new Media(musicFile);
            mediaPlayer = new MediaPlayer(sound);
        } catch (NullPointerException | IllegalArgumentException e) {
            System.err.println("Error loading music file: " + musicFilePath);
        }
    }

    /**
     * Plays the music in a loop.
     */
    public void playMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop the music
            mediaPlayer.play();
        }
    }

    /**
     * Stops the music playback.
     */
    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
}
