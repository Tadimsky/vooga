package vooga.rts.leveleditor.components;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
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
            myImage = ImageIO.read(new File(System.getProperty("user.dir") + IMAGE_PATH + myImageName));            
        }
    }
    
    public int getMyX() {
        return (int) myLocation.getX();
    }
    
    public int getMyY() {
        return (int) myLocation.getY();
    }
}
