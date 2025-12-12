package uk.ac.nott.cs.comp2013.froggergame.model.logic.collision;

import javafx.application.Platform;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.StateManager;
import uk.ac.nott.cs.comp2013.froggergame.view.components.End;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Score;

public class EndCollisionStrategy implements CollisionStrategy {
    @Override
    public void handleCollision(Animal animal, Object collider, StateManager stateManager, Score scoreDisplay) {
        End end = (End) collider;

        if (end.isActivated()) {
            stateManager.addPoints(-50);
        } else {
            end.setEnd();
            stateManager.addPoints(50);
        }
        Platform.runLater(() -> scoreDisplay.updateScore(stateManager.getPoints()));
        animal.resetPosition();
    }
}
