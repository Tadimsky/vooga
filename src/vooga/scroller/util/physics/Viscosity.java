package vooga.scroller.util.physics;

import util.Vector;
import vooga.scroller.sprites.Sprite;

/**
 * This is how we want to handle viscosity. Every sprite will have an instance of this 
 * gravity object. 
 * 
 * @author Jay Wang
 */

public class Viscosity implements Force {

    Sprite sprite;
    private static final double VISCOSITY_CONSTANT = .8;

    private Vector viscosity;
    
    public Viscosity (Sprite sprite) {
        this.sprite = sprite;
        Vector direction = sprite.getVelocity();
        direction.negate();
        direction.scale(VISCOSITY_CONSTANT);
        
        double angle = direction.getDirection();
        double magnitude = direction.getMagnitude(); 
        
        viscosity = new Vector(angle, magnitude);
    }
    
    public void apply() {
        sprite.addVector(viscosity);
    }

}
