package uk.ac.nott.cs.comp2013.froggergame.model.factory;

import uk.ac.nott.cs.comp2013.froggergame.view.resource.ImageLoader;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.*;
import uk.ac.nott.cs.comp2013.froggergame.view.scenes.MyStage;

public class GameObjectFactory {
    private static final String FROG_IMAGE = "froggerUp.png";

    // Creates an animal (frog) for the game.
    public Animal createAnimal() {
        return new Animal(ImageLoader.loadImage(FROG_IMAGE, 40, 40).getUrl());
    }

    // Creates a log with specified properties.
    public Log createLog(String imageName, int width, int height, int xpos, int ypos, int yLevel, double speed) {
        String imageUrl = ImageLoader.loadImage(imageName, width, height).getUrl();
        return new Log(imageUrl, xpos, ypos, yLevel, speed);
    }

    // Creates an obstacle with specified properties.
    public Obstacle createObstacle(String imageName, int width, int height, int xpos, int ypos, int speed) {
        String imageUrl = ImageLoader.loadImage(imageName, width, height).getUrl();
        return new Obstacle(imageUrl, xpos, ypos, speed, width, height);
    }

    // Creates a turtle or wet turtle based on the "isWet" flag.
    public AbstractTurtle createTurtle(int xpos, int ypos, int speed, int width, int height, boolean isWet) {
        if (isWet) {
            return new WetTurtle(xpos, ypos, speed, width, height);
        } else {
            return new Turtle(xpos, ypos, speed, width, height);
        }
    }

    // Interface for mapping and setting up game objects.
    public interface GameObjectSetup<T> {
        T map(Object[] rawData);
        void setup(MyStage background, T gameObject);
    }

    // Generalized setup method for adding game objects to the stage.
    public <T> void setupGameObjects(MyStage background, Object[][] data, GameObjectSetup<T> setup) {
        for (Object[] rawData : data) {
            T gameObject = setup.map(rawData);
            setup.setup(background, gameObject);
        }
    }

    // Sets up logs on the game stage using log data.
    public void setupLogs(MyStage background, Object[][] logData) {
        setupGameObjects(background, logData, new GameObjectSetup<Log>() {
            @Override
            public Log map(Object[] rawData) {
                return createLog(
                        (String) rawData[0], (int) rawData[1], (int) rawData[2], (int) rawData[3],
                        (int) rawData[4], (int) rawData[5], (double) rawData[6]
                );
            }

            @Override
            public void setup(MyStage background, Log gameObject) {
                background.addActor(gameObject);
            }
        });
    }

    // Sets up obstacles on the game stage using obstacle data.
    public void setupObstacles(MyStage background, Object[][] obstacleData) {
        setupGameObjects(background, obstacleData, new GameObjectSetup<Obstacle>() {
            @Override
            public Obstacle map(Object[] rawData) {
                return createObstacle(
                        (String) rawData[0], (int) rawData[1], (int) rawData[2], (int) rawData[3],
                        (int) rawData[4], (int) rawData[5]
                );
            }

            @Override
            public void setup(MyStage background, Obstacle gameObject) {
                background.addActor(gameObject);
            }
        });
    }

    // Sets up turtles on the game stage using turtle data.
    public void setupTurtles(MyStage background, Object[][] turtleData) {
        setupGameObjects(background, turtleData, new GameObjectSetup<AbstractTurtle>() {
            @Override
            public AbstractTurtle map(Object[] rawData) {
                return createTurtle(
                        (int) rawData[0], (int) rawData[1], (int) rawData[2], (int) rawData[3],
                        (int) rawData[4], (boolean) rawData[5]
                );
            }

            @Override
            public void setup(MyStage background, AbstractTurtle gameObject) {
                background.addActor(gameObject);
            }
        });
    }
}
