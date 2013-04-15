package vooga.rts.gamedesign.strategy.attackstrategy;

import java.util.List;

import vooga.rts.gamedesign.Weapon;
import vooga.rts.gamedesign.sprite.rtsprite.IAttackable;
import vooga.rts.gamedesign.strategy.Strategy;


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

public interface AttackStrategy {
	
	/**
	 * Attacks the given IAttackable object by first judging whether the Weapon
	 * is in range for the attack action.
	 * @param a the IAttackable object being attacked.
	 * @param distance the distance between this and the IAttackable object.
	 */
	public void attack(IAttackable a, double distance);
	
	/**
	 * Returns the list of Weapon stored.
	 * @return the list of Weapon stored
	 */
	public List<Weapon> getWeapons();
	
	/**
	 * Returns the currently activated Weapon.
	 * @return the currently activated Weapon.
	 */
	public Weapon getCurrentWeapon();
	
	/**
	 * Adds a Weapon to the list of Weapons belonged to this AttackStrategy.
	 * @param weapon the new Weapon to be added into the list.
	 */
	public void addWeapons(Weapon weapon);
	
	/**
	 * Returns the index of the Weapon that's currently been activated in the
	 * list of Weapons belonged to this AttackStrategy.
	 * @return
	 */
	public int getWeaponIndex();
	
	/**
	 * Determines whether this AttackStrategy is able to attack.
	 * @return Whether this AttackStrategy is able to attack.
	 */
	public boolean getCanAttack();

}
