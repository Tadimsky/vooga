package vooga.rts.gamedesign.sprite.gamesprites.interactive;

import vooga.rts.gamedesign.sprite.gamesprites.interactive.units.Unit;

/**
 * 
 * @author Ryan Fishel
 * @author Kevin Oh
 * @author Francesco Agosti
 * @author Wenshun Liu 
 *
 */
public interface IOccupiable {
	
	/**
	 * This method specifies which unit is going to occupy the occupiable
	 * object.  It calls that units occupy method.
	 * @param unit is the unit that will occupy the occupiable object
	 */
	public void getOccupied(Unit unit);
}