package vooga.fighter.model.utils;

import java.awt.Dimension;
import java.util.List;

import util.Location;
import util.Pixmap;

/**
 * Data object for an image representing a game object. Includes size of the 
 * object, location of the object on a canvas, and the image itself.
 *  
 * @author James Wei
 *
 */
public class ImageDataObject {

    private Pixmap myImage;
    private Location myLocation;
    private Dimension mySize;
    private List<Integer> myImageEffects;

    
    public ImageDataObject(Pixmap image, Location location, Dimension size, List<Integer> imageEffects) {
        myImage = image;
        myLocation = location;
        mySize = size;
        myImageEffects = imageEffects;
    }
    
    public Pixmap getImage () {
        return myImage;
    }
    
    public Location getLocation () {
        return myLocation;
    }    
    
    public Dimension getSize () {
        return mySize;
    }        
    
    public List<Integer> getImageEffect () {
        return myImageEffects;
    }        
}
