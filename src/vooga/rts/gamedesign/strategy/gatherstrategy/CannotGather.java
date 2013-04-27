package vooga.rts.gamedesign.strategy.gatherstrategy;

import vooga.rts.gamedesign.sprite.gamesprites.interactive.IGatherable;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.gamedesign.strategy.Strategy;

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

	public void gatherResource(int playerID, IGatherable gatherable) {
		return;
	}

	public void update(double elapsedTime) {
		return;
	}

	public int getGatherAmount() {
		return 0;
	}

	public void setGatherAmount(int gatherAmount) {
		return;
	}
	
	public void affect(InteractiveEntity other) {
		other.setGatherStrategy(this);
	}

}
