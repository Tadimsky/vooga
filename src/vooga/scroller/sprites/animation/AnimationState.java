package vooga.scroller.sprites.animation;

import vooga.scroller.util.Pixmap;

/**
 * Abstract class that represents an animation state of a sprite. 
 * These could be walking left/right, jumping, falling, etc.
 * 
 * @author Scott Valentine
 *
 */
public abstract class AnimationState<S> {

    private Pixmap myImage;
    
    /**
     * Creates a new animation state with the image to be displayed when activated.
     * 
     * @param image is the Image that this animation state shows.
     */
    public AnimationState(Pixmap image) {
        myImage = image;
    }
    
    /**
     * Returns whether or not the animation is valid given the unit on which the animation acts.
     * 
     * @param unit is the Sprite on which the animation acts
     * @return boolean representing the ability of this animation to occur. 
     */
    public abstract boolean validAnimation(S unit);
        
    /**
     * Gives the current image of this animation state.
     * 
     * @return The image used by this animation state.
     */
    public Pixmap getView() {
        return myImage;
    }
    
}
