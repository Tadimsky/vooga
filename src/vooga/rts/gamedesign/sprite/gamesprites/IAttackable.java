package vooga.rts.gamedesign.sprite.gamesprites;


/**
 * This interface represents all things that can be attacked.  For example, 
 * buildings and units would implement this interface since they can be
 * attacked
 * 
 * @author Ryan Fishel
 * @author Kevin Oh
 * @author Francesco Agosti
 * @author Wenshun Liu 
 *
 */
public interface IAttackable {

	/**
	 * Calculates the damage that should be done to the IAttackable based on
	 * armor
	 * @return the amount of damage that will be done to the IAttackable
	 */
    public int calculateDamage(int damage);
    
    /**
     * Changes the health of an IAttackable after calculating the amount of 
     * damage that will be done
     * @param change is the amount that the health will be changed by
     */
    public void changeHealth(int change);

}