package vooga.rts.gamedesign.sprite.map;

import java.awt.Dimension;

import vooga.rts.gamedesign.sprite.gamesprites.GameSprite;
import vooga.rts.util.Location3D;
import vooga.rts.util.Pixmap;

/**
 * An object that appears on the map such as a tree or rock.  This would also
 * account for raised or lowered ground (for example a mountain or lake).
 * 
 * @author Kevin Oh
 * @author Francesco Agosti
 * @author Wenshun Liu 
 *
 */
public abstract class Terrain extends GameSprite {

    public int myHeight;
    public int myLevel;
    
    /**
     * Creates a new terrain.
     * @param image is the picture of the terrain
     * @param center is the location of the terrain
     * @param size is the dimensions of the terrain
     */
    public Terrain(Pixmap image, Location3D center, Dimension size) {
        super(image, center, size);
    }
    
    /**
     * Gets the level of the terrain which is the node that the terrain is in.
     * @return the level of the terrain
     */
    public int getLevel () {
        return myLevel;
    }
}
