package vooga.towerdefense.model.tiles;

import java.awt.Dimension;
import vooga.towerdefense.gameElements.GameElement;
import util.Location;
import util.Pixmap;
import vooga.towerdefense.util.Sprite;

/**
 * This class represents a Tile object.
 * 
 * @author Erick Gonzalez
 */
public abstract class Tile extends Sprite {
    
    private GameElement myElement;
    private boolean myIsWalkable;
    private boolean myIsBuildable;
    private int myId;
    private Pixmap myPixmap;
    
    /**
     * 
     * @param id a tile id
     * @param image an image
     * @param center the center of this tile
     * @param size the size of the tile
     */
    public Tile (int id, Pixmap image, Location center, Dimension size) {
        super(image, center, size);
        myId = id;
        myPixmap = image;
    }


    /**
     * 
     * @return true if you can build things on this tile.
     */
    public boolean isBuildable() {
        return myIsBuildable;
    }
    
    /**
     * 
     * @return the tile id
     */
    public int getTileId() {
        return myId;
    }
    
    /**
     * 
     * @return true if units can walk on this tile
     */
    public boolean isWalkable() {
        return myIsWalkable;
    }
    
    protected void setBuildable(boolean isBuildable) {
        myIsBuildable = isBuildable;
    }
    
    protected void setWalkable(boolean isWalkable) {
        myIsWalkable = isWalkable;
    }
    
    public boolean containsElement() {
        return myElement != null;
    }
    
    public GameElement getElement() {
        return myElement;
    }
    
    public void setTower(GameElement t) {
        myElement = t;
    }
    
    public void deleteTower() {
        myElement = null;
    }
    
    public Pixmap getPixmap () {
        return myPixmap;
    }
}
