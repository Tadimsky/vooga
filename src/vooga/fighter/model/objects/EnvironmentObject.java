package vooga.fighter.model.objects;

import vooga.fighter.model.loaders.EnvironmentObjectLoader;
import vooga.fighter.model.utils.UpdatableLocation;

/**
 * Represents an environment object like a block or platform.
 * 
 * More behavior will be added.
 * 
 * @author James Wei, alanni, David Le
 * 
 */
public class EnvironmentObject extends GameObject {
	private String myName;

	/**
	 * Constructs a new EnvironmentObject without a given center; used for level editor.
	 */
	public EnvironmentObject(String name, String pathHierarchy) {
		super();
		init(name, pathHierarchy);
	}
	
    /**
     * Constructs a new EnvironmentObject with the given image, center, and size.
     * In the future this will use the object loader to read from XML.
     */
    public EnvironmentObject(String name, UpdatableLocation center, String pathHierarchy) {
        super();
        setLocation(center);
        init(name, pathHierarchy);
    }
    
    
    /**
     * Initializes environment object
     */
    private void init(String name, String pathHierarchy) {
    	myName = name;
        setLoader(new EnvironmentObjectLoader(name, this, pathHierarchy));
        setCurrentState("default");
        getCurrentState().setLooping(true);
        setImageData();
    }
    
    /**
     * return the name of this type of environment object.
     * @return myName
     */
    public String getName() {
    	return myName;
    }

    /**
     * Updates the environment object. Behavior to be added.
     */
    public void completeUpdate() {
        
    }
}
