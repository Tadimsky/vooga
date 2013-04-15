package vooga.rts;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import vooga.rts.controller.MainController;
import vooga.rts.resourcemanager.ResourceManager;


public class StartGUI {
    public static void main (String[] args) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, ParserConfigurationException, SAXException, IOException {
        ResourceManager.instance().queueFile("tree.jpg");
        ResourceManager.instance().queueFile("got1.jpg");
        ResourceManager.instance().queueFile("got2.jpg");
        ResourceManager.instance().queueFile("got3.jpg");
        ResourceManager.instance().queueFile("got4.jpg");
        ResourceManager.instance().queueFile("got5.jpg");
        
        new MainController();
    }
}
