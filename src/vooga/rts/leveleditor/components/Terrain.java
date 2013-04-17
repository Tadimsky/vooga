package vooga.rts.leveleditor.components;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;
import vooga.rts.util.Location;

/**
 * all class of terrains, which is used as a part of map
 * 
 * @author Richard Yang
 *
 */
public class Terrain {
   
   private static final String BUNDLE_RELATIVE_PATH = "vooga.rts.leveleditor.resource.";
   private static final String IMAGE_RELATIVE_PATH = "./src/vooga/rts/leveleditor/resources/";
   
   private int myID; 
   private Location myLocation;
    
   private String myName;
   private int myWalkAbility;
   
   private BufferedImage myImage;
   
   private ResourceBundle myResources = ResourceBundle.getBundle(BUNDLE_RELATIVE_PATH + "ImageIndex");
   
   public Terrain(Location loc , int ID , int walkAbility) {
       myLocation = loc;
       myID = ID;
       myName = myResources.getString(ID+"");
       myWalkAbility = walkAbility;
       try {
        myImage = ImageIO.read(new File(System.getProperty("user.dir") + IMAGE_RELATIVE_PATH+ myName+".jpg"));
    }
    catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
       
       
   }
   
   public Terrain(int x , int y , int ID ,int walkAbility) {
       
       this(new Location(x,y) , ID , walkAbility);
   }
  

   public String getMyName () {
    return myName;
   }

   public int getMyWalkAbility () {
    return myWalkAbility;
   }
   
   public int getMyID() {
       return myID;
   }
   
   public Location getMyLocation() {
       return myLocation;
   }
   
   public void paint(Graphics pen) {
       pen.drawImage(myImage, (int)myLocation.getX(), (int)myLocation.getY(), null);
   }
   
   
   
   
   
}
