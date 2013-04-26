package vooga.rts.leveleditor.components;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import vooga.rts.gamedesign.sprite.map.Tile;
import vooga.rts.resourcemanager.ResourceManager;
import vooga.rts.util.Location3D;
import vooga.rts.util.Pixmap;


/**
 * the class of a single editable map node. This node has a linked list
 * to store the information of tiles, terrains and resources.
 * 
 * @author Richard Yang
 *
 */

public class EditableTile extends Tile {
    
    /**
     * the default image path for each tile
     */
    private static final String DEFAULT_IMAGE_PATH = "./vooga/rts/leveleditor/resource/default.gif";
    
    private int myID;
    private String myName;
    private  String myImageName;
    
    /**
     * the default image for each tile
     */
    private static Pixmap DEFAULT_IMAGE = new Pixmap(
                                  ResourceManager.getInstance().<BufferedImage>getFile
                                  (DEFAULT_IMAGE_PATH, BufferedImage.class));
    
    private boolean myOccupied;
    
    /**
     * constructor 1
     * @param image image of the tile 
     * @param center center position of the tile 
     * @param size size pf the tile
     * @param id id of the tile
     * @param name of the tile
     * @param imageName of the tile
     * @param isOccupied of the tile
     */
    public EditableTile(Pixmap image, Location3D center, Dimension size, 
                        int id , String name, String imageName, boolean isOccupied) {
        super(image, center, size);
        myID = id;
        myName = name;
        myImageName = imageName;
        myOccupied = isOccupied;
    }
    
    
    /**
     * constructor 2
     * @param image image of the tile
     * @param xCount  x number 
     * @param yCount y number 
     * @param size size of the tile
     * @param id id of the tile
     * @param name of the tile
     * @param imageName of the tile
     * @param isOccupied of the tile
     */
    public EditableTile(Pixmap image, int xCount, int yCount, Dimension size, int id, 
                        String name, String imageName , boolean isOccupied) {
        this(image, new Location3D(xCount * size.getWidth()+ size.getWidth() / 2,
                                   yCount * size.getHeight()+ size.getHeight() / 2,0), size, id, name, imageName, isOccupied);
    }
    
    /**
     * constructor 3
     * @param node a certain tile 
     */
    public EditableTile(EditableTile node) {
        this(node.getImage(),node.getWorldLocation(),
                           new Dimension((int)node.getWidth(), (int)node.getHeight()),
                                              node.getMyID(), node.getMyName(), node.getMyImageName(), node.getOccupied());
    }
    
    /**
     * constructor 4
     * @param xCount x number 
     * @param yCount y number 
     * @param size size pf the tile
     */
    public EditableTile(int xCount, int yCount, Dimension size) {
        
        this(DEFAULT_IMAGE, xCount, yCount, size, 0, "", "", false);
    }
    
    /**
     * constructor 5
     * @param xCount x number 
     * @param yCount y number 
     * @param width width of the tile 
     * @param height height of the tile
     */
    public EditableTile(int xCount, int yCount, int width, int height) {
        this(xCount, yCount, new Dimension(width, height));
    }
    
    
    /**
     * set the tile to be occupoed 
     * @param b the status 
     */
    public void setOccupied(boolean b) {
        myOccupied = b;
    }
   
    /**
     * get whether the tile is occupied 
     * @return boolean
     */
    public boolean getOccupied() {
        return myOccupied;
    }
        
    /**
     * return the id of tile
     * @return int
     */
    public int getMyID() {
        return myID;
    }
    
    /**
     * get the image name of tile
     * @return String
     */
    public String getMyImageName() {
        return myImageName;
    }
    
    /**
     * get the name of that image
     * @return String
     */
    public String getMyName() {
        return myName;
    }
    
    /**
     * reset the tile
     */
    public void reset() {
        myOccupied = false;
        myID = 0;
        myName = "";
        myImageName = "";
    }

    /**
     * set the type of the tile
     * @param type type string
     */
    public void setType(String type) {
        myName = type;        
    }
   
}
