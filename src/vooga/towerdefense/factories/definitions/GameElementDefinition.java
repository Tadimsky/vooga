package vooga.towerdefense.factories.definitions;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import vooga.towerdefense.factories.actionfactories.ActionFactory;
import vooga.towerdefense.factories.attributefactories.AttributeManagerFactory;
import vooga.towerdefense.util.Location;
import vooga.towerdefense.util.Pixmap;


/**
 * Class that defines a game element as created by the designer
 * Used as a replacement for an xml file
 * Functionally, it bundles the states the define an object
 * 
 * @author Matthew Roy
 * @author Xu Rui
 * 
 */
public abstract class GameElementDefinition {
    private String myName;
    private Pixmap myImage;
    private Location myCenter;
    private Dimension mySize;

    public GameElementDefinition(){
    	
    }
    
    public GameElementDefinition (String name, Pixmap image, Location center, Dimension size) {
        myName = name;
    	myImage = image;
    	mySize = size;
    	myCenter = center;
    }
    
    
    public String getName() {
        return myName;
    }
    
    public Pixmap getImage () {
        return myImage;
    }

    public Location getCenter () {
        return myCenter;
    }

    public Dimension getSize () {
        return mySize;
    }
    
    public abstract AttributeManagerFactory getAttributeManagerFactory();
    
    
    public List<ActionFactory> getActions() {
        return new ArrayList<ActionFactory>();
    }

}
