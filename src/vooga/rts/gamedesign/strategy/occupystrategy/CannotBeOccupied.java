package vooga.rts.gamedesign.strategy.occupystrategy;

import java.util.ArrayList;
import java.util.List;

import vooga.rts.action.InteractiveAction;
import vooga.rts.commands.Command;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.units.Unit;
import vooga.rts.gamedesign.strategy.Strategy;


/**
 * 
 * This class implements OccupyStrategy and is used as an instance in 
 * interactives for objects that are not able to be occupied.
 * 
 * @author Ryan Fishel
 * @author Kevin Oh
 * @author Francesco Agosti
 * @author Wenshun Liu 
 *
 */
public class CannotBeOccupied implements OccupyStrategy{

	public List<Integer> getOccupiers() {
		return null;
	}

	public void createOccupyActions(InteractiveEntity entity) {
		return;
	}

	public void getOccupied(InteractiveEntity entity, Unit u) {
		return;
	}

	public void affect(InteractiveEntity entity) {
		entity.setOccupyStrategy(this);
	}


}
