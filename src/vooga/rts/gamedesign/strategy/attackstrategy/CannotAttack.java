package vooga.rts.gamedesign.strategy.attackstrategy;

import java.util.ArrayList;
import java.util.List;

import vooga.rts.gamedesign.Weapon;
import vooga.rts.gamedesign.sprite.InteractiveEntity;
import vooga.rts.gamedesign.sprite.rtsprite.IAttackable;


/**
 * 
 * This class implements AttackStrategy and is used as an instance in
 * InteractiveEntity for objects that are not able to attack.
 * 
 * @author Ryan Fishel
 * @author Kevin Oh
 * @author Francesco Agosti
 * @author Wenshun Liu
 * 
 */

public class CannotAttack implements AttackStrategy {
	private List<Weapon> myWeapons; //TODO: probably won't need these
	private int myWeaponIndex;
	private boolean myCanAttack = false;

	public CannotAttack() {
		 myWeapons = new ArrayList<Weapon>();
		 myWeaponIndex = 0;
	}

	/**
	 * Attacks the given IAttackbleable object. In the state of CannotAttack
	 * does nothing to the IAttackable object.
	 */
	public void attack(IAttackable a, double distance) {
		return;
	}

	/**
	 * Returns the list of Weapon stored in this CanAttack object. In the state
	 * of CannotAttack returns a null list.
	 * @return a null list of Weapons
	 */
	public List<Weapon> getWeapons() {
		return null;
	}

	/**
	 * Returns the index of the Weapon that's currently been activated in the
	 * list of Weapons belonged to this CanAttack object. In the case of
	 * CannotAttack return -1, representing no Weapon has been activated.
	 * @return -1, representing no Weapon has been activated.
	 */
	public int getWeaponIndex() {
		return -1;
	}

	/**
	 * Adds a Weapon to the list of Weapons belonged to this AttackStrategy.
	 * @param weapon the new Weapon to be added into the list.
	 */
	public void addWeapons(Weapon weapon) {
		myWeapons.add(weapon);

	}

	/**
	 * Determines whether this CanAttack is able to attack.
	 * @return Whether this CanAttack is able to attack.
	 */
	public boolean getCanAttack() {
		return myCanAttack;
	}

	public Weapon getCurrentWeapon() {
		return null;
	}
}
