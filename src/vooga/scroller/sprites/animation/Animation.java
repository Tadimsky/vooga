package vooga.scroller.sprites.animation;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import vooga.scroller.util.ISpriteView;

/**
 * Represents an animation that depends on different movements of a sprite.
 * 
 * @param S is the object that this animation animates (i.e. a sprite).
 * 
 * @author Scott Valentine
 *
 */

public class Animation<S> implements ISpriteView {

    private List<AnimationState<S>> myAnimations;
    private S mySprite;
    private ISpriteView myDefaultStateView;

    /**
     * Creates a new animation that acts on a sprite.
     * 
     * @param defaultImageFile is the file path of the default image for this animation.
     * @param sp is the sprite on that this animation animates.
     */
    public Animation (S sp) {
        this();
        mySprite = sp;
        
        // myAnimations = new ArrayList<AnimationState>();
        //initAnimations();
    }
    
    public Animation() {
        myAnimations = new ArrayList<AnimationState<S>>();
    }
    
    public void setSprite(S sprite) {
        mySprite = sprite;
    }

    /**
     * Gives the current image that this animation should display
     * 
     * @return
     */
    private ISpriteView getStateView() {
        for (AnimationState<S> as: myAnimations) {
            if (as.validAnimation(mySprite)) {
                return as.getView();
            }
        }
        return this.getDefaultStateView();
    }
    
    private ISpriteView getDefaultStateView () {
        return myDefaultStateView;
    }

    public void setDefaultView (ISpriteView defaultImage) {
        myDefaultStateView = defaultImage;
    }
    
    @Override
    public void paint (Graphics2D pen, Point2D center, Dimension size, double angle) {
        ISpriteView currView = getStateView();
        currView.paint(pen, center, size, angle);
        
    }

    @Override
    public ISpriteView reset () {
        return myDefaultStateView.reset();
    }

    @Override
    public void paint (Graphics2D pen, Point2D myCenter, Dimension mySize) {
      ISpriteView currView = getStateView();
      currView.paint(pen, myCenter, mySize);
    }

    @Override
    public Image getDefaultImg () {
        
        return myDefaultStateView.getDefaultImg();
    }
    
    /**
     * Adds an animation state to this animation.
     * 
     * @param animation is the animation state to add.
     */
    public void addAnimationState(AnimationState<S> animation) {
        myAnimations.add(animation);
    }

    @Override
    public Image getImage () {
        // TODO Auto-generated method stub
        return null;
    }
}
