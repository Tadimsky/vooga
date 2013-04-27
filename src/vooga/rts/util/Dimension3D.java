package vooga.rts.util;

import java.awt.Dimension;
import java.awt.geom.Point2D;


/**
 * This class represents a dimension in 3D space.
 * It provides helper methods to return values,
 * perform calculations and manipulate the dimension.
 * 
 * @author Jonathan Schmidt
 * 
 */
public class Dimension3D {

    private double myWidth;
    private double myDepth;
    private double myHeight;

    /**
     * Create a Dimension3D with no size.
     */
    public Dimension3D () {
        this(0.0, 0.0, 0.0);
    }

    /**
     * Creates a Dimension 3D with the specified sizes.
     * 
     * @param width The width of the dimension (X axis)
     * @param depth The depth of the dimension (Y axis)
     * @param height The height of the dimension (Z axis)
     */
    public Dimension3D (double width, double depth, double height) {
        myWidth = width;
        myDepth = depth;
        myHeight = height;
    }

    /**
     * Creates a Dimension3D with the same values as the specified parameter.
     * 
     * @param source The Dimension3D to copy from.
     */
    public Dimension3D (Dimension3D source) {
        this(source.getWidth(), source.getDepth(), source.getHeight());
    }

    /**
     * Sets the values of an existing Dimension3D
     * 
     * @param width The new width of the dimension.
     * @param depth The new depth of the dimension.
     * @param height The new height of the dimension.
     */
    public void setDimension (double width, double depth, double height) {
        myWidth = width;
        myDepth = depth;
        myHeight = height;
    }

    /**
     * Adds the values in the provided Dimension3D to the current Dimension3D
     * 
     * @param add The dimension to add to the current dimension
     */
    public void add (Dimension3D add) {
        this.add(add.getWidth(), add.getDepth(), add.getHeight());
    }

    /**
     * Adds the values to the current dimension.
     * 
     * @param width The width to add
     * @param depth The depth to add
     * @param height The height to add
     */
    public void add (double width, double depth, double height) {
        myWidth += width;
        myDepth += depth;
        myHeight += height;
    }

    /**
     * @return the width of the dimension (X axis)
     */
    public double getWidth () {
        return myWidth;
    }

    /**
     * @return the depth of the dimension (Y axis)
     */
    public double getDepth () {
        return myDepth;
    }

    /**
     * @return the height of the dimension (Z axis)
     */
    public double getHeight () {
        return myHeight;
    }

    @Override
    public String toString () {
        return "Dimension3D [Width=" + myWidth + ", Depth=" + myDepth + ", Height=" + myHeight +
               "]";
    }

}
