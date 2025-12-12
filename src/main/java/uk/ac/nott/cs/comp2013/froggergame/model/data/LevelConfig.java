package uk.ac.nott.cs.comp2013.froggergame.model.data;

/**
 * Represents the configuration for a game level, including the total number of endpoints,
 * starting position, vertical position, gaps between endpoints, and a speed factor.
 */
public record LevelConfig(int totalEnds, int startX, int y, int gap, double speedFactor) {
    // Default values for level configuration
    private static final int DEFAULT_TOTAL_ENDS = 5;
    private static final int DEFAULT_START_X = 4;
    private static final int DEFAULT_Y = 96;
    private static final int DEFAULT_GAP = 133;

    /**
     * Creates a default level configuration with a specified speed factor.
     *
     * @param speedFactor The speed factor for the level
     * @return A default LevelConfig object
     */
    public static LevelConfig createDefault(double speedFactor) {
        return new LevelConfig(DEFAULT_TOTAL_ENDS, DEFAULT_START_X, DEFAULT_Y, DEFAULT_GAP, speedFactor);
    }
}
