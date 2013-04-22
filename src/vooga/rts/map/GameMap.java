package vooga.rts.map;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import vooga.rts.IGameLoop;
import vooga.rts.ai.Path;
import vooga.rts.ai.PathFinder;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.resourcemanager.ResourceManager;
import vooga.rts.util.Camera;
import vooga.rts.util.Location;
import vooga.rts.util.Location3D;


/**
 * The GameMap manages all aspects of the map on the screen.
 * This includes the underlying tiles, the terrain objects on
 * the map and the nodes used for pathfinding.
 * 
 * In addition to this, the map will be responsible for painting
 * everything that is in the world. This is to ensure that objects
 * are painted in the correct order.
 * 
 * @author Challen Herzberg-Brovold
 * @author Jonathan Schmidt
 * 
 */

public class GameMap implements IGameLoop {

    private int myNodeSize;
    private NodeMap myNodeMap;
    private TileMap myTiles;
    private TerrainManager myTerrain;

    /**
     * calculates how many nodes there are
     * 
     * @param mapSize This is the size of the map in pixels
     */
    public GameMap (int node, Dimension size) {
        NodeFactory factory = new NodeFactory();
        myTerrain = new TerrainManager();
        myNodeSize = node;
        myNodeMap = factory.makeMap(myNodeSize, size);
        Camera.instance().setMapSize(size);
        randomGenMap();
    }

    /**
     * @return the terrain
     */
    public TerrainManager getTerrain () {
        return myTerrain;
    }

    /**
     * @param terrain the terrain to set
     */
    public void setTerrain (TerrainManager terrain) {
        myTerrain = terrain;
    }

    public Node getNode (Location location) {
        int x = (int) location.x / myNodeSize;
        int y = (int) location.y / myNodeSize;
        return myNodeMap.get(x, y);
    }

    public Path getPath (PathFinder finder, Location start, Location finish) {
        return finder.calculatePath(getNode(start), getNode(finish), myNodeMap);
    }

    public NodeMap getMap () {
        return myNodeMap;
    }

    /**
     * Returns a list of Interactive Entities that are within a specified radius of
     * a central point. This can be used by the units to find targets in the area.
     * 
     * @param loc The Location to search from
     * @param radius The radius of the circle to search in
     * @return
     */
    public List<InteractiveEntity> getUnitsInArea (Location3D loc, double radius) {
        return null;
    }

    @Override
    public void update (double elapsedTime) {
        myTiles.update(elapsedTime);
    }

    @Override
    public void paint (Graphics2D pen) {
        myTiles.paint(pen);
    }

    private void randomGenMap () {

        int tilesX = 256;
        int tilesY = 256;

        int tileWidthX = 60;
        int tileWidthY = 42;
        myTiles = new TileMap(new Dimension(tileWidthX, tileWidthY), tilesX, tilesY);

        /*
         * BufferedImage banana =
         * ResourceManager.getInstance()
         * .<BufferedImage> getFile("images/tiles/iso-64x64-outside.png",
         * BufferedImage.class);
         * 
         * 
         * myTiles.addTileType(1, banana.getSubimage(0, 0, 64, 64));
         * myTiles.addTileType(2, banana.getSubimage(2 * 64, 0, 64, 64));
         */

        BufferedImage banana =
                ResourceManager
                        .getInstance()
                        .<BufferedImage> getFile("images/tiles/isometric_new_tiles_by_spasquini.png",
                                                 BufferedImage.class);

        myTiles.addTileType(1, banana.getSubimage(6 * tileWidthX, 0, tileWidthX, tileWidthY));
        myTiles.addTileType(2, banana.getSubimage(7 * tileWidthX, 0, tileWidthX, tileWidthY));

        for (int i = 0; i < tilesX; i++) {
            for (int j = 0; j < tilesY; j++) {
                if (Math.random() < 0.2) {
                    myTiles.createTile(2, i, j);
                }
                else {
                    myTiles.createTile(1, i, j);
                }
            }
        }
        System.out.println("Map Made");
        Camera.instance().setMapSize(new Dimension(tilesX * tileWidthX, tilesY * tileWidthY));
    }
}
