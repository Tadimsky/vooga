package vooga.fighter.forces;

import util.Vector;
import vooga.fighter.model.objects.CharacterObject;

/**
 * 
 * @author Jerry Li
 *
 */
public class Gravity extends Force {
    
    private double myDirection;
    private double myMagnitude;
    private Vector myVector;
    
    /**
     * Constructs gravity
     */
    public Gravity() {
        super();
        
    }

    /**
     * Applies gravity to character object
     */
    public void applyForce(CharacterObject object) {
        object.getLocation().addAcceleration(myVector);
    }
    
    /**
     * Initialize the force by setting direction and magnitude
     */
    public void initialize(double param1, double param2) {
        myDirection = param1;
        myMagnitude = param2;
        myVector= new Vector(myDirection, myMagnitude);
    }
    
    /**
     * Return the vector
     */
    public Vector getVector() {
        return myVector;
    }
    
}
