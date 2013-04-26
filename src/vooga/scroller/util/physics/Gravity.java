package vooga.scroller.util.physics;

import util.Vector;
import vooga.scroller.sprites.Sprite;

/**
 * This is how we want to handle gravity. Every sprite will have an instance of this 
 * gravity object. 
 * 
 * @author Jay Wang
 */

public class Gravity implements Force {

    Sprite sprite;
    private static final double GRAVITY_CONSTANT = -5;

    private Vector gravityVector;
    
    public Gravity (Sprite sprite) {
        this.sprite = sprite;
        gravityVector = new Vector(Sprite.UP_DIRECTION, GRAVITY_CONSTANT);
    }
    
    public void apply() {
        sprite.addVector(gravityVector);
    }

}
