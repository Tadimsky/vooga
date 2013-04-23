package vooga.rts.leveleditor.components;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import vooga.rts.leveleditor.gui.MapPanel;
import vooga.rts.resourcemanager.ResourceManager;
import vooga.rts.util.Location;
/**
 * This class represents the available resource types the designer can choose from
 * 
 * @author Ziqiang Huang
 * @author Yang Yang
 *
 */

public class Resource extends MapComponent{
    
    private static final String BUNDLE_NAME = "ResourceIndex";
    
    private Location myLocation;
    //further extension required
    private int myAmount;
    private String myType;
    private int myImageWidth;
    private int myImageHeight;
    
    /**
     * Constructor for this class
     * 
     * @param id
     * @param name
     * @param image
     */
    public Resource(Location loc, int id) {
        super(BUNDLE_NAME);
        myLocation = loc;
    }
    
    public Resource(int x , int y, int id ) {
        this( new Location(x,y), id);
    }
    
    public Resource(int id) {
        this(0,0,id);
    }
    
    public Resource(int x, int y , int ID, String name, String imageName) {
        super(ID,name,imageName);
        myLocation = new Location(x,y);
    }
    
    public Resource(int ID, String name, BufferedImage image) {
        super(ID,name,"");
        myImage = image;
    }

    public void setType(String type) {
        myType = type;
     }

    public void setAmount(int amount) {
        myAmount = amount;
    }
    
    public int getMyX() {
        return (int) myLocation.getX();
    }
    
    public int getMyY() {
        return (int) myLocation.getY();
    }
    
    public int getImageWidth() {
        return myImageWidth;
    }
    
    public int getImageHeight() {
        return myImageHeight;
    }

    public void zoomIn() {
        myLocation = new Location(myLocation.getX()*MapPanel.ZOOM_RATE,myLocation.getY()*MapPanel.ZOOM_RATE);
        myImageWidth = (int)(myImageWidth*MapPanel.ZOOM_RATE);
        myImageHeight = (int)(myImageHeight*MapPanel.ZOOM_RATE);
    }
    
    public void zoomOut() {
        myLocation = new Location(myLocation.getX()/MapPanel.ZOOM_RATE,myLocation.getY()/MapPanel.ZOOM_RATE);
        myImageWidth = (int)(myImageWidth/MapPanel.ZOOM_RATE);
        myImageHeight = (int)(myImageHeight/MapPanel.ZOOM_RATE);     
    }
    
    public void paint (Graphics pen) {
        pen.drawImage(myImage, getMyX(), getMyY(), myImageWidth,myImageHeight,null);
    }
}
