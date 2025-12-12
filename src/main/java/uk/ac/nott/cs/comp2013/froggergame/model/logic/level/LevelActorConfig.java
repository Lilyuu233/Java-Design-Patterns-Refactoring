package uk.ac.nott.cs.comp2013.froggergame.model.logic.level;

/**
 * Represents the configuration for level-specific actors, such as crocodiles and spiders.
 */
public record LevelActorConfig(
        String crocodileImagePath, // Path to the crocodile image resource
        double crocodileX,         // Initial x-coordinate for the crocodile
        double crocodileY,         // Initial y-coordinate for the crocodile
        double crocodileSpeed,     // Speed of the crocodile
        String spiderImagePath     // Path to the spider image resource
){
}