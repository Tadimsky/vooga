package vooga.rts.gamedesign.sprite.gamesprites;

import util.Location;
import vooga.rts.util.Location3D;

/**
 * 
 * @author Ryan Fishel
 * @author Kevin Oh
 * @author Francesco Agosti
 * @author Wenshun Liu 
 *
 */
public interface IMovable {

	/**
	 * Moves the Object to the given location.
	 * @param loc
	 */
	public void move(Location3D loc);
}