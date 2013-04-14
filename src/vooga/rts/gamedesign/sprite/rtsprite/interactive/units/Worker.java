package vooga.rts.gamedesign.sprite.rtsprite.interactive.units;

import java.awt.Dimension;

import vooga.rts.gamedesign.Interval;
import vooga.rts.gamedesign.sprite.GameEntity;
import vooga.rts.gamedesign.sprite.rtsprite.interactive.IGatherable;
import vooga.rts.gamedesign.strategy.gatherstrategy.CanGather;
import vooga.rts.gamedesign.strategy.gatherstrategy.CannotGather;
import vooga.rts.gamedesign.strategy.gatherstrategy.GatherStrategy;
import vooga.rts.util.Location;
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
	private static final int DEFUALT_GATHER_INTERVAL = 75;
	private int myGatherAmount;
	//TODO: find best place for interval
	
	/**
	 * Creates a new worker
	 * @param image is the image of the worker
	 * @param center is the location of the worker
	 * @param size is the size of the worker
	 * @param sound is the sound the worker will make
	 * @param teamID represents the team that the worker is on
	 * @param health is the health of the worker
	 */
	public Worker(Pixmap image, Location center, Dimension size, Sound sound,
			int teamID, int health, int gatherAmount) {
		super(image, center, size, sound, teamID, health);
		//interval = new Interval(DEFUALT_GATHER_INTERVAL);
		myGatherAmount = gatherAmount;
		myGatherStrategy = new CanGather(DEFUALT_GATHER_INTERVAL, gatherAmount);
	
	}

	@Override
	public void update(double elapsedTime) {
		myGatherStrategy.getInterval().decrementCooldown();
		super.update(elapsedTime);
	}

	/**
	 * The worker gathers the resource if it can and then resets its gather
	 * cooldown.
	 * @param g is the resource being gathered.
	 */
	public void gather(IGatherable g) {
		
		if(this.collidesWith((GameEntity)g)) {
			myGatherStrategy.gatherResource(g);
		}
	}
	
	public void setGatherAmount(int gatherAmount) {
		myGatherAmount = gatherAmount;
		myGatherStrategy = new CanGather(DEFUALT_GATHER_INTERVAL, myGatherAmount);
	}
}
