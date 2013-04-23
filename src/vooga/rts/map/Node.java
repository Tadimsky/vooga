package vooga.rts.map;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import vooga.rts.gamedesign.sprite.gamesprites.GameSprite;
import vooga.rts.util.Camera;
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
    public static int NODE_SIZE = 32;

    private int myHeight;
    private int myTier;
    private int myX;
    private int myY;
    private Rectangle myBounds;

    private List<GameSprite> myContents;

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
        myTier = tier;
        myBounds = new Rectangle(myX * NODE_SIZE, myY * NODE_SIZE, NODE_SIZE, NODE_SIZE);
        myContents = new ArrayList<GameSprite>();
    }

    /**
     * Creates a Node at a specified index.
     * The level is set to 0.
     * 
     * @param x The X index
     * @param y The Y index
     */
    public Node (int x, int y) {
        this(x, y, 0);
    }

    public int getX () {
        return myX;
    }

    public int getY () {
        return myY;
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
    public boolean contains (Location3D world) {
        return world.getX() >= myBounds.getMinX() && world.getX() <= myBounds.getMaxX() &&
               world.getY() >= myBounds.getMinY() && world.getY() <= myBounds.getMaxY();
    }

    public void addSprite (GameSprite sprite) {
        if (!myContents.contains(sprite)) {
            myContents.add(sprite);
        }
    }

    public void removeSprite (GameSprite sprite) {
        if (myContents.contains(sprite)) {
            myContents.remove(sprite);
        }
    }

    public List<GameSprite> getContents () {
        return myContents;
    }

    public boolean containsSprite (GameSprite sprite) {
        return myContents.contains(sprite);
    }

    public <T extends GameSprite> List<T> filterGameSprites (List<GameSprite> fullList,
                                                             GameSprite gsType,
                                                             int teamID,
                                                             boolean same) {
        List<T> resultList = new ArrayList<T>();
        for (GameSprite item : fullList) {
            /*
             * if (item instanceof ) {
             * 
             * Determine whether these things are the same.
             * 
             * }
             */
            if (same) {

            }
            else {

            }
            resultList.add((T) item);
        }
        return resultList; 
    }

    public void paint (Graphics2D pen) {        
        for (GameSprite gs : myContents) {
            gs.paint(pen);
        }
    }
    
    /**
     * Paints the Node with a ellipse surrounding it. This is used for testing purposes.
     * Also prints the index of the node which can be the order that the node was painted.
     * 
     * @param pen The Graphics 2D to paint with
     * @param index The index of the node
     */
    public void paint (Graphics2D pen, int index) {
        Point2D screen =
                Camera.instance().worldToView(new Location3D(myX * NODE_SIZE, myY * NODE_SIZE, 0));
        if (myX % 10 == 0 || myY % 10 == 0) {
            pen.fill(new Ellipse2D.Double(screen.getX(), screen.getY(), NODE_SIZE, NODE_SIZE));
        }
        else
        {
            pen.draw(new Ellipse2D.Double(screen.getX(), screen.getY(), NODE_SIZE, NODE_SIZE));
        }
        pen.setColor(Color.red);
        pen.drawString(Integer.toString(index), (int)screen.getX() + NODE_SIZE/2, (int)screen.getY()+ NODE_SIZE/2);
        pen.setColor(Color.black);
        paint(pen);
    }

}
