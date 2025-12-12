package uk.ac.nott.cs.comp2013.froggergame.view.managers;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.Pane;

/**
 * A composite class that implements the GameComponent interface.
 * This class is used to manage a collection of child components in the game.
 */
public class LevelComposite implements GameComponent {
    // List to store child GameComponents
    private final List<GameComponent> children = new ArrayList<>();

    /**
     * Constructor for LevelComposite.
     * Initializes an empty list of child components.
     */
    public LevelComposite() {
    }

    /**
     * Adds a child component to the composite.
     *
     * @param component The GameComponent to be added.
     */
    public void addComponent(GameComponent component) {
        this.children.add(component);
    }

    /**
     * Renders all child components onto the given pane.
     *
     * @param pane The pane where the components should be rendered.
     */
    @Override
    public void render(Pane pane) {
        for (GameComponent component : this.children) {
            component.render(pane);
        }
    }
}
