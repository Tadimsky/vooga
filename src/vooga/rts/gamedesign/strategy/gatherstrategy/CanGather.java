package vooga.rts.gamedesign.strategy.gatherstrategy;

import vooga.rts.gamedesign.Interval;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.IGatherable;

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

	private Interval interval;
	private int myGatherAmount;

	public CanGather(int cooldown, int gatherAmount) {
		interval = new Interval(cooldown);
		myGatherAmount = gatherAmount;
	}

	public void gatherResource(IGatherable gatherable) {
		if (interval.allowAction()) {
			gatherable.getGathered(myGatherAmount);
			interval.resetCooldown();
		}
	}

	@Override
	public Interval getInterval() {
		return interval;
	}

}
