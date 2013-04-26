package vooga.rts.gamedesign.strategy.attackstrategy;

import java.util.ArrayList;
import java.util.List;

import vooga.rts.gamedesign.sprite.gamesprites.IAttackable;
import vooga.rts.gamedesign.weapon.Weapon;
import vooga.rts.util.Location3D;


/**
 * 
 * This class implements AttackStrategy and is used as an instance in
 * InteractiveEntity for objects that are not able to attack.
 * 
 * @author Ryan Fishel
 * @author Kevin Oh
 * @author Francesco Agosti (did not have anything to do with this CannotAttack having weapons stuff)
 * @author Wenshun Liu
 * 
 */

public class CannotAttack implements AttackStrategy {
	
	/**
	 * Creates a new attack strategy that represents an entity that cannot attack.
	 */
	public CannotAttack() {
	}

	/**
	 * Attacks the given IAttackbleable object. In the state of CannotAttack
	 * does nothing to the IAttackable object.
	 */
	public void attack(IAttackable attackable, double distance) {
		return;
	}

	/**
	 * Returns the list of Weapon stored in this CanAttack object. In the state
	 * of CannotAttack returns a null list.
	 * @return a null list of Weapons
	 */
	public List<Weapon> getWeapons() {
		return new ArrayList<Weapon>();
	}


	/**
	 * Adds a Weapon to the list of Weapons belonged to this AttackStrategy.
	 * @param weapon the new Weapon to be added into the list.
	 */
	public void addWeapon(Weapon weapon) {
		return;

	}

	@Override
	public void setWeaponLocation(Location3D newLocation) {
		
	}

	@Override
	public Weapon getCurrentWeapon() {
		return null;
	}
	
    public boolean hasWeapon(){
    	return false;
    }
}
