package vooga.towerdefense.factories.actionfactories;

import vooga.towerdefense.action.Action;
import vooga.towerdefense.action.waveactions.WaveAction;
import vooga.towerdefense.factories.elementfactories.GameElementFactory;
import vooga.towerdefense.gameElements.GameElement;
import vooga.towerdefense.model.GameMap;

/**
 * This action factory builds a wave action.
 * 
 * @author Zhen Gou
 *
 */
public class WaveActionFactory extends ActionFactory {

	private int myNumUnits;
	private int myCooldown;
	private GameElementFactory myFactory;
	private GameMap myMap;
	
	public WaveActionFactory(int numUnits, int cooldown, GameElementFactory factory, GameMap map) {
		myNumUnits = numUnits;
		myCooldown = cooldown;
		myFactory = factory;
		myMap = map;
		
	}
	
	@Override
	protected Action buildAction(GameElement e) {
		return new WaveAction(myNumUnits, myCooldown, myFactory, myMap);
	}
}
