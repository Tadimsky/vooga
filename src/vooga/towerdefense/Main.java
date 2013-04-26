package vooga.towerdefense;

import vooga.towerdefense.controller.Controller;

public class Main {
    
    private Main() {
        // prevent instantiation of this class
    }
    
    public static void main(String[] args) {
        Controller controller = new Controller("English");
        controller.start();
    }
}
