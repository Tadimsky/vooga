package vooga.rts.gamedesign.strategy.production;

import java.awt.Graphics2D;

import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
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

	@Override
	public void addProducable(InteractiveEntity producable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(double elapsedTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paint(Graphics2D pen) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRallyPoint(Location3D rallyPoint) {
		// TODO Auto-generated method stub
		
	}

}
