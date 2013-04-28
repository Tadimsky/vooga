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
 * interactives for objects that are not able to produce other interactives.  
 * 
 * @author Ryan Fishel
 * @author Kevin Oh
 * @author Francesco Agosti
 * @author Wenshun Liu 
 *
 */
public class CannotProduce implements ProductionStrategy{

    @Override
    public void createProductionActions (InteractiveEntity producer) {
        //this will do nothing. 
    }

	public void addProducable(InteractiveEntity producable) {
		return;
	}

	@Override
	public void update(double elapsedTime) {
		// TODO Auto-generated method stub
		
	}

	public void paint(Graphics2D pen) {
		return;
	}

	public void setRallyPoint(Location3D rallyPoint) {
		return;
	}

	public void setRallyPoint(InteractiveEntity entity) {
		return;
	}

	@Override
	public List<InteractiveEntity> getProducables() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProducables(List<InteractiveEntity> producables) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void affect(InteractiveEntity entity) {
		entity.setProductionStrategy(this);
	}

	@Override
	public ProducingState getProducingState() {
		return ProducingState.NOT_PRODUCING;
	}

}
