package vooga.fighter.model;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import vooga.fighter.model.objects.GameObject;
import vooga.fighter.model.utils.ImageDataObject;
import vooga.fighter.util.CollisionManager;


/**
 * Represents a mode in the game. Holds a list of all game objects in the mode,
 * and updates those every update cycle.
 * 
 * @author James Wei, alanni
 * 
 */
public abstract class Mode {

    private List<GameObject> myObjects;
    private List<ImageDataObject> myImageDataObjects;
    private long myId;
    private CollisionManager myCollisionManager;

    /**
     * Constructs a new Mode.
     */
    public Mode(CollisionManager collisionmanager) {
        myObjects = new ArrayList<GameObject>();
        myImageDataObjects = new ArrayList<ImageDataObject>();
        myCollisionManager = collisionmanager;
    }

    /**
     * Returns the id of the mode.
     */
    public long getMyId() {
        return myId;
    }

    /**
     * Returns the list of objects for this mode.
     */
    public List<GameObject> getMyObjects() {
        return myObjects;
    }

    /**
     * Add an object to the list of game objects.
     */
    public void addObject(GameObject object) {
        myObjects.add(object);
        myImageDataObjects.add(object.getImageData());
    }

    /**
     * Remove an object from the list of game objects.
     */
    public void removeObject(GameObject object) {
        myObjects.remove(object);
        myImageDataObjects.remove(object.getImageData());
    }

    /**
     * Handles collisions between objects in this mode. Collision checking is
     * delegated to the CollisionManager, and the handling of individual collisions
     * is achieved by delegating to the objects themselves through double dispatch
     * in the visitor design pattern.
     */
    public void handleCollisions() {
        myCollisionManager.checkCollisions(myObjects);
    }

    /**
     * Creates the list of image data objects and returns it.
     */
    public List<ImageDataObject> getImageData() {
        List<ImageDataObject> result = new ArrayList<ImageDataObject>();
        for (GameObject object : getMyObjects()) {
            result.add(object.getImageData());
        }
        return result;
    }

    /**
     * Removes objects that have been destroyed or have timed out
     */
    public void removeAppropriateObjects() {
        ArrayList<GameObject> objectsCopy = new ArrayList<GameObject>(myObjects);
        for (GameObject o : objectsCopy) {
            if (o.shouldBeRemoved()) {
                removeObject(o);
            }
        }
    }

    /**
     * Updates the mode for one game loop. Implemented by subclasses.
     */
    public abstract void update();

}
