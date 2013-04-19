package vooga.rts.gamedesign.sprite.gamesprites.interactive;

public interface IGatherable {
	
	/**
	 * The IGatherable resource will get gathered by a worker.
	 * @param is the amount of resources that will be gathered
	 */
	public void getGathered(int playerID, int gatherAmount);


}
