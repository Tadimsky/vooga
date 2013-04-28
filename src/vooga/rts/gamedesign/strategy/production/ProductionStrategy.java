package vooga.rts.gamedesign.strategy.production;

import java.awt.Graphics2D;
import java.util.List;

import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.buildings.Building;
import vooga.rts.gamedesign.state.ProducingState;
import vooga.rts.gamedesign.strategy.Strategy;
import vooga.rts.util.Location3D;

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
public interface ProductionStrategy extends Strategy{

	/** 
	 *  sets the rally point of this producer to rallypoint. 
	 */
	public void setRallyPoint(Location3D rallyPoint);
	
	/**
	 * Sets the rally point to be the location of the interactive entity. 
	 * @param entity
	 */
	public void setRallyPoint(InteractiveEntity entity);
	
	/**
	 * Creates all the actions that this strategy can accomplish. 
	 * @param producer
	 */
	public void createProductionActions(InteractiveEntity producer);

	/**
	 * Adds a producable entity to the list of producables this producer can make. 
	 */
	public void addProducable(InteractiveEntity producable);

	/**
	 * Returns a list of the entities this producer can make. 
	 * @return list of producables
	 */
	public List<InteractiveEntity> getProducables();
	
	/**
	 * Sets the list of producables to the parameter producables. 
	 * @param producables
	 */
	public void setProducables(List<InteractiveEntity> producables);

	
	/**
	 * Updates the strategy by updating the list of producables. 
	 * @param elapsedTime
	 */
	public void update (double elapsedTime);

	/**
	 * Paints all the interactive entities stored in this class. 
	 * @param pen
	 */
	public void paint (Graphics2D pen);
	
	/**
	 * Returns the production state
	 * @return
	 */
	public ProducingState getProducingState();

}