package vooga.rts.gamedesign.sprite.map;

import java.awt.Dimension;
import vooga.rts.gamedesign.sprite.gamesprites.GameSprite;
import vooga.rts.util.Location3D;
import vooga.rts.util.Pixmap;


/**
 * The background image for the map.
 * 
 * @author Ryan Fishel
 * @author Kevin Oh
 * @author Francesco Agosti
 * @author Wenshun Liu
 * 
 */
public class Tile extends GameSprite {
	/**
	 * Creates a new tile.
	 * @param image is the picture of the tile
	 * @param center is the location of the tile
	 * @param size is the dimension of the tile
	 */
    public Tile (Pixmap image, Location3D center, Dimension size) {
        super(image, center, size);
    }
}
