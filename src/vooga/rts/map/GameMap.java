package vooga.rts.map;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import vooga.rts.util.Vector;
import vooga.rts.IGameLoop;
import vooga.rts.ai.Path;
import vooga.rts.ai.PathFinder;
import vooga.rts.gamedesign.sprite.gamesprites.GameEntity;
import vooga.rts.gamedesign.sprite.gamesprites.GameSprite;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.resourcemanager.ResourceManager;
import vooga.rts.util.Camera; 
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

public class GameMap implements IGameLoop, Observer {

    private int myNodeSize;
    private NodeMap myNodeMap;
    private TileMap myTiles;
    private TerrainManager myTerrain;
    private Map<GameSprite, Node> mySprites;
    private Map<GameEntity, Path> myMoving;
    private int myWidth;
    private int myHeight;
    /**
     * calculates how many nodes there are
     * 
     * @param mapSize This is the size of the map in pixels
     */
    public GameMap (int node, Dimension size) {
        NodeFactory factory = new NodeFactory();
        mySprites = new HashMap<GameSprite, Node>();
        myMoving = new HashMap<GameEntity, Path>();
        myTerrain = new TerrainManager();
        myNodeSize = node;
        Camera.instance().setMapSize(size);
        randomGenMap();
        myNodeMap = factory.makeMap(node, new Dimension(myWidth, myHeight));
    }

    public Node getNode (Location3D location) {
        int x = (int) location.getX() / myNodeSize;
        int y = (int) location.getY() / myNodeSize;
        System.out.println("node x and y: " + x + " "+ y);
        return myNodeMap.get(x, y);
    }

    public Path getPath (PathFinder finder, Location3D start, Location3D finish) {
        System.out.println(getNode(start).equals(null));
        return finder.calculatePath(getNode(start), getNode(finish), myNodeMap);
    }

    @Override
    public void update (double elapsedTime) {
        myTiles.update(elapsedTime); //What's this used for? (CHB)
        for (GameEntity g: myMoving.keySet()) {
            Vector v = g.getWorldLocation().difference(mySprites.get(g).getCenter().to2D());
            if (v.getMagnitude() < Location3D.APPROX_EQUAL) {
                if (myMoving.get(g).size() == 0) {
                    myMoving.remove(g);
                    continue;
                }
                myMoving.get(g).setNext();
            }
            g.translate(g.getVelocity()); // Possibly no longer need GameEntities to necessarily implement IGameLoop. Just needs to paint
        }
    }

    @Override
    public void update (Observable arg0, Object arg1) {
        if (arg1 instanceof Location3D) { // This will get changed eventually hopefully, will only be called when something is moving
            GameEntity entity = (GameEntity) arg0;
            myMoving.put(entity, getPath(entity.getFinder(), entity.getWorldLocation(), (Location3D) arg1)); 
        }
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
    public void paint (Graphics2D pen) {
        myTiles.paint(pen);
    }

    private void randomGenMap () {

        int tilesX = 256;
        int tilesY = 256;

        int tileWidthX = 60;
        int tileWidthY = 42;
        myWidth = tilesX*tileWidthX;
        myHeight = tilesY*tileWidthY;
        
        myTiles = new TileMap(new Dimension(tileWidthX, tileWidthY), tilesX, tilesY);

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

    public void add (GameSprite sprite) {
        mySprites.put(sprite, getNode(sprite.getWorldLocation()));
    }

    /**
     * @param terrain the terrain to set
     */
    public void setTerrain (TerrainManager terrain) {
        myTerrain = terrain;
    }

    /**
     * @return the terrain
     */
    public TerrainManager getTerrain () {
        return myTerrain;
    }

    public NodeMap getMap () {
        return myNodeMap;
    }
}
