package vooga.rts.gamedesign.strategy.gatherstrategy;

import vooga.rts.gamedesign.Interval;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.IGatherable;
import vooga.rts.util.DelayedTask;

/**
 * 
 * This class implements GatherStrategy and is used as an instance in
 * interactives for objects that are not able to gather resources.
 * 
 * @author Ryan Fishel
 * @author Kevin Oh
 * @author Francesco Agosti
 * @author Wenshun Liu
 * 
 */

public class CannotGather implements GatherStrategy {

	@Override
	public void gatherResource(int playerID, IGatherable gatherable) {
	}

	@Override
	public void update(double elapsedTime) {
	}

}
