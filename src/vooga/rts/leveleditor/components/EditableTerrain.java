package vooga.rts.leveleditor.components;


import java.awt.Image;
import vooga.rts.gamedesign.sprite.map.Terrain;
import vooga.rts.util.Location3D;
import vooga.rts.util.Pixmap;


/**
 * all class of terrains, which is used as a part of this map
 * these terrains contains a walkability which is used for path finding
 * this class extend Terrain class in game design part
 * 
 * @author Richard Yang
 * 
 */

public class EditableTerrain extends Terrain {

    private int myID;
    
    private String myName;
    private String myImageName;
    
    private int myWalkAbility; 
    
    /**
     * constructor for EditableTerrain
     * @param image the image for this terrain
     * @param center the center location of this terrain
     * @param id the id of this tile
     * @param name the name of this tile
     * @param imageName the image name of this terrain
     * @param walkAbility the walkability of this terrain
     */
    public EditableTerrain (Pixmap image, Location3D center , int id , String name , 
                                               String imageName, int walkAbility) {
        super(image, center, image.getMyDimension());
        myID = id;
        myName = name;
        myImageName = imageName;
        myWalkAbility = walkAbility;
    }
   /**
    * constructor for editable terrain
    * @param image the image of this terrain
    * @param x x position of this terrain
    * @param y y position of this terrain
    * @param z z position of this terrain
    * @param id id of this terrain
    * @param name name of this terrain
    * @param imageName imagename of this terrain 
    * @param walkAbility walkability of this terrain
    */
    public EditableTerrain (Pixmap image, int x, int y , int z, int id,
                            String name , String imageName , int walkAbility) {
        this(image , new Location3D(x, y, z) , id , name , imageName , walkAbility);
    }
    /**
     * constructor for editableterrain
     * @param image the image of this terrain
     * @param x the x position of this terrain
     * @param y the y position of this terrain
     * @param layerCount number of layers
     * @param layerHeight height of a single layer
     * @param id id of this terrain
     * @param name name 
     * @param imageName image name
     * @param walkAbility walkability
     */
    public EditableTerrain (Pixmap image, int x, int y, int layerCount, int layerHeight, 
                                   int id , String name , String imageName, int walkAbility) {
        this(image, x, y, layerCount * layerHeight, id, name, imageName, walkAbility);
    }
    /**
     * return the id of this 
     * @return myID id of this terrain
     */
    public int getMyID () {
        return myID;
    }
    /**
     * get the name 
     * @return name
     */
    public String getMyName () {
        return myName;
    }
    /**
     * return the imagename of this terrain
     * @return image name
     */
    public String getMyImageName () {
        return myImageName;
    }
    
    /**
     * return walkability 
     * @return walkability
     */
    public int getMyWalkAbility () {
        return myWalkAbility;
    }
    
    /**
     * return image
     * @return image
     */
    public Image getMyImage() {
        return super.getImage().getMyImage();
    }

    /**
     * set the walkability
     * @param walkability walkability of this terrain
     */
    public void setWalkability(int walkability) {
        myWalkAbility = walkability;
        
    }
    /**
     * set the type of this terrain 
     * @param type type of this terrain
     */
    public void setType(String type) {
        myName = type;
        
    } 
 
}
