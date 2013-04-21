package vooga.rts.gamedesign.strategy.gatherstrategy;

import vooga.rts.gamedesign.Interval;
import vooga.rts.gamedesign.sprite.gamesprites.Resource;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.IGatherable;
import vooga.rts.gamedesign.state.GatherState;
import vooga.rts.util.DelayedTask;

/**
 * This class implements GatherStrategy and is used as an instance in
 * interactives for objects that are able to gather. This class holds the list
 * of resources the interactive gathers. And the gather method will specify how
 * the interactive will gather.
 * 
 * @author Ryan Fishel
 * @author Kevin Oh
 * @author Francesco Agosti
 * @author Wenshun Liu
 * 
 */
public class CanGather implements GatherStrategy {

	private DelayedTask myGatherDelay;
	private double myCooldown;
	private int myGatherAmount;
	private GatherState myGatherState;

	public CanGather(double cooldown, int gatherAmount) {
		myCooldown = cooldown;
		myGatherAmount = gatherAmount;
		myGatherState = GatherState.WAITING;
	}

	public void gatherResource(int playerID, IGatherable gatherable) {
		if(((Resource)gatherable).isDead()) {
			return;
		}
		final IGatherable toBeGathered = gatherable;
		final int id = playerID;
		if(myGatherState == GatherState.WAITING) {
			myGatherState = GatherState.GATHERING;
			System.out.println("I gathered!!!");
			myGatherDelay = new DelayedTask(myCooldown, new Runnable() {

				@Override
				public void run() {
					toBeGathered.getGathered(id, myGatherAmount);
					myGatherState = GatherState.WAITING;
				}
			});
		}

	}

	@Override
	public void update(double elapsedTime) {
		if(myGatherDelay != null) {
			myGatherDelay.update(elapsedTime);
		}
	}

}
