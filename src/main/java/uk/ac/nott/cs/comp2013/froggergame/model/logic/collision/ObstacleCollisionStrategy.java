package uk.ac.nott.cs.comp2013.froggergame.model.logic.collision;

import javafx.application.Platform;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.StateManager;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Score;

public class ObstacleCollisionStrategy implements CollisionStrategy {
    @Override
    public void handleCollision(Animal animal, Object collider, StateManager stateManager, Score scoreDisplay) {
        animal.setCarDeath(true);
        stateManager.addPoints(-50);
        Platform.runLater(() -> scoreDisplay.updateScore(stateManager.getPoints()));
    }
}

