package vooga.rts.gamedesign.strategy.gatherstrategy;

import vooga.rts.gamedesign.Interval;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.IGatherable;
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

	public CanGather(double cooldown, int gatherAmount) {
		myCooldown = cooldown;
		myGatherAmount = gatherAmount;
	}

	public void gatherResource(int playerID, IGatherable gatherable) {
		final IGatherable toBeGathered = gatherable;
		final int id = playerID;
		myGatherDelay = new DelayedTask(myCooldown, new Runnable() {

			@Override
			public void run() {
				toBeGathered.getGathered(id, myGatherAmount);
			}
		});

	}

	@Override
	public void update(double elapsedTime) {
		myGatherDelay.update(elapsedTime);
	}

}
