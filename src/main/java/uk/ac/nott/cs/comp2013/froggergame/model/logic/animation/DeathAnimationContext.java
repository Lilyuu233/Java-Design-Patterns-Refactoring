package uk.ac.nott.cs.comp2013.froggergame.model.logic.animation;

import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import uk.ac.nott.cs.comp2013.froggergame.view.resource.ImageManager;

/**
 * Context record for managing death animation details.
 *
 * @param animal      The animal involved in the animation.
 * @param imageManager The image manager for loading and handling animation frames.
 * @param type         The type of death animation (e.g., "car death" or "water death").
 * @param frameLimit   The number of frames in the animation sequence.
 */
public record DeathAnimationContext(Animal animal, ImageManager imageManager, String type, int frameLimit) {
}
