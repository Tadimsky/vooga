package vooga.rts.gamedesign.sprite.rtsprite;

import java.awt.Dimension;

import vooga.rts.gamedesign.sprite.GameEntity;
import vooga.rts.gamedesign.sprite.InteractiveEntity;
import vooga.rts.gamedesign.sprite.rtsprite.interactive.IGatherable;
import vooga.rts.util.Location;
import vooga.rts.util.Location3D;
import vooga.rts.util.Pixmap;

/**
 * This represents all resources that can be gathered by workers.
 * 
 * @author Ryan Fishel
 * @author Kevin Oh
 * @author Francesco Agosti
 * @author Wenshun Liu 
 *
 */

public class Resource extends GameEntity implements IGatherable {

	/**
	 * Creates a new resource
	 * @param image is the image of the resource
	 * @param center is the location of the resource
	 * @param size is the size of the resource
	 * @param playerID is the team the resource is on.  Will be set to 0 to 
	 * signify that the resource is not on anyone's team
	 * @param health is the value contained in the resource.  When this value
	 * becomes 0, the resource will disappear because it will not have any value
	 * left
	 */
	public Resource(Pixmap image, Location3D center, Dimension size, int playerID, int health) {
		super(image, center, size, playerID, health);
	}

	@Override
	public void getGathered(int gatherAmount) {
		changeHealth(gatherAmount);
		//TODO: Remove resource properly
		if(isDead()) {
			setVisible(false);
		}
	}
}