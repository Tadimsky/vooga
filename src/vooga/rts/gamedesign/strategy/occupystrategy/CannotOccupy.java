package vooga.rts.gamedesign.strategy.occupystrategy;

import vooga.rts.gamedesign.sprite.gamesprites.interactive.IOccupiable;


/**
 * 
 * This class implements OccupyStrategy and is used as an instance in 
 * interactives for objects that are not able to occupy IOccupiables.
 * 
 * @author Ryan Fishel
 * @author Kevin Oh
 * @author Francesco Agosti
 * @author Wenshun Liu 
 *
 */
public class CannotOccupy implements OccupyStrategy{

	@Override
	public boolean canOccupy(IOccupiable occupiable){
		return false;
	}

}
