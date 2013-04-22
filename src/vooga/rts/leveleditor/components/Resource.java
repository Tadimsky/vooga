package vooga.rts.leveleditor.components;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
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
        setType(id);
        myLocation = loc;}
    
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
    
    public void setType(int id) {
        super.setID(id);
        try {
         refreshImage();
     }
     catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
     }
    }
    
    public void refreshImage() throws IOException {
        if(myResource.containsKey(myID+"")) {
            String content = myResource.getString(myID+"");
            String[] buffer = content.split("&");
            myName = buffer[0];
            myImageName = buffer[1];
            myImage = ResourceManager.getInstance().<BufferedImage>getFile(myImageName, BufferedImage.class); 
            myImageWidth = myImage.getWidth();
            myImageHeight = myImage.getHeight();
        }
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
