package vooga.rts.gamedesign.sprite.gamesprites;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

import vooga.rts.gamedesign.sprite.gamesprites.interactive.units.Unit;
import vooga.rts.gamedesign.state.AttackingState;
import vooga.rts.gamedesign.state.EntityState;
import vooga.rts.gamedesign.state.MovementState;
import vooga.rts.gamedesign.state.OccupyState;
import vooga.rts.gamedesign.state.ProducingState;
import vooga.rts.gamedesign.strategy.occupystrategy.CannotBeOccupied;
import vooga.rts.gamedesign.strategy.occupystrategy.OccupyStrategy;
import vooga.rts.util.Camera;
import vooga.rts.util.Location;
import vooga.rts.util.Location3D;
import vooga.rts.util.Pixmap;
import vooga.rts.util.Sound;
import vooga.rts.util.Vector;
import vooga.rts.manager.GameUnitManager;
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
    // Default velocity magnitude
    public static int DEFAULT_SPEED = 0;
    private Vector myVelocity;
    private GameMap myMap;
    private int myMaxHealth;
    private int myCurrentHealth;
    private PathFinder myFinder;
    private int myPlayerID;
    private Path myPath;
    private Location3D myGoal;
    private Vector myOriginalVelocity;
    private EntityState myEntityState;
    private OccupyStrategy myOccupyStrategy;
    private static GameUnitManager myGameUnitManager;

    public GameEntity (Pixmap image, Location3D center, Dimension size, int playerID, int health) {
        super(image, center, size);
        myMaxHealth = health;
        myCurrentHealth = myMaxHealth;
        myPlayerID = playerID;
        // ALERT THIS IS JUST FOR TESTING
        myOriginalVelocity = new Vector(0, 0);
        myVelocity = new Vector(0, 0);
        myGoal = new Location3D();
        myEntityState = new EntityState();
        myOccupyStrategy = new CannotBeOccupied();
    }

    /**
     * CONSTRUCTOR FOR TESTIGN PURPOSE
     */
    public GameEntity() {
		this(null,null,null,1,10);
	}
    
	public void setGameUnitManager(GameUnitManager gameUnitManager) {
		myGameUnitManager = gameUnitManager;
	}
	
	public GameUnitManager getGameUnitManager() {
		return myGameUnitManager;
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

    public void addMaxHealth (int health) {
        myMaxHealth += health;
    }

    public int getMaxHealth () {
        return myMaxHealth;
    }

    /**
     * Returns the teamID the shape belongs to.
     */
    public int getPlayerID () {
        return myPlayerID;
    }
    
    public void setPlayerID(int playerID) {
    	myPlayerID = playerID;
    }

    /**
     * Rotates the Unit by the given angle.
     * 
     * @param angle
     */
    public void turn (double angle) {
        myVelocity.turn(angle);
    }

    public boolean collidesWith (GameEntity gameEntity) {
        return getBounds().intersects(gameEntity.getBounds());
    }

    /**
     * Translates the current center by vector v
     * 
     * @param vector
     */
    public void translate (Vector vector) {

        getWorldLocation().translate(vector);
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
    public void move (Location3D loc) {
        myEntityState.setMovementState(MovementState.MOVING);
        myGoal = new Location3D(loc);
        Vector v = getWorldLocation().difference(myGoal.to2D());
        // TODO: not static amount
        setVelocity(v.getAngle(), getSpeed());
    }

    public int getSpeed () {
        return DEFAULT_SPEED;
    }

    public void move (Location3D loc, GameMap map) {
        setPath(loc.to2D(), map);
    }

    public void setPath (Location location, GameMap map) {
        myPath =
                myFinder.calculatePath(map.getNode(getWorldLocation().to2D()),
                                       map.getNode(location), map.getMap());
        // myGoal = myPath.getNext();
    }

    /**
     * Updates the shape's location.
     */
    // TODO: make Velocity three dimensional...
    public void update (double elapsedTime) {

        if (getWorldLocation().near(myGoal)) {
            myEntityState.setMovementState(MovementState.STATIONARY);
            
        }
        //move(myGoal);
        
        stopMoving();
        
        Vector v = new Vector(myVelocity);
        v.scale(elapsedTime);
        translate(v);
        myEntityState.update();
    }

    public void changeHealth (int change) {
        myCurrentHealth -= change;
    }
    
    public void getOccupied(Unit occupier) {
    	if (occupier.collidesWith(this)) {
    		myOccupyStrategy.getOccupied(this, occupier);
    	}
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
    public void paintHelper (Graphics2D pen) {
        if (!isDead()) {
            super.paintHelper(pen);
        }
    }

    /**
     * Returns the state of the entity such as its attacking state or movement
     * state.
     * 
     * @return the state of the entity.
     */
    public EntityState getEntityState () {
        return myEntityState;
    }
    
    public OccupyStrategy getOccupyStrategy() {
    	return myOccupyStrategy;
    }
    
    public void setOccupyStrategy(OccupyStrategy occupyStrategy) {
    	myOccupyStrategy = occupyStrategy;
    }

    /**
     * If the entity is in a stationary state, it stops moving.
     */
    public void stopMoving () {
        if (!myEntityState.canMove()) {
            setVelocity(getVelocity().getAngle(), 0);
        }
    }

}
