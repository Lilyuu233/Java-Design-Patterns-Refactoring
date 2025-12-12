package uk.ac.nott.cs.comp2013.froggergame.model.logic.collision;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.nott.cs.comp2013.froggergame.model.entities.*;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.StateManager;
import uk.ac.nott.cs.comp2013.froggergame.view.components.End;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Score;

/**
 * Handles collision logic for the Animal entity with various game objects.
 * Registers and processes specific actions for each type of collision.
 */
public class AnimalCollisionHandler implements CollisionHandler {
    private final Animal animal;
    private final StateManager stateManager;
    private final Score scoreDisplay;
    private final Map<Class<?>, CollisionStrategy> collisionStrategies = new HashMap<>();

    /**
     * Initializes the collision handler for the specified animal with state management and score display.
     *
     * @param animal       The Animal entity to handle collisions for.
     * @param stateManager Manages the state of the animal.
     * @param scoreDisplay Updates the displayed score.
     */
    public AnimalCollisionHandler(Animal animal, StateManager stateManager, Score scoreDisplay) {
        this.animal = animal;
        this.stateManager = stateManager;
        this.scoreDisplay = scoreDisplay;
        registerStrategies();
    }

    /**
     * Checks for collisions and invokes the appropriate actions for detected collisions.
     */
    private void registerStrategies() {
        collisionStrategies.put(Obstacle.class, new ObstacleCollisionStrategy());
        collisionStrategies.put(Log.class, new LogCollisionStrategy());
        collisionStrategies.put(WetTurtle.class, new WetTurtleCollisionStrategy());
        collisionStrategies.put(End.class, new EndCollisionStrategy());
        collisionStrategies.put(Spider.class, new SpiderCollisionStrategy());
        collisionStrategies.put(Turtle.class, new TurtleCollisionStrategy());
    }

    @Override
    public void checkCollisions() {
        for (Class<?> colliderType : collisionStrategies.keySet()) {
            List<?> collisions = animal.getIntersectingObjects(colliderType);
            if (!collisions.isEmpty()) {
                for (Object collider : collisions) {
                    CollisionStrategy strategy = collisionStrategies.get(colliderType);
                    if (strategy != null) {
                        strategy.handleCollision(animal, collider, stateManager, scoreDisplay);
                    }
                }
                return;
            }
        }
        if (animal.isOnPlatform()) {
            animal.clearCurrentPlatform();
        }
    }
}
