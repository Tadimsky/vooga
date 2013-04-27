package vooga.rts.gamedesign.strategy.gatherstrategy;

import vooga.rts.gamedesign.sprite.gamesprites.Resource;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.IGatherable;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.gamedesign.state.GatherState;
import vooga.rts.util.DelayedTask;

/**
 * This class implements GatherStrategy and is used as an instance in
 * InteractiveEntity for objects that are able to gather. The gather action is
 * performed is the form of "delayed" tasks. This class also stores information
 * such as the cool down time between gather actions, and the amount can be
 * gathered per time.
 * 
 * @author Ryan Fishel
 * @author Kevin Oh
 * @author Francesco Agosti
 * @author Wenshun Liu
 * 
 */
public class CanGather implements GatherStrategy {
	
	public static final double DEFAULTCOOL = 10;
	public static final int DEFAULTAMOUNT = 10;

	private DelayedTask myGatherDelay;
	private double myCooldown;
	private int myGatherAmount;
	private GatherState myGatherState;
	
	/**
	 * Creates a new gather strategy that represents a unit that can gather 
	 * resources.This strategy is created with a cooldown time between 
	 * gathers, how many resources it can gather at a time and is set
	 * to be waiting to gather (as its state).
	 * @param cooldown is the cooldown time between gathers
	 * @param gatherAmount is the amount that can be gathered at a time
	 */
	public CanGather(double cooldown, int gatherAmount) {
		myCooldown = cooldown;
		myGatherAmount = gatherAmount;
		myGatherState = GatherState.WAITING;
	}
	
	public CanGather(){
		this(DEFAULTCOOL, DEFAULTAMOUNT);
	}
	
	/**
	 * Allows the entity to gather a specific resource (a IGatherable object)
	 * 
	 */
	public void gatherResource(int playerID, IGatherable gatherable) {
		if(((Resource)gatherable).isDead()) {
			return;
		}
		final IGatherable toBeGathered = gatherable;
		final int id = playerID;
		if(myGatherState == GatherState.WAITING) {
			myGatherState = GatherState.GATHERING;
			myGatherDelay = new DelayedTask(myCooldown, new Runnable() {

				@Override
				public void run() {
					toBeGathered.getGathered(id, myGatherAmount);
					myGatherState = GatherState.WAITING;
				}
			});
		}
	}

	public void update(double elapsedTime) {
		if(myGatherDelay != null) {
			myGatherDelay.update(elapsedTime);
		}
	}

	public int getGatherAmount() {
		return myGatherAmount;
	}

	public void setGatherAmount(int gatherAmount) {
		myGatherAmount = gatherAmount;
	}

	/**
     * Applies this CanGather strategy to the InteractiveEntity passed in by
     * setting the strategy for the InteractiveEntity.
     * 
     * @param other the InteractiveEntity that will receive the effect of
     * this GatherStrategy
     */
	public void affect(InteractiveEntity other) {
		other.setGatherStrategy(this);
	}

}
