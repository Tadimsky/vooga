package vooga.rts.map;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.TreeMap;
import util.Location;
import vooga.rts.IGameLoop;
import vooga.rts.gamedesign.sprite.gamesprites.GameEntity;
import vooga.rts.gamedesign.sprite.gamesprites.GameSprite;
import vooga.rts.gamedesign.sprite.map.Terrain;
import vooga.rts.state.GameState;
import vooga.rts.state.MainState;
import vooga.rts.util.Location3D;


/**
 * This class is a basic manager for Game Sprites that will go on the map.
 * These will be things such as Terrains and Resources.
 * 
 * This class uses generics in order to store multiple types of specific GameSprites
 * 
 * @author Jonathan Schmidt
 * 
 */
public class GameSpriteManager<T extends GameSprite> implements IGameLoop, Observer {

    private List<T> myGameSprites;
    
    private Iterator<T> myRemoval;

    /**
     * Creates a new GameSpriteMananger
     */
    public GameSpriteManager () {
        myGameSprites = new ArrayList<T>();
    }

    @Override
    public void update (double elapsedTime) {
        for (T t : myGameSprites) {
            t.update(elapsedTime);
        }
    }

    @Override
    public void paint (Graphics2D pen) {
    }

    /**
     * Adds items to the manager. These items will then be observed by the NodeMap
     * and will be updated and painted every cycle.
     * 
     * @param gs The item to add
     */
    public void add (T gs) {
        gs.addObserver(GameState.getMap().getNodeMap());
        gs.setChanged();
        gs.notifyObservers(gs.getWorldLocation());
        myGameSprites.add(gs);
    }

    /**
     * Removes a particular item from the manager. This will stop it from being updated or painted.
     * 
     * @param gs The Terrain to remove
     */
    public void remove (T gs) {    
        if (gs == null) {
            return;
        }
        myGameSprites.remove(gs);
        gs.setChanged();
        gs.notifyObservers("remove");
    }
    
    private void removeIterator (T gs) {        
        myRemoval.remove();
        gs.setChanged();
        gs.notifyObservers("remove");
    }

    /**
     * Returns the first item at the provided world location
     * 
     * @param world The world location to look at
     * @return The Game Sprite at that position
     */
    public T getItem (Location3D world) {
        for (T t : myGameSprites) {
            if (t.intersects(world)) {
                return t;
            }
        }
        return null;
    }

    /**
     * Returns a list of all the items in this manager that are within a certain radius
     * of the provided location.
     * 
     * @param center The location to search from
     * @param radius The distance away from the location to select items
     * @return List of items that are in the area
     */
    public List<T> getInArea (Location3D center, double radius) {
        Map<Double, T> sortedItems = new TreeMap<Double, T>();
        for (T single : myGameSprites) {
            // Do basic test first
            if (single.getWorldLocation().getManhattanDistance(center) < radius) {
                // Do accurate test second
                double distance = single.getWorldLocation().getDistance(center);
                if (distance < radius) {
                    sortedItems.put(distance, single);
                }
            }
        }
        return new ArrayList<T>(sortedItems.values());
    }

    @Override
    public void update (Observable arg0, Object arg1) {
        if (arg0 instanceof GameEntity) {
            if (((GameEntity) arg0).isDead()) {
                remove((T) arg0);
            }
        }
    }

    /**
     * Returns the number of entities in the manager.
     */
    public int getSize () {
        return myGameSprites.size();
    }
    
    /**
     * Returns the contents of the Game Sprite Manager
     */
    public List<T> getContents() {
        return myGameSprites;
    }
    
    public void clearAll() {
        myRemoval = myGameSprites.iterator();
        while (myRemoval.hasNext()) {
            removeIterator(myRemoval.next());
        }
    }

    public List<T> getMySprites () {
        return myGameSprites;
    }

}
