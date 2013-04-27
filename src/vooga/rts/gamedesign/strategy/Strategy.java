package vooga.rts.gamedesign.strategy;

import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;


/**
 * This interface acts as the common interface for all strategies.
 * Acts as a tag so that all strategies can be placed in the same collection.
 * @author Francesco Agosti
 *
 */
public interface Strategy {
	
	
	/**
	 * This will give the interactive entity the traits that go along with this strategy. 
	 * 
	 * @return a copy of the strategy
	 */
	public void affect(InteractiveEntity other);

}
