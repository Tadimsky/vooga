package vooga.rts.map;

import java.awt.Rectangle;
import vooga.rts.util.Location;
import vooga.rts.util.Location3D;


/**
 * The Node class represents the smallest thing that the game needs to know about.
 * This will be used for generating a path for units to follow and also be responsible
 * for painting all game entities in their correct location.
 * 
 * @author Jonathan Schmidt
 * @author Challen Herzberg-Brovold
 * 
 */
public class Node {
    public static int NODE_SIZE = 8;

    private int myX;
    private int myY;
    private int myHeight;
    private int myTier;
    private Location3D myCenter;
    private Rectangle myBounds;

    /**
     * Creates a Node at the specified index and in the specified tier.
     * 
     * @param x The X index
     * @param y The Y index
     * @param tier The level that the node is at
     */
    public Node (int x, int y, int tier) {
        myX = x;
        myY = y;
        myCenter = new Location3D(myX * NODE_SIZE + NODE_SIZE/2, myY * NODE_SIZE + NODE_SIZE/2, tier);
        myTier = tier;
        myBounds = new Rectangle(x * NODE_SIZE, y * NODE_SIZE, NODE_SIZE, NODE_SIZE);
    }

    /**
     * Creates a Node at a specified index.
     * The level is set to 0.
     * 
     * @param x The X index
     * @param y The Y index
     */
    public Node (int x, int y) {
        this(y, x, 0);
    }

    public Location3D getCenter() {
        return myCenter;
    }

    // public void addObstruction (IObstruction obstruct) {
    // myHeight = obstruct.getHeight();
    // }

    public double getTier () {
        return myTier;
    }

    // This return statement could potentially be cleaned up, but still will wait for patter to
    // clear up.
    public boolean connectsTo (Node other) {
        return getTier() == other.getTier() || other.getTier() < 0;
    }

    /**
     * 
     * @return the height of the node based on anything inside of it (ie. IObstructions)
     */
    public int getHeight () {
        return myHeight;
    }

    /**
     * Returns whether a location is contained within this node.
     * 
     * @param world The location to check
     * @return Whether it is in the node or not
     */
    public boolean isInside (Location3D world) {
        return myBounds.contains(world.to2D());
    }
    
    public int getX () {
        return myX;
    }
        
    public int getY () {
        return myY;
    }
}
