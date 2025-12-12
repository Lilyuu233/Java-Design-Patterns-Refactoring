package uk.ac.nott.cs.comp2013.froggergame.model.entities;

public class Turtle extends AbstractTurtle {
    public Turtle(int xpos, int ypos, int speed, int width, int height) {
        super(xpos, ypos, speed, width, height, new String[]{
                "TurtleAnimation1.png",
                "TurtleAnimation2.png",
                "TurtleAnimation3.png"
        });
    }

    @Override
    protected void updateStateBasedOnFrame(int frame) {
    }
}
