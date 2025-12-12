package uk.ac.nott.cs.comp2013.froggergame.model.logic.collision;

import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Log;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.StateManager;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Score;

public class LogCollisionStrategy implements CollisionStrategy {
    @Override
    public void handleCollision(Animal animal, Object collider, StateManager stateManager, Score scoreDisplay) {
        animal.setCurrentPlatform((Log) collider);
    }
}

