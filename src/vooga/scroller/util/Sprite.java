
package vooga.scroller.util;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.Queue;


/**
 * This class represents a shape that moves on its own.
 * 
 * Note, Sprite is a technical term:
 *   http://en.wikipedia.org/wiki/Sprite_(computer_graphics)
 *   
 * @author Robert C. Duvall
 */
public abstract class Sprite {
    // canonical directions for a collision
    public static final int RIGHT_DIRECTION = 0;
    public static final int UP_DIRECTION =  270;
    public static final int LEFT_DIRECTION = 180;
    public static final int DOWN_DIRECTION = 90;

    // state
    private Location myCenter;
    private Vector myVelocity;
    private Dimension mySize;
    private Pixmap myView;
    // keep copies of the original state so shape can be reset as needed
    private Location myOriginalCenter;
    private Vector myOriginalVelocity;
    private Dimension myOriginalSize;
    private Pixmap myOriginalView;
    // cached for efficiency
    private Rectangle myBounds;
    private Location myLastLocation;
    private Location myLastLocation2;
    private Pixmap myDefaultImage;
    
    /**
     * Create a shape at the given position, with the given size.
     */
    public Sprite (Pixmap image, Location center, Dimension size) {
        this(image, center, size, new Vector());
    }

    /**
     * Create a shape at the given position, with the given size, velocity, and color.
     */
    public Sprite (Pixmap image, Location center, Dimension size, Vector velocity) {
        // make copies just to be sure no one else has access
        
        myDefaultImage = image;
        myOriginalCenter = new Location(center);
        myLastLocation = new Location(myOriginalCenter.x, myOriginalCenter.y);
        myLastLocation2 = new Location(myOriginalCenter.x, myOriginalCenter.y);
        myOriginalSize = new Dimension(size);
        myOriginalVelocity = new Vector(velocity);
        myOriginalView = new Pixmap(image);
        reset();
        resetBounds();
    }
    
    public Sprite copy(){
        try {
            return this.getClass().newInstance();
        }
        catch (InstantiationException e) {
            return null;
        }
        catch (IllegalAccessException e) {
            return null;
        }
    }

    /**
     * Describes how to "animate" the shape by changing its state.
     * 
     * Currently, moves by the current velocity.
     */
    public void update (double elapsedTime, Dimension bounds) {
        myLastLocation2 = new Location(myLastLocation.x, myLastLocation.y);
        myLastLocation = new Location(myCenter.x, myCenter.y);

        // do not change original velocity
        Vector v = new Vector(myVelocity);
        v.scale(elapsedTime);
        translate(v);
    }

    /**
     * Moves shape's center by given vector.
     */
    public void translate (Vector v) {
        myCenter.translate(v);
        resetBounds();
    }

    /**
     * Resets shape's center.
     */
    public void setCenter (double x, double y) {
        myCenter.setLocation(x, y);
        resetBounds();
    }
    
    /**
     * Returns myCenter
     */
    public Location getCenter() {
        return myCenter;
    }

    /**
     * Returns shape's x coordinate in pixels.
     */
    public double getX () {
        return myCenter.getX();
    }

    /**
     * Returns shape's y-coordinate in pixels.
     */
    public double getY () {
        return myCenter.getY();
    }

    /**
     * Returns shape's left-most coordinate in pixels.
     */
    public double getLeft () {
        return myCenter.getX() - mySize.width / 2;
    }

    /**
     * Returns shape's top-most coordinate in pixels.
     */
    public double getTop () {
        return myCenter.getY() - mySize.height / 2;
    }

    /**
     * Returns shape's right-most coordinate in pixels.
     */
    public double getRight () {
        return myCenter.getX() + mySize.width / 2;
    }

    /**
     * Returns shape's bottom-most coordinate in pixels.
     */
    public double getBottom () {
        return myCenter.getY() + mySize.height / 2;
    }

    /**
     * Returns shape's width in pixels.
     */
    public double getWidth () {
        return mySize.getWidth();
    }

    /**
     * Returns shape's height  in pixels.
     */
    public double getHeight () {
        return mySize.getHeight();
    }

