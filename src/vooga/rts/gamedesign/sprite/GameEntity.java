package vooga.rts.gamedesign.sprite;

import java.awt.Dimension;
import java.awt.Graphics2D;
import vooga.rts.util.Location;
import vooga.rts.util.Pixmap;
import vooga.rts.util.Sound;
import vooga.rts.util.Vector;
import vooga.rts.map.GameMap;
import vooga.rts.ai.Path;
import vooga.rts.ai.PathFinder;


/**
 * This class encompasses all classes that can affect the game directly through
 * specific behavior. This class has the health behavior (can �die�) and
 * velocity (can move and collide), and belong to a certain team. Setting the
 * health to zero sets the isVisible boolean to false, not allowing the
 * GameEntity to be painted.
 * 
 * @author Ryan Fishel
 * @author Kevin Oh
 * @author Francesco Agosti
 * @author Wenshun Liu
 * 
 */
public class GameEntity extends GameSprite {
    private Vector myVelocity;
    private GameMap myMap;
    private int myMaxHealth;
    private int myCurrentHealth;
    private PathFinder myFinder;
    private int myTeamID;
    private Path myPath;
    private Location myCurrentLocation;
    private Location myGoal;
    private Vector myOriginalVelocity;


    public GameEntity (Pixmap image, Location center, Dimension size, int teamID, int health) {
        super(image, center, size);
        myMaxHealth = health;
        myCurrentHealth = myMaxHealth;
        myTeamID = teamID;
        myCurrentLocation = new Location(center);
        // ALERT THIS IS JUST FOR TESTING
        myOriginalVelocity = new Vector(0, 0);
        myVelocity = new Vector(0, 0);
        myGoal = new Location();
    }

    /**
     * Returns shape's velocity.
     */
    public Vector getVelocity () {
        return myVelocity;
    }

    /**
     * Resets shape's velocity.
     */
    public void setVelocity (double angle, double magnitude) {
        myVelocity = new Vector(angle, magnitude);
    }

    public int getHealth () {
        return myCurrentHealth;
    }

    public void setHealth (int health) {
        myCurrentHealth = health;
    }

    /**
     * Returns the teamID the shape belongs to.
     */
    public int getTeamID () {
        return myTeamID;
    }

    /**
     * Rotates the Unit by the given angle.
     * 
     * @param angle
     */
    public void turn (double angle) {
        myVelocity.turn(angle);
    }

    /**
     * Translates the current center by vector v
     * 
     * @param v
     */
    public void translate (Vector v) {
       
        getCenter().translate(v);
        resetBounds();
    }

    /**
     * Reset shape back to its original values.
     */
    @Override
    public void reset () {
        super.reset();
        myVelocity = myOriginalVelocity;
    }

    /**
     * Moves the Unit only. Updates first the angle the Unit is facing,
     * and then its location.
     * Possible design choice error.
     */
    public void move (Location loc) {
        myGoal = new Location(loc);
        Vector v = getCenter().difference(myGoal);
        setVelocity(v.getAngle(),1);
    }
    
    public void move (Location loc, GameMap map) {
        setPath(loc, map);
    }
    
    public void setPath (Location location, GameMap map) {
        myPath = myFinder.calculatePath(map.getNode(getCenter()), 
                                        map.getNode(location), map.getMap());
        myGoal = myPath.getNext();
     }
    /**
     * Updates the shape's location.
     */
    // TODO: make Velocity three dimensional...
    public void update (double elapsedTime) {
        Vector v = new Vector(myVelocity);
        v.scale(elapsedTime);
        if(this.intersects(myGoal)){
            //System.out.println("myGoal reached");
            setVelocity(0,0);
        }
        else { 
            //System.out.println("Veclocity " + v.getAngle() + " " + v.getMagnitude());
            //this is kinda messed up right now
            translate(v);
        }
    }

    public void changeHealth (int change) {
        myCurrentHealth -= change;
    }

    /**
     * Checks to see if an GameEntity is dead.
     * 
     * @return true if the GameEntity has been killed and false if the GameEntity
     *         is still alive.
     */
    public boolean isDead () {
        return myCurrentHealth <= 0;
    }

    /**
     * Kills the GameEntity by setting its current health value to zero.	
     */
    public void die () {
        myCurrentHealth = 0;
    }

    @Override
    public void paint (Graphics2D pen) {
        if (!isDead()) {
            super.paint(pen);
        }
    }

}
