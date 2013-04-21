package vooga.rts.gamedesign;

/**
 * This class deals with cooldowns.  For example, after a weapon shoots, it 
 * needs to have a delay before it can shoot again.  This class will take
 * care of setting that delay and correctly decrementing the cooldown so that
 * the weapon can shoot at the correct time again.  Additionally, this class
 * will be used to handle the cooldown times for producing a unit or making
 * a building.
 * @author Ryan Fishel
 *
 */
@Deprecated
public class Interval {
	
	private int myMaxCooldown;
	private int myCooldown;
	
	/**
	 * Creates a default interval that has a very large value so that an 
	 * action using this interval will never be allowed.
	 */
	public Interval() {
		myMaxCooldown = Integer.MAX_VALUE;
		myCooldown = myMaxCooldown;
	}
	
	/**
	 * Creates a new interval with a max cooldown.
	 * @param cooldown is the initial max cooldwon of the interval
	 */
	public Interval (int cooldown) {
		myMaxCooldown = cooldown;
		myCooldown = 0;
	}
	
	/**
	 * Decreases the cooldown by 1 every time it is called if the cooldwon is
	 * not already at 0.
	 */
	public void decrementCooldown() {
		if(myCooldown != 0) {
			myCooldown--;
		}
	}
	
	/**
	 * Tells if the cooldown is over and an action is ready to be performed.
	 * For example, if the interval is being used for a weapon, if this is 
	 * true, is will signify that the weapon can shoot again.
	 * @return true if the cooldown is at 0 and false if it is above zero.
	 */
	public boolean allowAction() {
		return myCooldown <= 0;
	}
	
	/**
	 * Resets the cooldown to the max cooldown.
	 */
	public void resetCooldown() {
		myCooldown = myMaxCooldown;
	}
	
	/**
	 * Sets the max cooldown of the interval.  For example, if a weapon is
	 * upgraded to have a shorter cooldown time this method will be used
	 * to lower the max cooldown of the weapon
	 * @param maxCooldown is the new max cooldown of the interval.
	 */
	public void setMaxCooldown(int maxCooldown) {
		myMaxCooldown = maxCooldown;
	}
	/**
	 * Returns the current cooldown of the interval.
	 * @return the cooldown of the interval
	 */
	public int getCooldown() {
		return myCooldown;
	}
	
	/**
	 * Returns the percentage of the cooldown time remaining.
	 * @return the percentage of the cooldown to the max cooldown remaining
	 */
	public int getPercentageCooldownRemaining() {
		return (myCooldown/myMaxCooldown) * 100;
	}
}