    /**
     * Scales shape's size by the given factors.
     */
    public void scale (double widthFactor, double heightFactor) {
        mySize.setSize(mySize.width * widthFactor, mySize.height * heightFactor);
        resetBounds();
    }

    /**
     * Resets shape's size.
     */
    public void setSize (int width, int height) {
        mySize.setSize(width, height);
        resetBounds();
    }

    /**
     * Returns shape's velocity.
     */
    public Vector getVelocity () {
        return myVelocity;
    }

    /**
     * Resets shape's velocity.
     */
    public void setVelocity (double angle, double magnitude) {
        myVelocity = new Vector(angle, magnitude);
    }

    /**
     * Resets shape's image.
     */
    public void setView (Pixmap image) {
        if (image != null) {
            myView = image;
        }
    }

    /**
     * Returns rectangle that encloses this shape.
     */
    public Rectangle getBounds () {
    	resetBounds();
    	return myBounds;   //restBounds*)
    }

    /**
     * Returns true if the given point is within a rectangle representing this shape.
     */
    public boolean intersects (Sprite other) {
        return getBounds().intersects(other.getBounds());
    }
    
    public boolean intersects (Rectangle other) {
        return getBounds().intersects(other.getBounds());
    }
    

    /**
     * Returns true if the given point is within a rectangle representing this shape.
     */
    public boolean intersects (Point2D pt) {
        return getBounds().contains(pt);
    }

    /**
     * Reset shape back to its original values.
     */
    public void reset () {
        myCenter = new Location(myOriginalCenter);
        mySize = new Dimension(myOriginalSize);
        myVelocity = new Vector(myOriginalVelocity);
        myView = new Pixmap(myOriginalView);
    }

    /**
     * Display this shape on the screen.
     */
    public void paint (Graphics2D pen)
    {
        myView.paint(pen, myCenter, mySize);
    }
    
    /**
     * Display this shape translated on the screen, used for all Sprites besides Player
     */
    public void paint (Graphics2D pen, Location loc, Location origLoc) {
        myView.paint(pen, translate(loc, origLoc), mySize);
        
    }
    
    /**
     * The translates the the Location with respect to the Location given.
     * @param loc the Location to translate in relation to
     * @return the translated Location
     */
    
    private Location translate(Location loc, Location origLoc) {
        Location temp =  new Location(myCenter.getX()-(loc.getX()-origLoc.getX()), myCenter.getY() - (loc.getY() - origLoc.getY()));        
        return temp;
    }
    

    /**
     * Returns rectangle that encloses this shape.
     */
    protected void resetBounds () {
        myBounds = new Rectangle((int)getLeft(), (int)getTop(), mySize.width, mySize.height);
    }

    /**
     * Returns approximate direction from center of rectangle to side which was hit or
     * NaN if no hit took place.
     */
    protected double getHitDirection (Rectangle bounds) {
        // double angle = Vector.angleBetween(myCenter, new Location(bounds.getCenterX(), bounds.getCenterY()));
        // BUGBUG: FIX ME --- this is very imperfect, but sort of works for now
        if (bounds.contains(new Location(getLeft(), getY()))) {
            return RIGHT_DIRECTION;
        }
        else if (bounds.contains(new Location(getX(), getBottom()))) {
            return UP_DIRECTION;
        }
        else if (bounds.contains(new Location(getRight(), getY()))) {
            return LEFT_DIRECTION;
        }
        else if (bounds.contains(new Location(getX(), getTop()))) {
            return DOWN_DIRECTION;
        }
        return 0;
        //return Double.NaN;
    }
    
    /**
     * Returns a view of this sprite -TODO: Maybe should be specified in an interface(?)
     */
    public Pixmap getView() {
        return myView;
    }
    
    /**
     * Gives the last location of this sprite.
     * 
     * @return The locaiton of the sprite at the previous update.
     */
    public Location lastLocation() {
        return myLastLocation2;
    }
    
    /**
     * Returns the default image for this sprite.
     * 
     * @return
     */
    public Image getDefaultImg () {
        
        return myDefaultImage.getImg();
    }


}
