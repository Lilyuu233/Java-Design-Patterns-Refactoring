package uk.ac.nott.cs.comp2013.froggergame.model.logic.collision;

import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.StateManager;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Score;

/**
 * CollisionStrategy interface that defines the method for handling collisions.
 * Classes implementing this interface will define the specific collision handling logic.
 */
public interface CollisionStrategy {

    /**
     * Handles the collision between the animal and another object.
     *
     * @param animal The animal object, representing the subject involved in the collision.
     * @param collider The object the animal collides with, can be any type of object.
     * @param stateManager The game state manager, used to manage changes in the game state.
     * @param scoreDisplay The score display component, used to update and show the score.
     */
    void handleCollision(Animal animal, Object collider, StateManager stateManager, Score scoreDisplay);
}
