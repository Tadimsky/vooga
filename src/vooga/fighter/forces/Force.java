package vooga.fighter.forces;

import java.util.ResourceBundle;
import util.Vector;
import vooga.fighter.model.objects.CharacterObject;

/**
 * Abstract class that details a force that acts upon character objects.
 * If a dev wants to create a new force, he/she just subclasses this abstract
 * class and adds the class name and parameters to force.properties.
 * 
 * @author Jerry Li
 *
 */
public abstract class Force {


    
    /**
     * Set resource bundle
     */
    public Force() {

    }



    /**
     * Initialize force with direction and magnitude
     * @param param1    direction
     * @param param2    magnitude
     */
    public abstract void initialize(double param1, double param2);
    
    /**
     * Applies the force to this object
     * @param object    characterobject
     */
    public abstract void applyForce(CharacterObject object) ;
    
    /**
     * return the vector;
     * @return
     */
    public abstract Vector getVector();

}
