package vooga.towerdefense.factories.waveactionfactories;

import java.util.Map;

import vooga.towerdefense.action.Action;
import vooga.towerdefense.action.waveactions.WaveAction;
import vooga.towerdefense.factories.actionfactories.ActionFactory;
import vooga.towerdefense.factories.elementfactories.GameElementFactory;
import vooga.towerdefense.gameelements.GameElement;

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
	public static Map<String, GameElementFactory> ourGEFactories; 
	
	public WaveActionFactory(String numUnits, String cooldown, String factory) {
		myNumUnits = Integer.parseInt(numUnits);
		myCooldown = Integer.parseInt(cooldown);
		myFactory = ourGEFactories.get(factory);		
		
	}
	
	@Override
	protected Action buildAction(GameElement e) {
		return new WaveAction(myNumUnits, myCooldown, myFactory, getMap());
	}
}
