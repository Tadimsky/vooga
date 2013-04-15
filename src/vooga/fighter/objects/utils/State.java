package vooga.fighter.objects.utils;

import java.awt.Rectangle;
import vooga.fighter.objects.GameObject;
import util.Pixmap;

/**
 * Holds a series of rectangles and images representing one state of an object.
 * Used for collision detection and handling, as well as animation.
 * 
 * @author james, David Le
 *
 */
public class State {

    private int myNumFrames;
    private int myCurrentFrame;
    
    /**
     * The priority of the hitbox when interacting with other hitboxes.
     * Lower numbers indicate higher priority.
     */
    private int myPriority;
    
    /**
     * The depth of the animations when overlapping with other animations.
     * Lower numbers indicate "deeper" images, aka images with lower depth
     * are drawn first (and thus other animations are drawn on top of them).
     */
    private int myDepth;
    
    private Pixmap[] myImages;
    private Rectangle[] myRectangles;    
    private GameObject myOwner;    
    
    /**
     * Creates a state with the given owner, number of frames, and default priority of zero.
     */
    public State(GameObject owner, int numFrames) {
        this(owner, numFrames, 0, 0);
    }
    
    /**
     * Creates a state with the given owner, number of frames, and priority.
     */
    public State(GameObject owner, int numFrames, int priority, int depth) {
        myOwner = owner;
        myNumFrames = numFrames;
        myPriority = priority;
        myDepth = depth;
        myRectangles = new Rectangle[myNumFrames];
        myImages = new Pixmap[myNumFrames];
    }    
    
    /**
     * Adds the rectangles for the hitboxes into this Pixmap's rectangle array.
     * 
     * Not yet implemented.
     * 
     * Note: Dayvid once you determine how you want the data from the XML file to be
     * loaded into a hitbox object we will tweak this method appropriately.
     */
    public void populateRectangle(Rectangle rect, int index) {
        myRectangles[index] = rect;
    }
    
    /**
     * Adds the images into this state's Pixmap array.
     * 
     * Not yet implemented.
     * 
     * Note: Dayvid once you determine how you want the data from the XML file to be
     * loaded into a hitbox object we will tweak this method appropriately.
     */
    public void populateImage(Pixmap image, int index) {
        myImages[index] = image;
    }
    
    /**
     * Returns the current active rectangle for this state.
     */
    public Rectangle getCurrentRectangle() {
        return myRectangles[myCurrentFrame];
    }
    
    /**
     * Returns the current active image for this state.
     */
    public Pixmap getCurrentImage() {
        return myImages[myCurrentFrame];
    }       
    
    /**
     * Returns the priority of this state. Lower numbers are considered higher
     * priority.
     */
    public int getPriority() {
        return myPriority;
    }
    
    /**
     * Returns the depth of this state. Lower numbers are considered lower depth,
     * i.e. an image with a lower depth will be drawn first (and thus other
     * images will be drawn on top of it).
     */
    public int getDepth() {
        return myDepth;
    }
    
    /**
     * Returns true if this state's hitbox has priority over another, false otherwise.
     * Note that returning false DOES NOT necessarily indicate that the other
     * hitbox has priority, as the two could have equal priorities.
     */
    public boolean hasPriority(State other) {
        int difference = myPriority - other.getPriority();
        return (difference < 0);
    }
    
    /**
     * Returns true if this state's image is deeper than another, false otherwise.
     * Note that returning false DOES NOT necessarily indicate that the other
     * image is deeper, as the two could have equal depths.
     */
    public boolean hasDepth(State other) {
        int difference = myDepth - other.getDepth();
        return (difference < 0);
    }
    
    /**
     * Progresses this state to the next frame in its animation and hitbox.
     */
    public void update() {
        myCurrentFrame++;
    }
    
    /**
     * Resets the current frame to the beginning of the state.
     */
    public void resetState() {
        myCurrentFrame = 0;
    }
    
    /**
     * Returns true if the state's animation has concluded, false otherwise.
     * Concluded in this sense means after the final animation has updated, i.e.
     * a concluded state has progressed to a frame beyond the number of frames
     * it actually has.
     */
    public boolean hasCompleted() {
        return (myCurrentFrame >= myNumFrames);
    }
}
