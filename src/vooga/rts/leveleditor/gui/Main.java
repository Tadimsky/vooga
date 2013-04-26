package vooga.rts.leveleditor.gui;

import vooga.rts.resourcemanager.ImageLoader;
import vooga.rts.resourcemanager.ResourceManager;

/**
 * This is the main class for level editor
 * 
 * @author Ziqiang Huang
 *
 */
public class Main {
    public static void main(String[] args) {
        ResourceManager.getInstance().registerResourceLoader(new ImageLoader());
        ResourceManager.getInstance().setResourceBase("/vooga/rts/leveleditor/resource/");
        
        new Canvas();
    }


}
