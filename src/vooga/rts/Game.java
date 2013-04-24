package vooga.rts;

import vooga.rts.state.MainState;


// Ignore me again

public class Game {
    MainState myState;

    public static final int FPS = 60;

    public static double TIME_PER_FRAME () {
        double persecond = 1 / (double) FPS;
        return persecond;
    }

    public Game () {
        myState = new MainState();
    }

    public static void main (String[] args) {
        new Game();
    }
}
