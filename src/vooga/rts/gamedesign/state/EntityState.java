package vooga.rts.gamedesign.state;

import vooga.rts.gamedesign.Interval;

/**
 * This class represents the state of the entity in the game.  For example,
 * this class will keep track of whether or not a unit can attack or can 
 * currently move.
 * 
 * @author Ryan Fishel
 * @author Kevin Oh
 * @author Francesco Agosti
 * @author Wenshun Liu
 *
 */

public class EntityState {
	
	private AttackingState myAttackingState;
	private OccupyState myOccupyState;
	private ProducingState myProducingState;
	private MovementState myMovementState;
	
	private Interval myAttackingInterval;
	
	private static final int DEFAULT_ATTAKING_INTERVAL = 25;
	
	/**
	 * Creates a new state for the entity with that is set to not attacking,
	 * not occupying, not producing, and stationary.  The interval for 
	 * attacking after moving is also set to the default attacking 
	 * interval
	 */
	public EntityState() {
		myAttackingState = AttackingState.NOT_ATTACKING;
		myOccupyState = OccupyState.NOT_OCCUPYING;
		myProducingState = ProducingState.NOT_PRODUCING;
		myMovementState = MovementState.STATIONARY;
		myAttackingInterval = new Interval(DEFAULT_ATTAKING_INTERVAL);
		myAttackingInterval.resetCooldown();
	}
	
	/**
	 * This method is used to set the attacking state (either attacking or
	 * not attacking) for the game entity.
	 * @param attackingState is the attacking state that the game entity will be set to
	 * 
	 */
	public void setAttackingState(AttackingState attackingState) {
		myAttackingState = attackingState;
	}
	
	/**
	 * This method is used to set the occupying state (either occupying
	 * or not occupying) for the game entity.
	 * @param occupyState is the occupying state that the game entity will be set to
	 */
	public void setOccupyState(OccupyState occupyState) {
		myOccupyState = occupyState;
	}
	
	/**
	 * This method is used to set the producing state (either producing or
	 * not producing) for the game entity.
	 * @param producingState is the producing state that the game entity will be set to
	 */
	public void setProducingState(ProducingState producingState) {
		myProducingState = producingState;
	}
	
	/**
	 * This method is used to set the movement state (either moving or 
	 * stationary) for the game entity.
	 * @param movementState is the movement state that the game entity will be set to
	 */
	public void setMovementState(MovementState movementState) {
		myMovementState = movementState;
	}
	
	/**
	 * Returns whether the entity is currently in the move state.
	 * @return true if the unit can move and false if the unit is stationary
	 */
	public boolean canMove() { 
		return myMovementState == MovementState.MOVING;
	}
	
	/**
	 * Returns whether the entity can attack.  In order to attack, the entity
	 * must be in the attacking state and have its attacking interval cooldown
	 * at 0.  The attacking interval cooldown makes it so that the unit must
	 * have a slight delay while it prepares to attack after stopping to move
	 * before it is able to attack.
	 * @return true if the entity can attack (in the attacking state and the 
	 * attacking interval cooldown is at 0 so the action is allowed) and false
	 * if the entity cannot attack
	 */
	public boolean canAttack() {
		return myAttackingState == AttackingState.ATTACKING && myAttackingInterval.allowAction();
	}
	
	/**
	 * Stops the movement of the entity.  This means that the entity's movement
	 * state is set to stationary.
	 */
	public void stop() {
		myMovementState = MovementState.STATIONARY;
	}
	
	/**
	 * Holds the entity.  This means that the entity is set to not attack and
	 * not move (it is set to stationary).
	 */
	public void hold() {
		myAttackingState = AttackingState.NOT_ATTACKING;
		myMovementState = MovementState.STATIONARY;
	}
	
	/**
	 * This method sets the entity to the attacking state.
	 */
	public void attack() {
		myAttackingState = AttackingState.ATTACKING;
	}
	/**
	 * This method is used to update the cooldown of the attacking interval.
	 * This interval is used to make the entity delay attacking after moving 
	 * so that the entity does not look "buggy" as it moves and shoots.  If the
	 * entity is moving, its cooldown is reset to the max cooldwow.  If the
	 * entity is not moving the cooldown gets decremented.
	 */
	public void update() {
		if(myMovementState == MovementState.MOVING) {
			myAttackingInterval.resetCooldown();
		}
		if(myAttackingState == AttackingState.ATTACKING) {
			myAttackingInterval.decrementCooldown();
		}
	}
}
