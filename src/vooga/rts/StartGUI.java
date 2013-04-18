package vooga.rts;

import vooga.rts.controller.MainController;
import vooga.rts.resourcemanager.ResourceManager;
import vooga.rts.resourcemanager.exceptions.FileNotSupportedException;


public class StartGUI {

    public static void main (String[] args) {
        try {
            ResourceManager.getInstance().queueFile("tree.jpg");
            ResourceManager.getInstance().queueFile("got1.jpg");
            ResourceManager.getInstance().queueFile("got2.jpg");
            ResourceManager.getInstance().queueFile("got3.jpg");
            ResourceManager.getInstance().queueFile("got4.jpg");
            ResourceManager.getInstance().queueFile("got5.jpg");
        }
        catch (FileNotSupportedException e) {
            e.printStackTrace();
        }

        new MainController();
    }
}
