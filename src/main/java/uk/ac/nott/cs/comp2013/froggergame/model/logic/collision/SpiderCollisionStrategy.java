package uk.ac.nott.cs.comp2013.froggergame.model.logic.collision;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Spider;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.StateManager;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Score;

public class SpiderCollisionStrategy implements CollisionStrategy {
    @Override
    public void handleCollision(Animal animal, Object collider, StateManager stateManager, Score scoreDisplay) {
        Spider spider = (Spider) collider;
        stateManager.addPoints(20);
        Platform.runLater(() -> scoreDisplay.updateScore(stateManager.getPoints()));

        Pane parentPane = spider.getParentPane();
        if (parentPane != null) {
            Platform.runLater(() -> parentPane.getChildren().remove(spider));
        }
        if (spider.getParentLog() != null) {
            spider.getParentLog().clearSpider();
        }
    }
}
