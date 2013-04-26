package vooga.scroller.sprites.interfaces;

import java.awt.geom.Point2D;
import util.Vector;


/**
 * The super-interface that all other interfaces extend. This just provides all the interfaces
 * with access to basic sprite methods that returns basic location about the sprite. Now, the
 * visit()
 * methods have access to all of these methods on the interfaces they operate on.
 * 
 * @author Jay Wang
 * 
 */
public interface Locatable {

    /**
     * Returns the bottom pixel of the Sprite.
     * 
     * @return double
     */
    public double getBottom ();

    /**
     * Returns the top pixel of the Sprite.
     * 
     * @return double
     */
    public double getTop ();

    /**
     * Returns the left pixel of the Sprite.
     * 
     * @return double
     */
    public double getLeft ();

    /**
     * Returns the right pixel of the Sprite.
     * 
     * @return double
     */
    public double getRight ();

    /**
     * Returns the location of the Sprite's X location
     * 
     * @return double
     */
    public double getX ();

    /**
     * Returns the location of the Sprite's Y location
     * 
     * @return double
     */
    public double getY ();

    /**
     * Returns the size of the Sprite's width in pixels
     * 
     * @return double
     */
    public double getWidth ();

    /**
     * Returns the size of the Sprite's height in pixels
     * 
     * @return double
     */
    public double getHeight ();

    /**
     * Sets the center of the Sprite to Location(x,y)
     */
//    public void setCenter (double x, double y);
//
//    /**
//     * Adds the Vector r to the current Velocity vector of the Sprite.
//     * 
//     * @param Vector
//     */
//    public void addVector (Vector r);
//
//    /**
//     * Returns the Velocity vector of the Sprite.
//     * 
//     * @return Vector
//     */
    public Vector getVelocity ();

    public Point2D getCenter ();

}
