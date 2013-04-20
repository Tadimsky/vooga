package vooga.rts.gamedesign.strategy.production;

import vooga.rts.gamedesign.factories.Factory;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.buildings.ProductionBuilding;

import java.util.List;


/**
 * This class implements ProductionStrategy and is used as an instance in
 * interactives for objects that are able to produce other interactives.
 * The produce method in this class will specify how the interactive will
 * produce other units.
 * 
 * @author Ryan Fishel
 * @author Kevin Oh
 * @author Francesco Agosti
 * @author Wenshun Liu
 * 
 */
public class CanProduce implements IProducer {

    public List<InteractiveEntity> myProducables;

    public Integer cooldown;

    public Factory myFactory;
    
    @Override
    public void produce (InteractiveEntity producer) {
        if(producer instanceof ProductionBuilding) {
            for(InteractiveEntity producable : ((ProductionBuilding) producer).getProducables()) {
                //producer.addAction
            }
        }
    }


}
