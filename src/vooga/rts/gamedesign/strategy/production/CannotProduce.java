package vooga.rts.gamedesign.strategy.production;

import java.awt.Graphics2D;
import java.util.List;

import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.buildings.Building;
import vooga.rts.gamedesign.state.ProducingState;
import vooga.rts.gamedesign.strategy.Strategy;
import vooga.rts.util.Location3D;

/**
 * This class implements ProductionStrategy and is used as an instance in 
 * InteractiveEntity for objects that are not able to produce other
 * InteractiveEntity.  
 * 
 * @author Ryan Fishel
 * @author Kevin Oh
 * @author Francesco Agosti
 * @author Wenshun Liu 
 *
 */
public class CannotProduce implements ProductionStrategy{
	
	public void addProducable(InteractiveEntity producable) {
		return;
	}
	
	public void update(double elapsedTime) {
		return;
	}

	public void paint(Graphics2D pen) {
		return;
	}

	public void setRallyPoint(Location3D rallyPoint) {
		return;
	}

	public List<InteractiveEntity> getProducables() {
		return null;
	}

	public void setProducables(List<InteractiveEntity> producables) {
		return;
	}

	public void affect(InteractiveEntity entity) {
		entity.setProductionStrategy(this);
	}

	public void setRallyPoint(InteractiveEntity entity) {
		return;
	}

	@Override
	public ProducingState getProducingState() {
		return ProducingState.NOT_PRODUCING;
	}

}
