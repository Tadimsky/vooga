package vooga.rts.gamedesign.strategy.gatherstrategy;

import vooga.rts.gamedesign.sprite.gamesprites.interactive.IGatherable;
import vooga.rts.gamedesign.strategy.Strategy;

/**
 * This interface is implemented by the classes CanGather and CannotGather that
 * are then used as instance variables in the classes that could potentially
 * gather resources. If the unit currently can gather, it will have an instance
 * of CanGather, otherwise it will have an instance of CannotGather. Using the
 * strategy pattern like this, interactives ability to gather can be dynamically
 * changed. For example, a unit may be implemented such that it cannot gather
 * resources until it hits a certain level. The unit will initially have an
 * instance of CannotGather but once the level is reached it will switch to have
 * an instance of CanGather.
 * 
 * @author Ryan Fishel
 * @author Kevin Oh
 * @author Francesco Agosti
 * @author Wenshun Liu
 * 
 */
public interface GatherStrategy extends Strategy {

	/**
	 * Allows a worker to gather a resource
	 * 
	 * @param gatherable
	 *            is the resource being gathered
	 * @param playerID is the team that the entity with this gather strategy is
	 * on
	 */

	public void gatherResource (int playerID, IGatherable gatherable);
	
	/**
	 * Updates the gather strategies so that the CanGather can update its 
	 * cooldown.
	 * @param elapsedTime is the amount of time that has passed
	 */
	public void update(double elapsedTime);
	
	/**
	 * Returns the amount of resource that this strategy can gather. 
	 * @return int gatheramount. 
	 */
	public int getGatherAmount();
	
	/**
	 * Sets the gather amount to a new gather amount. 
	 * @param gatherAmount
	 */
	public void setGatherAmount(int gatherAmount);

}