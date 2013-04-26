package vooga.towerdefense.model.tiles.factories;

import java.awt.Dimension;
import vooga.towerdefense.model.tiles.Tile;
import util.Location;

/**
 * An abstract TileFactory.
 * 
 * @author Erick Gonzalez
 */
public abstract class TileFactory {
    
    /**
     * The size of a tile
     */
    public static final Dimension TILE_DIMENSIONS = new Dimension(50, 50);
    
    /**
     * 
     * @param id a tile id.
     * @param center the center of this tile on the map
     * @return the corresponding tile
     */
    public abstract Tile createTile(int id, Location center);

}
