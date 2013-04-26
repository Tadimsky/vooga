package vooga.fighter.model.objects;

import util.Location;
import util.Pixmap;
import vooga.fighter.model.loaders.ObjectLoader;
import vooga.fighter.model.utils.ImageDataObject;
import vooga.fighter.model.utils.State;
import vooga.fighter.model.utils.UpdatableLocation;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Represents a single object in the game.
 * 
 * @author James Wei, alanni, David Le
 * 
 */
public abstract class GameObject {
    
    private long myInstanceId;
    private ObjectLoader myLoader;
    private UpdatableLocation myCenter;
    private State myCurrentState;
    private String myCurrentStateKey;
    private State myDefaultState;
    private String myDefaultStateKey;
    private ImageDataObject myImageData;
    private Map<String,State> myStates;
    private Map<String,Integer> myProperties;
    private List<Integer> myImageEffects;
    private boolean myRemoveState;
    
    /**
     * Constructs a new GameObject. All fields are initially empty, and must be
     * populated with an ObjectLoader.
     */
    public GameObject() {
        myInstanceId = System.currentTimeMillis();
    	myImageEffects = new ArrayList<Integer>();
        myStates = new HashMap<String,State>();
        myProperties = new HashMap<String,Integer>();
        myLoader = null;
        myCurrentState = null;
        myCurrentStateKey = null;
        myDefaultState = null;
        myDefaultStateKey = null;
        myImageData = null;
        myRemoveState = false;
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            
        }
    }
    
    /**
     * Returns the game object's instance id. This id is unique to this particular
     * instantiation of the object.
     */
    public long getInstanceId() {
        return myInstanceId;
    }

    /**
     * Sets game object's center.
     */
    public void setLocation(UpdatableLocation ul) {
        myCenter = ul;
    }

    /**
     * Returns game object's center.
     */
    public UpdatableLocation getLocation() {
        return myCenter;
    }

    
    /**
     * Adds a property for this object. Overwrites any existing value.
     */
    public void addProperty(String key, int value) {
        myProperties.put(key, value);
    }

    /**
     * Returns a property for this object. Returns -1 if property does not exist.
     */
    public int getProperty(String key) {
        if (myProperties.containsKey(key)) {
            return myProperties.get(key);
        } else {
            return -1;
        }
    }
    
    /**
     * Clears all properties.
     */
    public void clearProperties() {
        myProperties.clear();
    }
    
    /**
     * Adds a state for this object. Overwrites any existing value.
     */
    public void addState(String key, State value) {
        myStates.put(key, value);
    }
    
    /**
     * Returns a state for this object. Returns null if it doesn't exist.
     */
    public State getState(String key) {
        if (myStates.containsKey(key)) {
            return myStates.get(key);
        } else {
            return null;
        }
    }
    
    /**
     * Sets the current state for this object. Resets the current state after
     * switching.
     */
    public void setCurrentState(String key) {
        myCurrentState = getState(key);
        myCurrentStateKey = key;
        myCurrentState.resetState();
    }
    
    /**
     * Sets the current state to the default state.
     */
    public void setToDefaultState() {
    	setCurrentState(getDefaultStateKey());
    }
    
    /**
     * Sets the default state for this object.
     */
    public void defineDefaultState(String key) {
        myDefaultState = getState(key);
        myDefaultStateKey = key;
    }
    
    /**
     * Gets the String key of the default state.
     */
    public String getDefaultStateKey() {
        return myDefaultStateKey;
    }
    
    /**
     * Gets the String key of the current state.
     */
    public String getCurrentStateKey() {
        return myCurrentStateKey;
    }
    
    /**
     * Gets the current state for this object.
     */
    public State getCurrentState() {
        return myCurrentState;
    }

    /**
     * Clears all states.
     */
    public void clearStates() {
        myStates.clear();
    }
    
    /**
     * Sets the object loader for this object.
     */
    public void setLoader(ObjectLoader loader) {
        myLoader = loader;
    }
    
    /**
     * Returns the object loader for this object.
     */
    public ObjectLoader getLoader() {
        return myLoader;
    }
    
    /**
     * Sets image data for this object. Size, Location, and Image must not be
     * null for this object before calling this method, otherwise, this method
     * returns null.
     */
    public void setImageData() {
        Pixmap myCurrentImage = myCurrentState.getCurrentImage();
        Dimension myCurrentSize = myCurrentState.getCurrentSize();
        Location myCurrentLocation = myCenter.getLocation();
        if (!(myCurrentSize == null || myCurrentImage == null || myCenter == null)) {
        	myImageData = new ImageDataObject(myCurrentImage, myCurrentLocation, myCurrentSize, myImageEffects);
        }
    }
    
    /**
     * Sets image data to the information from an ImageDataObject
     */
    public void setImageData(ImageDataObject image){
    	myImageData= new ImageDataObject(image.getImage(), image.getLocation(),image.getSize(), image.getImageEffect() );
    }
    
    /**
     * Returns image data for this object.
     */
    public ImageDataObject getImageData() {
        return myImageData;
    }
    
    /**
     * Updates the object for the game loop. Should be overridden by subclasses if
     * necessary, but all overrides should first call superclass method.
     */
    public void update() {
    	setImageData();
        if (myCenter != null) {
            myCenter.update();
        }
        completeUpdate();
    }
    
    /**
     * Updates the objects state for the game loop. States are updated separately
     * because all states must be updated together, either before or after other
     * update logic that depends on current states.
     */
    public void updateState() {
        if (myCurrentState != null) {
            myCurrentState.update();
        }
        if (myCurrentState.hasCompleted()) {
            stateCompleteUpdate();
        }
    }
    
    /**
     * Determines behavior to be taken if the state has completed. Note that looping
     * states will automatically reset. By default this method sets the current state
     * to the default state.
     */
    public void stateCompleteUpdate() {
        myCurrentState = myDefaultState;
        myCurrentStateKey = myDefaultStateKey;
        myCurrentState.resetState();
    }
    
    /**
     * Returns true if this object is colliding with another.
     */
    public boolean checkCollision(GameObject other) {
        Rectangle thisRect = getCurrentState().getCurrentRectangle(); 
        Rectangle otherRect = other.getCurrentState().getCurrentRectangle();
        return thisRect.intersects(otherRect);
    }
    
    /**
     * Returns the map of states for this object.
     */
    public Map<String,State> getStates(){
        return myStates;
    }
    
    /**
     * Indicates whether or not the object is ready to be removed.
     */
    public boolean shouldBeRemoved() {
    	return myRemoveState;
    }
    
    /**
     * Sets the removeState of this object
     */
    public void setRemoveState(boolean bool) {
    	myRemoveState = bool;
    }
    
    /**
     * Checks if this object is equal to another by using the instance id.
     */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        GameObject other;
        if (o instanceof GameObject) {
            other = (GameObject) o;
        } else {
            return false;
        }        
        return myInstanceId == other.getInstanceId();
    }
    
    /**
     * Returns hashcode for the game object by casting instance id to int.
     */
    public int hashcode() {
        return (int) myInstanceId;
    }
    
    /**
     * Handles additional update logic outside of resolving movement on the object
     * and setting image data.
     */
    public abstract void completeUpdate();
    
    
    
}
