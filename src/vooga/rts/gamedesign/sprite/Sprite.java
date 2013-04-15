package vooga.rts.gamedesign.sprite;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;


import vooga.rts.IGameLoop;
import vooga.rts.util.*;

public abstract class Sprite implements IGameLoop {

    
    private boolean isVisible;
    // state
    private Location myCenter;
    //The location above should be screenLocation 
    private Pixmap myView;
    // keep copies of the original state so shape can be reset as needed
    private Location myOriginalCenter;
    
    private Rectangle myOriginalBounds;

    private Pixmap myOriginalView;
    // cached for efficiency
    private Rectangle myBounds;
    
    public void setVisible(Boolean b){
    	isVisible = b;
    }
	
    public boolean isVisible(){
    	return isVisible;
    }

    /**
     * Create a shape at the given position, with the given size, velocity, and color.
     */
    public Sprite (Pixmap image, Location center) {
        // make copies just to be sure no one else has access
        myOriginalCenter = new Location(center);
        myView = image; //new Pixmap(image);
        isVisible = true;
        myCenter = new Location(center);
    }
    /**
     * Resets shape's center.
     */
    private void setCenter (double x, double y) {
        myCenter.setLocation(x, y);
        resetBounds();
    }
    
    private Location getCenter() {
    	return myCenter;
    }
    
    private Location getOriginalCenter() {
    	return myOriginalCenter;
    }

    /**
     * Resets shape's image.
     */
    public void setView (Pixmap image) {
        if (image != null) {
            myView = image;
        }
    }
    
    public Pixmap getView() {
    	return myView;
    }

    /**
     * Returns rectangle that encloses this shape.
     */
    private Rectangle getBounds () {
        return myBounds;
    }
    
    private void setBounds(Rectangle bound){
    	myBounds = bound;
    }
    /*
     * Returns pixmap that is the image of this sprite
     */
    public Pixmap getImage(){
        return myView;
    }

    /**
     * Reset shape back to its original values.
     */
    public void reset () {
        myCenter = new Location(myOriginalCenter);
        myView = new Pixmap(myView);
    }
    
 

    /**
     * Display this shape on the screen.
     */
    public void paint (Graphics2D pen)
    {   
    	if(!isVisible) return;
        //myView.paint(pen, myCenter);
    }

    /**
     * Returns rectangle to the original bound.
     */
    protected void resetBounds () {
        myBounds = myOriginalBounds;
    }


}
