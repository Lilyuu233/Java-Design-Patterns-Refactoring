package uk.ac.nott.cs.comp2013.froggergame.view.managers;

import javafx.scene.layout.Pane;

/**
 * Represents a component in the game that can be rendered onto a given pane.
 * This interface is intended to standardize the rendering process for various game components.
 */
public interface GameComponent {

    /**
     * Renders the component onto the specified pane.
     *
     * @param pane The pane where the component will be displayed.
     */
    void render(Pane pane);
}
