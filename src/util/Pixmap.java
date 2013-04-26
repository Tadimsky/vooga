package util;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import javax.swing.ImageIcon;


/**
 * This class represents an image on the screen and
 * adds some utility functions to the Image class.
 * 
 * Note, Java only supports the formats: png, jpg, gif.
 * 
 * @author Robert C. Duvall, Dagbedji F, edited by @author Yoshida, @author Xu Rui, @author
 *         Bill Muensterman and @author Wayne You added paintReverse
 */
public class Pixmap {
    private java.awt.Image myImage;
    private String myFileName;
    private static final String RESOURCE_LOCATION = "/vooga/";
    
    /**
     * Create an image from the given path including filename.
     * The file "path" is the relative resource location from the source "src".
     * If filePath = "/"+pictureFileNameIncludingFileType, this object will look for a
     * .png/.jpg/.gif file inside src.
     * 
     * @param fileName Relative resource location including its fileName.
     *        Example: filePath = "/" + "car.png" will look for an image car.png in the src folder.
     */
    public Pixmap(String fileName) {
        setImage(fileName);
    }
    
    /**
     * Create a copy of image from the given other image.
     * 
     * @param other A <code>Pixmap</code> to be copied.
     */
    public Pixmap(Pixmap other) {
        this(other.myFileName);
    }
    
    /**
     * Set this image to the image referred to by the given filename.
     * 
     * @param filePath Relative resource location including its fileName.
     *        Example: filePath = "/" + "car.png" will look for an image car.png in the src folder.
     */
    public void setImage (String fileName) {
        myImage = new ImageIcon(getClass().getResource(RESOURCE_LOCATION + fileName)).getImage();
        myFileName = fileName;
    }
    
    /**
     * Describes how to draw the image on the screen.
     * 
     * @param pen A {@link Graphics2D} object that allows the picture to be painted.
     * @param center A {@link Point2D} for the location of the center of the picture to be painted.
     * @param size The size (width, length) of the image.
     */
    public void paint (Graphics2D pen, Point2D center, Dimension size) {
        paint(pen, center, size, 0);
    }
    
    /**
     * Describes how to draw the image rotated on the screen.
     * 
     * @param pen A {@link Graphics2D} object that allows the picture to be painted.
     * @param center A {@link Point2D} for the location of the center of the picture to be painted.
     * @param size The size (width, length) of the image.
     * @param angle The angle in <b>radians</b>!
     */
    public void paint (Graphics2D pen, Point2D center, Dimension size, double angle) {
        // save current state of the graphics area
        AffineTransform old = new AffineTransform(pen.getTransform());
        // move graphics area to center of this shape
        pen.translate(center.getX(), center.getY());
        // rotate area about this shape
        pen.rotate(angle);
        // draw as usual (i.e., rotated)
        pen.drawImage(myImage, -size.width / 2, -size.height / 2, size.width, size.height, null);
        // restore graphics area to its old state, so our changes have no lasting effects
        pen.setTransform(old);
    }
    
    /**
     * Describes how to draw the image on the screen reversed horizontally.
     * 
     * @param pen A {@link Graphics2D} object that allows the picture to be painted.
     * @param center A {@link Point2D} for the location of the center of the picture to be painted.
     * @param size The size (width, length) of the image.
     */
    public void paintReverse (Graphics2D pen, Point2D center, Dimension size) {
        // Get the current transform
        AffineTransform saveAT = pen.getTransform();
        // Perform transformation
        pen.transform(AffineTransform.getScaleInstance(-1, 1));
        // Render
        // g2d.draw(...);
        paint(pen, center, size);
        // Restore original transform
        pen.setTransform(saveAT);
    }
    
    /**
     * Gets the image of this <code>Pixmap</code> as a <code>java.awt.Image</code>
     * 
     * @return the image of this <code>Pixmap</code> as a <code>java.awt.Image</code>
     */
    public Image getImg () {
        return myImage;
    }
    
}
