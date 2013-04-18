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
public class Terrain extends MapComponent{
   
   private static final String BUNDLE_NAME = "TerrainIndex";
    
   protected Location myLocation;
    
   private int myWalkAbility;
  
  
   public Terrain(Location loc , int ID ) {
       super(BUNDLE_NAME);
       setType(ID);
       myLocation = loc;
   }
      
       
   
   
   public Terrain(int x , int y , int ID) {
       
       this(new Location(x,y) , ID );
   }
  

   public Terrain(int i) {
       this(0,0,i);
   }
   
   @Override
   public void setType(int id) {
       super.setType(id);
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
           myWalkAbility = Integer.parseInt(buffer[2]);
           myImage = ImageIO.read(new File(System.getProperty("user.dir") + IMAGE_PATH + myImageName));            
       }
   }


   public int getMyWalkAbility () {
    return myWalkAbility;
   }
   
   
   public Location getMyLocation() {
       return myLocation;
   }
   
   public int getMyX() {
       return (int)myLocation.getX();
   }
   
   public int getMyY() {
       return (int)myLocation.getY();
   }
   
   public void paint(Graphics pen) {
       pen.drawImage(myImage, (int)myLocation.getX(), (int)myLocation.getY(), null);
   }

   

   
   
   
   
}
