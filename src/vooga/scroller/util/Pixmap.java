
package vooga.scroller.util;

import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.geom.AffineTransform;
import javax.swing.Icon;
import javax.swing.ImageIcon;


/**
 * This class represents an image on the screen and 
 * adds some utility functions to the Image class.
 * 
 * Note, Java only supports the formats: png, jpg, gif.
 * 
 * @author Robert C. Duvall
 * Added getter for view @author DF
 */

public class Pixmap implements ISpriteView, IBackgroundView {
    // underlying implementation
    private java.awt.Image myImage;
    private String myFileName;


    /**
     * Create an image from the given directoryAndFilename.
     */
    public Pixmap (String directoryAndFileName) {
        setImage(directoryAndFileName);
    }
    
    /**
     * Create an image from the given filename.
     */
    public Pixmap (String directory, String fileName) {
        this(directory+fileName);
    }

    /**
     * Create a copy of image from the given other image.
     */
    public Pixmap (Pixmap other) {
        this(other.myFileName);
    }

    /**
     * Set this image to the image referred to by the given filename.
     */
    public void setImage (String fileName) {
        myImage = new ImageIcon(getClass().getResource(fileName)).getImage();
        myFileName = fileName;
    }
    

    /**
     * Describes how to draw the image on the screen.
     */
    @Override
    public void paint (Graphics2D pen, Point2D center, Dimension size) {
        paint(pen, center, size, 0);
    }

    /**
     * Describes how to draw the image rotated on the screen.
     */
    @Override
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
     * TODO - Get Version Control approval
     * @return the default image for this Pixmap
     */
    @Override
    public Image getDefaultImg() {
        return myImage;
    }
    
    @Override
    public ISpriteView reset () {
        // TODO Auto-generated method stub
        return this;
    }

    @Override
    public void setDefaultView (ISpriteView ispriteview) {
       // myImage = ispriteview
        // TODO: 
    }

    @Override
    public Image getImage () {
        return myImage;
    }

    @Override
    public String getFileName () {
        return myFileName;
    }

    /**
     * TODO - Changing the picture should be a function of the state,
     * not changing the image itself. A sprite should hold a collection
     * of its images and paint it.
     * TODO - Get Version Control approval
     * @param im
     */
    private void setImg(Image im) {
        myImage = im;
    }

}
