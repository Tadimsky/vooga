package vooga.rts.gamedesign.strategy.occupystrategy;

import vooga.rts.gamedesign.sprite.gamesprites.interactive.IOccupiable;

/**
 * 
 * This class implements OccupyStrategy and is used as an instance in 
 * interactives for objects that are able to occupy IOccupiables. The occupy 
 * method in this class will specify how the interactive will occupy the 
 * IOccupiable.
 * 
 * @author Ryan Fishel
 * @author Kevin Oh
 * @author Francesco Agosti
 * @author Wenshun Liu 
 *
 */
public class CanOccupy implements OccupyStrategy{

	@Override
	public boolean canOccupy(IOccupiable occupiable){
		return true;
	}
	

}
