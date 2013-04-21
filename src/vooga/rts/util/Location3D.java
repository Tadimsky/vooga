package vooga.rts.util;

import java.awt.geom.Point2D;


/**
 * This class represents a point in 3D space.
 * It provides helper methods to return values,
 * perform calculations and manipulate the location.
 * 
 * @author Jonathan Schmidt
 * 
 */
public class Location3D {

    public static final double APPROX_EQUAL = 1;
    private double myX;
    private double myY;
    private double myZ;

    /**
     * Create a Location3D at the origin.
     */
    public Location3D () {
        this(0.0, 0.0, 0.0);
    }

    /**
     * Creates a Location3D with the specified coordinates.
     * 
     * @param x The X value
     * @param y The Y value
     * @param z The Z value
     */
    public Location3D (double x, double y, double z) {
        myX = x;
        myY = y;
        myZ = z;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString () {
        return "Location3D [X=" + myX + ", Y=" + myY + ", Z=" + myZ + "]";
    }

    /**
     * Creates a Location3D with the specified Point 2D and
     * sets the Z value to 0.
     * 
     * @param source The Point2D to copy from.
     */
    public Location3D (Point2D source) {
        this(source.getX(), source.getY(), 0);
    }

    /**
     * Creates a Location3D with the same values as the specified parameter.
     * 
     * @param source The Location3D to copy from.
     */
    public Location3D (Location3D source) {
        this(source.getX(), source.getY(), 0);
    }

    /**
     * Reset this location to origin.
     */
    public void reset () {
        setLocation(0, 0, 0);
    }

    /**
     * Moves this Location by the specified 2D Vector.
     * Ignores the Z component of the vector.
     * 
     * @param amount The vector to translate by.
     */
    public void translate (Vector amount) {
        this.add(amount.getXChange(), amount.getYChange(), 0);
    }

    /**
     * Returns a vector that represents the difference between the
     * current location and another 2D location.
     * Ignores the Z value of the two locations.
     * 
     * @param other The other position.
     * @return The vector
     */
    public Vector difference (Point2D other) {
        return new Vector(new Point2D.Double(getX(), getY()), new Point2D.Double(other.getX(),
                                                                                 other.getY()));
    }

    // TODO: Translate by Vector Amount
    // TODO: Return the difference between two points as a vector.

    public void setLocation (double X, double Y, double Z) {
        myX = X;
        myY = Y;
        myZ = Z;
    }

    public void add (Location3D add) {
        this.add(add.getX(), add.getY(), add.getZ());
    }

    public void add (double x, double y, double z) {
        myX += x;
        myY += y;
        myZ += z;
    }

    /**
     * @return the X Coordinate
     */
    public double getX () {
        return myX;
    }

    /**
     * @return the Y Coordinate
     */
    public double getY () {
        return myY;
    }

    /**
     * @return the Z Coordinate
     */
    public double getZ () {
        return myZ;
    }

    /**
     * Returns the Euclidean distance between two points.
     * 
     * @param x The X value of the other point
     * @param y The Y value of the other point
     * @param z They Z value of the other point
     * @return
     */
    public double getDistance (double x, double y, double z) {
        return Math.sqrt(Math.pow(x - myX, 2) + Math.pow(y - myY, 2) + Math.pow(z - myZ, 2));
    }

    /**
     * Returns the Euclidean distance between two points.
     * 
     * @param other The other point
     * @return
     */
    public double getDistance (Location3D other) {
        return getDistance(other.getX(), other.getY(), other.getZ());
    }
    
    public void negate() {
        myX *= -1;
        myY *= -1;
        myZ *= -1;
    }
    
    public Location to2D() {
        return new Location(getX(), getY());
    }

    /**
     * Returns whether a location is 'near' another Location.
     * The amount that constitutes whether it is near or not is 
     * the APPROX_EQUAL constant. 
     * @param goal
     * @return
     */
    public boolean near (Location3D goal) {
        return (Math.abs(getX() - goal.getX()) < APPROX_EQUAL && 
                Math.abs(getY() - goal.getY()) < APPROX_EQUAL && 
                Math.abs(getZ() - goal.getZ()) < APPROX_EQUAL);        
    }
}
