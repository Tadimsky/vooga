package vooga.rts.map;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import vooga.rts.IGameLoop;
import vooga.rts.gamedesign.sprite.gamesprites.GameSprite;
import vooga.rts.gamedesign.sprite.map.Terrain;
import vooga.rts.state.GameState;
import vooga.rts.state.MainState;
import vooga.rts.util.Location3D;


/**
 * This class is a basic manager for Game Sprites that will go on the map.
 * These will be things such as Terrains and Resources.
 * 
 * This class uses generics in order to store mutliple ti
 * 
 * @author Jonathan Schmidt
 * 
 */
public class GameSpriteManager<T extends GameSprite> extends Observable implements IGameLoop {

    private List<T> myGameSprites;

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
        for (T t : myGameSprites) {
            t.paint(pen);
        }
    }

    /**
     * Adds items to the manager. These items will then be observed by the NodeMap
     * and will be updated and painted every cycle.
     * 
     * @param gs The item to add
     */
    public void add (T gs) {
        gs.addObserver(GameState.getMap().getNodeMap());
        myGameSprites.add(gs);
    }

    /**
     * Removes a particular item from the manager. This will stop it from being updated or painted.
     * 
     * @param gs The Terrain to remove
     */
    public void remove (T gs) {
        myGameSprites.remove(gs);
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
        List<T> items = new ArrayList<T>();
        for (T single : myGameSprites) {
            // Do basic test first
            if (single.getWorldLocation().getManhattanDistance(center) < radius) {
                // Do accurate test second
                if (single.getWorldLocation().getDistance(center) < radius) {
                    items.add(single);
                }
            }
        }
        return items;
    }

}
