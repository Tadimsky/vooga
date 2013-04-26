package vooga.fighter.forces;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Constructs the forces from a properties file
 * If a dev wants to add a new force, he subclasses force and adds it
 * to forces.properties
 * @author Jerry Li
 *
 */
public class ForceFactory {
    
    private static final String delimiter= ",";
    private String myForcePathway = "config.forces";
    private String myForcePackage = "forces.";
    private List<Force> myForces;
    private ResourceBundle myForceResources;
    
    /**
     * Constructs force factory, sets resource bundle
     */
    public ForceFactory(String name) {
        myForcePathway = name + myForcePathway;
        myForcePackage = name + myForcePackage;
        myForces = new ArrayList<Force>();
        myForceResources = ResourceBundle.getBundle(myForcePathway);
        initializeForces();
    }
    
    /**
     * Constructs list of forces
     */
    public void initializeForces() {
        myForces = new ArrayList<Force>();
        for (String forceName : myForceResources.keySet()) {
            Force current = constructForce(forceName);
            String[] parameters = myForceResources.getString(forceName).split(delimiter);
            if (parameters.length == 2) {
                current.initialize(Double.parseDouble(parameters[0]), Double.parseDouble(parameters[1]));
            }
            else {
                current.initialize(Double.parseDouble(parameters[0]), 0);
            }
            myForces.add(current);
        }
    }
    
    /**
     * Constructs force using reflection and the resource bundle
     * @param name      name
     * @return          
     */
    public Force constructForce(String name) {
        Object objectForce = null;
        Force force = null;
        try {
            Class<?> forceClass = null;
            String filePath = myForcePackage + name;
            forceClass = Class.forName(filePath);
            objectForce = forceClass.newInstance();
            force = (Force) objectForce;
            //controller.initializeName(myResources.getString(controllerName));
            
        }
        catch (Exception e){
            throw new NullPointerException("No such class");
        }
        return force;
    }
    
    /**
     * Return list of forces
     * @return
     */
    public List<Force> getForces() {
        return myForces;
    }
}
