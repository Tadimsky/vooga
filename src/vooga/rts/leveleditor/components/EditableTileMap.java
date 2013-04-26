package vooga.rts.leveleditor.components;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import vooga.rts.map.TileMap;
import vooga.rts.util.Location;
import vooga.rts.util.Location3D;
import vooga.rts.util.Pixmap;

/**
 * this class contains a matrix of EditableTile, which is a part of the whole map
 * this class extends TileMap
 * @author Richard Yang
 *
 */

public class EditableTileMap extends TileMap {

    /**
     * constructor 1
     * @param tileSize of the tile
     * @param width of the tile
     * @param height of the tile
     */
    public EditableTileMap (Dimension tileSize, int width, int height) {
        super(tileSize, width, height);
        initialize();
    }

    /**
     * initialize the whole tile map
     */
    public void initialize() {
        int x = this.getMyHeight();
        int y = this.getMyWidth();
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                setTile(i, j, new EditableTile(i, j, getMyTileSize()));
            }
        }
    }    
    /**
     * add tile based on some parameters
     * @param i ith 
     * @param j jth 
     * @param id id of the tile
     * @param name id of the tile
     * @param imageName id of the tile
     * @param image id of the tile
     */
    public void addTile(int i, int j, int id, String name , String imageName, Pixmap image) {
        addTileType(id, (BufferedImage)image.getMyImage());
        this.createTile(id, i, j, id, name, imageName);
    }
    
    /**
     * add tiles based on different parameters
     * @param loc location of the tile
     * @param id id of the tile 
     * @param name of the tile 
     * @param imageName of the tile 
     * @param image of the tile 
     */
    public void addTile(Location3D loc, int id, String name, String imageName, Pixmap image) {
        int i = (int)(loc.getX() / this.getMyTileSize().getWidth() * 2);
        int j = (int)(loc.getY() / this.getMyTileSize().getHeight() * 2);
        addTile(i, j, id, name, imageName, image);
    }
    
    /**
     * create a tile at a certain position
     * @param tiletype of the tile 
     * @param x x position
     * @param y y position 
     * @param id of the tile 
     * @param name of the tile 
     * @param imageName of the tile 
     */
    public void createTile (int tiletype, int x, int y, int id, String name , String imageName) {
        if (x < 0 || y < 0 || x >= this.getMyWidth() || y >= getMyHeight()) {
            return;
        }
        BufferedImage pic = this.getMyTileTypes().get(tiletype);
        Pixmap image = new Pixmap(pic);

        Location3D position =
                new Location3D(x * getMyTileSize().width / 2 ,
                               y * getMyTileSize().height / 2, 0);

        EditableTile newTile = new EditableTile(image, position, getMyTileSize(), id, name, 
                                                imageName, false);
        setTile(x, y, newTile);
    }
    
    
    
    /**
     * remove a tile based on its i and j
     * @param i ith 
     * @param j jth
     * @throws IOException exception has to throw
     */
    public void removeTile(int i, int j) throws IOException {
        this.setTile(i, j, new EditableTile(i, j, getMyTileSize()));
    }
    
    /**
     * remove a tile based on its location
     * @param center center position of the tile
     * @throws IOException exception has to throw
     */
    public void removeTile(Location3D center) throws IOException {
        int i = (int)(center.getY() / getMyTileSize().getHeight());
        int j = (int)(center.getX() / getMyTileSize().getWidth());
        removeTile(i, j);
    }
    
    /**
     * remove all tiles in this map
     */
    public void removeAllTiles() {
        initialize();
    }
    
    @Override
    public EditableTile getTile(int i , int j) {
        return (EditableTile) super.getTile(i, j);    
    }
    
    /**
     * get my x count
     * @param loc location
     * @return int 
     */
    public int getXCount (Location loc) {
        return (int)(loc.getY() / getMyTileSize().getHeight());
    }
    
    /**
     * get my y count
     * @param loc location
     * @return int 
     */
    public int getYCount (Location loc) {
        return (int)(loc.getX() / getMyTileSize().getWidth());
    }
    

    
}
