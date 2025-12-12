package uk.ac.nott.cs.comp2013.froggergame.model.base;

import java.util.List;
import java.util.stream.Collectors;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

import uk.ac.nott.cs.comp2013.froggergame.controller.input.KeyEventHandlerProvider;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.collision.BoundaryManager;
import uk.ac.nott.cs.comp2013.froggergame.view.scenes.World;

/**
 * Represents an abstract actor in the game, providing base functionality
 * for movement, collision detection, and interaction with the game world.
 */
public abstract class Actor extends ImageView implements KeyEventHandlerProvider {
    private EventHandler<? super KeyEvent> keyPressedHandler;
    private EventHandler<? super KeyEvent> keyReleasedHandler;
    protected double speed; // Speed of the actor
    private BoundaryManager boundaryManager;
    private final double baseSpeed;
    private World world;

    /**
     * Constructor to initialize the actor's position and speed.
     *
     * @param xpos  Initial x position
     * @param ypos  Initial y position
     * @param speed Speed of the actor
     */
    public Actor(int xpos, int ypos, double speed) {
        this.setX(xpos);
        this.setY(ypos);
        this.speed = speed;
        this.baseSpeed = speed;
    }

    /**
     * Moves the actor by the given x and y increments.
     *
     * @param dx Change in x position
     * @param dy Change in y position
     */
    public void move(double dx, double dy) {
        this.setX(this.getX() + dx);
        this.setY(Math.max(0.0, this.getY() + dy));
    }

    public void setSpeedFactor(double speedFactor) {
        this.speed = this.baseSpeed * speedFactor;
    }

    public void configureBounds(int screenLeftBound, int screenRightBound, int resetLeftPosition, int resetRightPosition) {
        this.boundaryManager = new BoundaryManager(screenLeftBound, screenRightBound, resetLeftPosition, resetRightPosition);
    }

    public void resetPositionIfOutOfBounds(double speed) {
        if (boundaryManager != null && boundaryManager.isOutOfBounds(this.getX(), speed)) {
            this.setX(boundaryManager.resetPositionBasedOnSpeed(speed));
        }
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public World getWorld() {
        if (world == null) {
            throw new IllegalStateException("Actor is not attached to any World instance.");
        }
        return world;
    }

    /**
     * Gets the actor's speed.
     *
     * @return The speed
     */
    public double getSpeed() {
        return this.speed;
    }

    /**
     * Gets a list of objects intersecting with this actor.
     *
     * @param cls The class of the objects to check
     * @param <A> The type of the objects
     * @return A list of intersecting objects
     */
    public <A> List<A> getIntersectingObjects(Class<A> cls) {
        return this.getObjectsFromWorld(cls).stream()
                .filter(this::isIntersecting)
                .collect(Collectors.toList());
    }

    /**
     * Checks if this actor is intersecting with a given object.
     *
     * @param obj The object to check
     * @return True if intersecting, otherwise false
     */
    private boolean isIntersecting(Object obj) {
        if (obj instanceof Node) {
            Bounds actorBounds = this.localToScene(this.getBoundsInLocal());
            Bounds objectBounds = ((Node) obj).localToScene(((Node) obj).getBoundsInLocal());
            return actorBounds.intersects(objectBounds);
        }
        return false;
    }

    /**
     * Gets objects of the specified class from the game world.
     *
     * @param cls The class of the objects to get
     * @param <A> The type of the objects
     * @return A list of objects
     */
    private <A> List<A> getObjectsFromWorld(Class<A> cls) {
        return this.getWorld().getObjects(cls);
    }

    /**
     * Abstract method to define behavior per game tick.
     *
     * @param now The current time in nanoseconds
     */
    public abstract void act(long now);

    public EventHandler<? super KeyEvent> getKeyPressedHandler() {
        return this.keyPressedHandler;
    }

    public EventHandler<? super KeyEvent> getKeyReleasedHandler() {
        return this.keyReleasedHandler;
    }
}

