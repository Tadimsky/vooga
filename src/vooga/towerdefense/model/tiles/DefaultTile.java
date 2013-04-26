package vooga.towerdefense.model.tiles;

import java.awt.Dimension;

import util.Location;
import util.Pixmap;

/**
 * A blank tile.
 * 
 * @author Jimmy Longley
 */
public class DefaultTile extends Tile {

    /**
     * 
     * @param id a tile id
     * @param image an image
     * @param center the center of the tile
     * @param size the size of the tile
     */
    public DefaultTile (int id, Pixmap image, Location center, Dimension size) {
        super(id, image, center, size);
        setBuildable(true);
        setWalkable(true);
    }
}
