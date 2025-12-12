package uk.ac.nott.cs.comp2013.froggergame.model.logic.collision;

import javafx.application.Platform;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.WetTurtle;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.StateManager;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Score;

public class WetTurtleCollisionStrategy implements CollisionStrategy {
    @Override
    public void handleCollision(Animal animal, Object collider, StateManager stateManager, Score scoreDisplay) {
        WetTurtle wetTurtle = (WetTurtle) collider;
        if (wetTurtle.isSunk()) {
            animal.clearCurrentPlatform();
            animal.setWaterDeath(true);
            stateManager.addPoints(-50);
            Platform.runLater(() -> scoreDisplay.updateScore(stateManager.getPoints()));
            animal.resetPosition();
        } else {
            animal.setCurrentPlatform(wetTurtle);
        }
    }
}
