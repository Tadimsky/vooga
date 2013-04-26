package vooga.rts.manager;

import java.util.HashMap;
import java.util.Map;


/**
 * This class stores all the resources that a particular player has.
 * It provides methods to check if the player has enough resources to
 * purchase something and it also provides methods to use a resource
 * and add to a resource.
 * 
 * @author Jonathan Schmidt
 * 
 */
public class IndividualResourceManager {

    private Map<String, Integer> myResources;

    /**
     * Creates an individual resource manager.
     */
    public IndividualResourceManager () {
        myResources = new HashMap<String, Integer>();
    }

    /**
     * Returns how much of a type of resource the player has.
     * 
     * @param resource The resource type.
     * @return the amount of resources
     */
    public int getAmount (String resource) {
        if (myResources.containsKey(resource)) {
            return myResources.get(resource.toLowerCase());
        }
        return 0;
    }

    /**
     * Sets the resource manager map.
     * 
     * @param resources
     */
    public void setResources (HashMap<String, Integer> resources) {
        myResources = resources;
    }

    /**
     * Sets the amount of resources of a type that the player has.
     * 
     * @param type The type of resource
     * @param value The amount of resources
     */
    public void setAmount (String type, int value) {
        myResources.put(type.toLowerCase(), value);
    }

    /**
     * Charges the resource manager a certain amount of a type of resource.
     * 
     * @param type The type of resource
     * @param amount The cost of this type of resource
     */
    public void charge (String type, int amount) {
        setAmount(type, getAmount(type) - amount);
    }
    
    /**
     * Provides a certain amount of a type of resources to the player.
     * 
     * @param type The type of resource
     * @param amount The amount of this type of resource earned
     */
    public void earn (String type, int amount) {
        setAmount(type, getAmount(type) - amount);
    }

}
