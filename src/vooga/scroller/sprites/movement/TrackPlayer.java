package vooga.scroller.sprites.movement;

import util.Vector;
import vooga.scroller.sprites.Sprite;
import vooga.scroller.sprites.interfaces.Locatable;

/**
 * This is a type of Movement that does simple AI in tracking the player. This method's execute() 
 * method will return the vector in the direction of the player with the given speed. 
 * 
 * @author Jay Wang
 * @author Scott Valentine
 *
 */
public class TrackPlayer implements Movement {

    private int mySpeed;
    private int myRadius;
    private Sprite myPredator;
    private Locatable myTarget; 
    
    /**
     * Creates a new TrackPlayer object that is tied to a specific sprite.
     * 
     * @param predator is the Sprite that is moving towards the target.
     * @param target is the object that the predator is moving towards.
     * @param speed is the speed at which the predator stalks the target.
     * @param radius is the radius at which the predator becomes aware ot the target.
     */
    public TrackPlayer (Sprite predator, Locatable target, int speed, int radius) {
        super();
        myPredator = predator;
        myTarget = target;
        mySpeed = speed;
        myRadius = radius;
    }
    

    /**
     * This method will allow a GameCharacter to track down a Player - a simple AI of sorts. 
     * Essentially, if the player gets within the specified radius of this entity, the entity will 
     * change its velocity vector with a vector that is in the direction of the player with the 
     * given SPEED. 
     * 
     * @param speed
     * @param radius
     * @return a vector that in direction of the player with the given SPEED
     */
    public void execute () {
        if (Vector.distanceBetween(myPredator.getCenter(), myTarget.getCenter()) < (double) myRadius) {
            myPredator.setVelocity(Vector.angleBetween(myPredator.getCenter(), myTarget.getCenter()), mySpeed); 
        }
    }

    /**
     * Sets the target that this tracking will point to.
     * 
     * @param target is the Locatable object that this movement will move towards.
     */
    public void setTarget (Locatable target) {
        myTarget = target;       
    }

}
