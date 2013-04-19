package vooga.rts;

import vooga.rts.controller.MainController;

// Ignore me again

public class Game {
    MainController myMainController;

    public static final int FPS = 60;
    
    public static double TIME_PER_FRAME () {
        double persecond = 1 / (double) FPS;
        return persecond;
    }

    public Game () {
        myMainController = new MainController();
    }

    public static void main (String[] args) {
        Game game = new Game();
    }
}
