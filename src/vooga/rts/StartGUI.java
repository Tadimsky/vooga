package vooga.rts;

import java.io.FileNotFoundException;
import vooga.rts.resourcemanager.ImageLoader;
import vooga.rts.resourcemanager.ResourceManager;
import vooga.rts.resourcemanager.SoundLoader;
import vooga.rts.resourcemanager.exceptions.FileNotSupportedException;
import vooga.rts.state.MainState;


public class StartGUI 
{

    private final static String DEFAULT_RESOURCE_LOCATION = "/vooga/rts/resources/";

    public static void main (String[] args) {
        ResourceManager.getInstance().registerResourceLoader(new ImageLoader());
        ResourceManager.getInstance().registerResourceLoader(new SoundLoader());
        ResourceManager.getInstance().setResourceBase(DEFAULT_RESOURCE_LOCATION);

        try {
            ResourceManager.getInstance().queueFile("tree.jpg");
            ResourceManager.getInstance().queueFile("got1.jpg");
            ResourceManager.getInstance().queueFile("got2.jpg");
            ResourceManager.getInstance().queueFile("got3.jpg");
            ResourceManager.getInstance().queueFile("got4.jpg");
            ResourceManager.getInstance().queueFile("got5.jpg");
        }
        catch (FileNotSupportedException fe) {
        }
        catch (FileNotFoundException e) {
        }

        new MainState();

    }
}
