package uk.ac.nott.cs.comp2013.froggergame.model.data;

public class GameObjectData {
    public static final Object[][] LOG_DATA = {
            {"log2.png", 200, 200, 200, 0, 176, 1.0},
            {"log2.png", 200, 200, 200, 220, 176, 1.0},
            {"log2.png", 200, 200, 200, 440, 176, 1.0},
            {"logs.png", 300, 300, 300, 0, 276, 1.5},
            {"logs.png", 300, 300, 300, 400, 276, 1.5},
            {"log3.png", 150, 150, 150, 50, 329, 0.75},
            {"log3.png", 150, 150, 150, 270, 329, 0.75},
            {"log3.png", 150, 150, 150, 490, 329, 0.75}
    };

    public static final Object[][] TURTLE_DATA = {
            {500, 376, -1, 130, 130, false},
            {300, 376, -1, 130, 130, false},
            {700, 376, -1, 130, 130, false},
            {600, 217, -1, 130, 130, true},
            {400, 217, -1, 130, 130, true},
            {200, 217, -1, 130, 130, true}
    };

    public static final Object[][] OBSTACLE_DATA = {
            {"truck1Right.png", 120, 120, 0, 649, 1},
            {"truck1Right.png", 120, 120, 300, 649, 1},
            {"truck1Right.png", 120, 120, 600, 649, 1},
            {"truck1Right.png", 120, 120, 720, 649, 1},

            {"car1Left.png", 50, 50, 100, 597, -1},
            {"car1Left.png", 50, 50, 250, 597, -1},
            {"car1Left.png", 50, 50, 400, 597, -1},
            {"car1Left.png", 50, 50, 550, 597, -1},

            {"truck2Right.png", 200, 200, 0, 540, 1},
            {"truck2Right.png", 200, 200, 500, 540, 1},

            {"redcar.png", 50, 50, 100, 490, -1},
            {"redcar.png", 50, 50, 400, 490, -1},
            {"redcar.png", 50, 50, 600, 490, -1},

            {"car1Left.png", 50, 50, 100, 700, -1},
            {"car1Left.png", 50, 50, 400, 700, -1},
            {"car1Left.png", 50, 50, 600, 700, -1}
    };

    public static final int[][] END_POINT_DATA = {
            {4, 96},
            {137, 96},
            {270, 96},
            {403, 96},
            {536, 96}
    };
}
