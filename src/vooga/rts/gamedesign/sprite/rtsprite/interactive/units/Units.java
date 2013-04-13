package vooga.rts.gamedesign.sprite.rtsprite.interactive.units;

import vooga.rts.gamedesign.Weapon;
import vooga.rts.ai.AstarFinder;
import vooga.rts.ai.Path;
import vooga.rts.ai.PathFinder;
import vooga.rts.gamedesign.sprite.rtsprite.IMovable;
import vooga.rts.gamedesign.sprite.rtsprite.RTSprite;
import vooga.rts.gamedesign.sprite.rtsprite.interactive.Interactive;
import vooga.rts.gamedesign.strategy.gatherstrategy.GatherStrategy;
import vooga.rts.gamedesign.strategy.occupystrategy.OccupyStrategy;
import vooga.rts.map.GameMap;
import vooga.rts.map.Node;
import vooga.rts.util.Location;
import vooga.rts.util.Pixmap;
import vooga.rts.util.Sound;
import vooga.rts.util.Vector;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;


/**
 * This is an abstract class that represents a unit. It will be extended
 * by specific types of units such as soldiers.
 * 
 * @author Ryan Fishel
 * @author Kevin Oh
 * @author Francesco Agosti
 * @author Wenshun Liu
 * 
 */
public abstract class Units extends Interactive implements IMovable {

    private List<Interactive> myKills;
    // private boolean myIsLeftSelected; // TODO: also need the same thing for Projectiles
    // private boolean myIsRightSelected; // TODO: should be observing the mouse action instead!!
    private Path myPath;
    private PathFinder myFinder;
    private Location myGoal;

    /**
     * Creates a new unit with an image, location, size, sound, teamID and health
     * 
     * @param image is the image of the unit
     * @param center is the position of the unit on the map
     * @param size is the size of the unit
     * @param sound is the sound the unit makes
     * @param teamID is the ID for the team that the unit is on
     * @param health is the max health of the unit
     */
    public Units (Pixmap image, Location center, Dimension size, Sound sound, int teamID, int health) {
        super(image, center, size, sound, teamID, health);
        //myPather = new PathingHelper();
        myGoal = new Location(center);
        Queue<Node> path = new LinkedList<Node>();
        myPath = new Path(path);
    }


    /**
     * Moves the Unit only when it is selected. Updates first the angle
     * the Unit is facing, and then its location.
     */
    public void move (Location loc) {
        myGoal = new Location(loc);
    }
    
    public void move (Location loc, GameMap map) {
        setPath(loc, map);
    }

    public void update (double elapsedTime) {
        Vector diff = getCenter().difference(myGoal);
        if (diff.getMagnitude() > 5) {
            if (myPath.size() > 0) {
                myGoal = myPath.getNext();
            }
            double angle = diff.getDirection();
            double magnitude = 100;            
            setVelocity(angle, magnitude);
        }
        else
        {
            setVelocity(0, 0);
        }
        super.update(elapsedTime);
    }

    /**
     * Rotates the Unit by the given angle.
     * 
     * @param angle
     */
    public void turn (double angle) {
        getVelocity().turn(angle);
    }

    public void setPath (Location location, GameMap map) {
       myPath = myFinder.calculatePath(map.getNode(getCenter()), 
                                       map.getNode(location), map.getMap());
       myGoal = myPath.getNext();
    }

    /*
     * public void move () {
     * if (myPather.size() == 0) {
     * return;
     * }
     * Location next = myPather.getNext(getCenter());
     * double angle = getCenter().difference(next).getDirection();
     * turn(angle);
     * setVelocity(angle, mySpeed);
     * }
     */

}
