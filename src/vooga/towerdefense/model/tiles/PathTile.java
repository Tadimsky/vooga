package vooga.towerdefense.model.tiles;

import java.awt.Dimension;
import util.Location;
import util.Pixmap;

/**
 * A path tile.
 * 
 * @author Erick Gonzalez
 */
public class PathTile extends Tile {

    /**
     * 
     * @param id a tile id
     * @param image an image
     * @param center the center of the tile
     * @param size the size of the tile
     */
    public PathTile (int id, Pixmap image, Location center, Dimension size) {
        super(id, image, center, size);
        setBuildable(false);
        setWalkable(true);
    }
}
