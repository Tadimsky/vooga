package vooga.rts.gamedesign.strategy.attackstrategy;

import java.util.List;

import vooga.rts.gamedesign.sprite.gamesprites.IAttackable;
import vooga.rts.gamedesign.strategy.Strategy;
import vooga.rts.gamedesign.weapon.Weapon;
import vooga.rts.util.Location3D;


/**
 * 
 * This interface is implemented by the classes CanAttack and CannotAttack that
 * are then used as instance variables in the classes that could possibly
 * attack. If the unit currently can attack, it will have an instance of
 * CanAttack, otherwise it will have an instance of CannotAttack. Using the
 * strategy pattern like this, units and buildings ability to attack can be
 * dynamically changed. For example, a worker may be implemented such that
 * it can not attack until an upgrade is bought. The worker will initially
 * have an instance of CannotAttack but once the upgrade is bought it will
 * switch to have an instance of CanAttack.
 * 
 * @author Ryan Fishel
 * @author Kevin Oh
 * @author Francesco Agosti
 * @author Wenshun Liu
 * 
 * 
 */

public interface AttackStrategy extends Strategy{
	
	/**
	 * Attacks the given IAttackable object by first judging whether the Weapon
	 * is in range for the attack action.
	 * @param attackable the IAttackable object being attacked.
	 * @param distance the distance between this and the IAttackable object.
	 */
	public void attack(IAttackable attackable, double distance);
	
	
	/**
	 * Adds a Weapon to the list of Weapons belonged to this AttackStrategy.
	 * @param weapon the new Weapon to be added into the list.
	 */
	public void addWeapon(Weapon weapon);
	
	/**
	 * Changes the location of the weapon and of its projectile.
	 */
	public void setWeaponLocation(Location3D newLocation);
	
	/**
	 * Gets the weapon.
	 */
	public Weapon getCurrentWeapon();
	
	/**
	 * Allows the caller to know if this class has a weapon
	 */
	public boolean hasWeapon();

}
