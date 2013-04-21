package vooga.rts.gamedesign.strategy.production;

import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;

/**
 * 
 * This interface is implemented by the classes Producer and Cannotproduce that
 * are then used as instance variables in the classes that could possibly
 * produce other interactives.  If the unit currently can produce, it will have 
 * an instance of Producer, otherwise it will have an instance of CannotProduce.  
 * Using the strategy pattern like this, interactives ability to produce can be
 * dynamically changed.  For example, a tank may be implemented such that 
 * it can not produce other interactives until an upgrade is bought.  The tank 
 * will initially have an instance of Cannotproduce but once the upgrade is 
 * bought it will switch to have an instance of producer.
 * 
 * @author Ryan Fishel
 * @author Kevin Oh
 * @author Francesco Agosti
 * @author Wenshun Liu 
 *
 */
public interface IProducer {

  /** 
   *  requires a timer for cooldown for production 
   */
  public void createProductionActions(InteractiveEntity producer);

}