package vooga.rts.map;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import vooga.rts.gamedesign.sprite.gamesprites.GameSprite;
import vooga.rts.util.Location3D;


/**
 * This class stores all the nodes that will be used for pathfinding.
 * 
 * @author Challen Herzberg-Brovold
 * 
 */
public class NodeMap implements Observer {

    private int myWidth;
    private int myHeight;
    private Node[][] myMap;

    private Map<GameSprite, Node> myLookupMap;

    public NodeMap (int width, int height) {
        myMap = new Node[width][height];
        System.out.println("height and width: " + width + " " + height);
        myWidth = width;
        myHeight = height;
        myLookupMap = new HashMap<GameSprite, Node>();
    }

    /**
     * Returns all the neighboring nodes of a specified node.
     * 
     * @param current the node for which we want the neighbors
     * @return a list of neighbors of the node
     */
    public List<Node> getNeighbors (Node current) {
        List<Node> neighbors = new ArrayList<Node>();
        int x = current.getX();
        int y = current.getY();
        for (int i = -1; i < 2; i += 2) {
            neighbors.add(get(x + i, y));
            neighbors.add(get(x, y + i));
        }
        return neighbors;
    }

    /**
     * Returns the node at the specified coordinates
     * 
     * @param x-coordinate
     * @param y-coordinate
     * @return node at the coordinates
     */
    public Node get (int x, int y) {
//        System.out.println("height: " + myHeight + "width: " + myWidth);
//        System.out.println("x: " + x + "y: " + y);
        return myMap[x][y];
    }

    /**
     * 
     * @return width of the map in nodes
     */
    public int getWidth () {
        return myWidth;
    }

    /**
     * 
     * @return height of the map in nodes.
     */
    public int getHeight () {
        return myHeight;
    }

    public void put (Node node, int x, int y) {
        myMap[x][y] = node;
    }

    public void paint (Graphics2D pen) {
        for (int x = 0; x < myWidth; x++) {
            for (int y = 0; y < myHeight; y++) {
                pen.drawRect(x * 10, y * 10, 10, 10);
            }
        }
    }

    private Node findContainingNode (Location3D world) {
        
        return null;
    }

    @Override
    public void update (Observable arg0, Object arg1) {
        if (!(arg0 instanceof GameSprite)) {
            return;
        }
        GameSprite item = (GameSprite) arg0;

        // if it's updating with its new location
        if (arg1 instanceof Location3D) {
            Node cur = myLookupMap.get(item);
            if (cur != null) {

            }
        }
    }
}
