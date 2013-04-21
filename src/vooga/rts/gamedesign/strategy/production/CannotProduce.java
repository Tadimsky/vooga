package vooga.rts.gamedesign.strategy.production;

import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;

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
public class CannotProduce implements IProducer{

    @Override
    public void createProductionActions (InteractiveEntity producer) {
        //this will do nothing. 
    }

}
