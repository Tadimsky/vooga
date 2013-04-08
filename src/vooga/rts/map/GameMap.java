package vooga.rts.map;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import vooga.rts.util.Location;
import vooga.rts.gamedesign.sprite.map.Tile;

public class GameMap {
    
    public static final int NODE_SIZE = 8;
    
    private Tile[][] myTiles;
    private MapNode[][] myMap;
    
    /**
     * The Width of the map in Grid Size
     */
    private int myWidth;
    
    /**
     * The height of the map in Grid Size
     */
    private int myHeight;
    
    // Eventually, this will ideally read in a map file of sort to create the map.
    // Currently just makes a square map of nodes from 
    
    
    public GameMap(Dimension mapSize, Dimension tileSize) {
       myWidth = (mapSize.width * tileSize.width) / NODE_SIZE;
       myHeight = (mapSize.height * tileSize.height) / NODE_SIZE;
       
       // Create map and add first layer
       myMap = new MapNode[myWidth][myHeight];
       myTiles = new Tile[mapSize.width][mapSize.height];
    }    
    
    /**
     * Generates a new 2D array for nodes. This will be used to generate
     * each layer that can be created in the map.
     * 
     * @param w Width of the map.
     * @param h Height of the map.
     * @return The new layer of nodes.
     */
    private MapNode[][] createLayer(int w, int h) {
        MapNode[][] res = new MapNode[w][h];
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                res[x][y]= new MapNode(x, y);
            }
        }
        return res;
    }
    
    /*
     * It might be easier and more efficient to store a unit's current node and 
     * update it as they move rather than having to calculate where they are every
     * time you need to move them.
     */
     /**
      * Determines which map node corresponds to the given location
      * @param location
      * @return
      */
     public MapNode getNode (Location location) {
         return myMap[(int)location.x/NODE_SIZE][(int)location.y/NODE_SIZE];
     }
     
     public List<MapNode> getNeighbors (MapNode center) {
         List<MapNode> neighbors = new ArrayList<MapNode>();
         for (int i = -1; i < 2; i += 2) {
             for (int j = -1; j < 2; j += 2) {
                 neighbors.add(myMap[center.getX() + i][center.getY()  + j]);
             }
         }
         return neighbors;
     }
}
