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
import vooga.rts.map.GameMap;
import vooga.rts.ai.Path;
import vooga.rts.ai.PathFinder;

/**
 * This class encompasses all classes that can affect the game directly through
 * specific behavior. This class has the health behavior (can “die”) and
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
    }
    
	/**
	 * Returns shape's velocity.
	 */
	public Vector getVelocity() {
		return myVelocity;
	}

	/**
	 * Resets shape's velocity.
	 */
	public void setVelocity(double angle, double magnitude) {
		myVelocity = new Vector(angle, magnitude);
	}

	/**
	 * Returns the current health of the entity.
	 * 
	 * @return the current health of the entity
	 */
	public int getHealth() {
		return myCurrentHealth;
	}

	public EntityState getState() {
		return myEntityState;
	}

	/**
	 * Sets the health of the entity.
	 * 
	 * @param health
	 *            is the amount of health the entity will have
	 */
	public void setHealth(int health) {
		myCurrentHealth = health;
	}

	/**
	 * Increases the max health of the entity.
	 * 
	 * @param health
	 *            is the amount of additional health the entity will get
	 */
	public void addMaxHealth(int health) {
		myMaxHealth += health;
	}

	/**
	 * Returns the max health of the entity.
	 * 
	 * @return the max health of the entity
	 */
	public int getMaxHealth() {
		return myMaxHealth;
	}

	/**
	 * Returns the teamID the shape belongs to.
	 */
	public int getPlayerID() {
		return myPlayerID;
	}

	/**
	 * Sets which team the entity will be on.
	 * 
	 * @param playerID
	 *            is the ID for the team that the entity is on
	 */
	public void setPlayerID(int playerID) {
		myPlayerID = playerID;
	}

	/**
	 * Rotates the Unit by the given angle.
	 * 
	 * @param angle
	 */
	public void turn(double angle) {
		myVelocity.turn(angle);
	}

	/**
	 * Specifies whether or not two entities collide.
	 * 
	 * @param gameEntity
	 *            is the entity that is being checked for a collision
	 * @return true if the bounds of the two entites intersect and false if the
	 *         bounds of the entities do not interesct
	 */
	public boolean collidesWith(GameEntity gameEntity) {
		return getBounds().intersects(gameEntity.getBounds());
	}

	/**
	 * Translates the current center by vector v
	 * 
	 * @param vector
	 */
	public void translate(Vector vector) {

		getWorldLocation().translate(vector);
		resetBounds();
	}

	/**
	 * Reset shape back to its original values.
	 */
	@Override
	public void reset() {
		super.reset();
		myVelocity = myOriginalVelocity;
	}

	/**
	 * Moves the Unit only. Updates first the angle the Unit is facing, and then
	 * its location. Possible design choice error.
	 */
	public void move(Location3D loc) {

		myEntityState.setMovementState(MovementState.MOVING);
		myGoal = new Location3D(loc);
		Vector v = getWorldLocation().difference(myGoal.to2D());
		// magic numero
		if (v.getMagnitude() < Location3D.APPROX_EQUAL) {
			setVelocity(v.getAngle(), 0);
		} else {
			setVelocity(v.getAngle(), getSpeed());
		}
	}

	/**
	 * Returns the speed of the entity.
	 * 
	 * @return the speed of the entity
	 */
	public int getSpeed() {
		return DEFAULT_SPEED;
	}

	/**
	 * This method is called to move the entity to a certain location.
	 * 
	 * @param loc
	 *            is the location where the entity will move to
	 * @param map
	 *            is the map that the game is being played on
	 */
	public void move(Location3D loc, GameMap map) {
		setPath(loc.to2D(), map);
	}

	/**
	 * Sets the path that the entity will move on.
	 * 
	 * @param location
	 *            is the location where the entity will move to
	 * @param map
	 *            is the map that the game is being played on
	 */
	public void setPath(Location location, GameMap map) {
		myPath = myFinder.calculatePath(map.getNode(getWorldLocation().to2D()),
				map.getNode(location), map.getMap());
		// myGoal = myPath.getNext();
	}

	/**
	 * Updates the shape's location.
	 */
	// TODO: make Velocity three dimensional...
	public void update(double elapsedTime) {

		if (getWorldLocation().near(myGoal)) {
			myEntityState.setMovementState(MovementState.STATIONARY);

		}
		move(myGoal);

		stopMoving();

		Vector v = new Vector(myVelocity);
		v.scale(elapsedTime);
		translate(v);
		myEntityState.update(elapsedTime);
		super.update(elapsedTime);
	}

	public void changeHealth(int change) {
		myCurrentHealth -= change;
	}

	/**
	 * Checks to see if an GameEntity is dead.
	 * 
	 * @return true if the GameEntity has been killed and false if the
	 *         GameEntity is still alive.
	 */
	public boolean isDead() {
		return myCurrentHealth <= 0;
	}

	/**
	 * Kills the GameEntity by setting its current health value to zero.
	 */
	public void die() {
		myCurrentHealth = 0;
	}

	@Override
	public void paint(Graphics2D pen) {
		if (!isDead()) {
			super.paint(pen);
		}
	}

	/**
	 * Returns the state of the entity such as its attacking state or movement
	 * state.
	 * 
	 * @return the state of the entity.
	 */
	public EntityState getEntityState() {
		return myEntityState;
	}
	
	/**
	 * If the entity is in a stationary state, it stops moving.
	 */
	public void stopMoving() {
		if (!myEntityState.canMove()) {
			// setVelocity(getVelocity().getAngle(), 0);
			setVelocity(0, 0);
		}
	}

}
