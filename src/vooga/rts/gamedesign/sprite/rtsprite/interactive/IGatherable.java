package vooga.rts.gamedesign.sprite.rtsprite.interactive;

import vooga.rts.gamedesign.strategy.gatherstrategy.GatherStrategy;

public interface IGatherable {
	
	/**
	 * The IGatherable resource will get gathered by a worker.
	 * @param is the amount of resources that will be gathered
	 */
	public void getGathered(int gatherAmount);


}
