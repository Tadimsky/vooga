package vooga.rts.gamedesign.sprite.gamesprites.interactive.units;

import java.awt.Dimension;
import vooga.rts.gamedesign.sprite.gamesprites.GameEntity;
import vooga.rts.gamedesign.sprite.gamesprites.Resource;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.IGatherable;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.buildings.Building;
import vooga.rts.gamedesign.strategy.gatherstrategy.CanGather;
import vooga.rts.gamedesign.strategy.gatherstrategy.GatherStrategy;
import vooga.rts.gamedesign.strategy.production.IProducer;
import vooga.rts.util.Location3D;
import vooga.rts.util.Pixmap;
import vooga.rts.util.Sound;


/**
 * This class represents a worker who will be able to gather resources
 * 
 * @author Ryan Fishel
 * @author Wenshun Liu
 * 
 */
public class Worker extends Unit {

	private GatherStrategy myGatherStrategy;
	private IProducer myProducer;
	private static final int DEFUALT_GATHER_INTERVAL = 75;
	private int myGatherAmount;

	/**
	 * Creates a new worker
	 * 
	 * @param image
	 *            is the image of the worker
	 * @param center
	 *            is the location of the worker
	 * @param size
	 *            is the size of the worker
	 * @param sound
	 *            is the sound the worker will make
	 * @param playerID
	 *            represents the team that the worker is on
	 * @param health
	 *            is the health of the worker
	 */
	public Worker(Pixmap image, Location3D center, Dimension size, Sound sound,
			int playerID, int health, int gatherAmount) {
		super(image, center, size, sound, playerID, health);
		myGatherAmount = gatherAmount;
		myGatherStrategy = new CanGather(DEFUALT_GATHER_INTERVAL, gatherAmount);

	}

	@Override
	public void update(double elapsedTime) {
		super.update(elapsedTime);
		myGatherStrategy.update(elapsedTime);
	}

	/**
	 * Another recognize method specific for workers as they can gather
	 * resources which are not InteractiveEntities
	 */
	public void recognize(Resource resource) {
		gather(resource);
	}

	/**
	 * The worker gathers the resource if it can and then resets its gather
	 * cooldown.
	 * 
	 * @param gatherable
	 *            is the resource being gathered.
	 */
	public void gather(IGatherable gatherable) {
		// shouldnt the cast be to a type Resource?
		if (this.collidesWith((GameEntity) gatherable)) {
			myGatherStrategy.gatherResource(getPlayerID(), gatherable);
		}
	}

	/**
	 * Sets the amount that the worker can gather at a time.
	 * 
	 * @param gatherAmount
	 *            is the amount that the worker can gather
	 */
	public void setGatherAmount(int gatherAmount) {
		myGatherAmount = gatherAmount;
		myGatherStrategy = new CanGather(DEFUALT_GATHER_INTERVAL,
				myGatherAmount);
	}

}

