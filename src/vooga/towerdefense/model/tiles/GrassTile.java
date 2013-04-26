package vooga.towerdefense.model.tiles;

import java.awt.Dimension;
import util.Location;
import util.Pixmap;

/**
 * A grass tile.
 * 
 * @author Erick Gonzalez
 */
public class GrassTile extends Tile {

    /**
     * 
     * @param id tile id
     * @param image an image 
     * @param center the center of the tile
     * @param size the size of the tile
     */
    public GrassTile (int id, Pixmap image, Location center, Dimension size) {
        super(id, image, center, size);
        setBuildable(true);
        setWalkable(false);
    }
}
