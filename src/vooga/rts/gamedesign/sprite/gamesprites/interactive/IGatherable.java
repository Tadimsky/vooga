package vooga.rts.gamedesign.sprite.gamesprites.interactive;

/**
 * 
 * This class specifies whether an object is gatherable.  If it is gatherable
 * then it will implement the getGathered method which will allow an oject
 * that can gather to gather it.  For example, a resource (like gold) will be
 * gatherable.
 * 
 * @author Ryan Fishel
 * @author Kevin Oh
 * @author Francesco Agosti
 * @author Wenshun Liu
 *
 */
public interface IGatherable {
	
	/**
	 * The IGatherable resource will get gathered by a worker.
	 * @param is the amount of resources that will be gathered
	 */
	public void getGathered(int playerID, int gatherAmount);


}
