package vooga.towerdefense.action.waveactions;

import vooga.towerdefense.action.Action;
import vooga.towerdefense.factories.elementfactories.GameElementFactory;
import vooga.towerdefense.gameElements.GameElement;
import vooga.towerdefense.model.GameMap;

public class WaveAction extends Action {

	private int myUnitsRemaining;
	private int myCooldown;
	private GameElementFactory myFactory;
	private int myClock;
	private GameMap myMap;
	
	public WaveAction(int numUnits, int cooldown, GameElementFactory factory, GameMap gameMap) {
		myUnitsRemaining = numUnits;
		myCooldown = cooldown;
		myFactory = factory;
		myClock = 0;
		myMap = gameMap;
	}
	@Override
	public void executeAction(double elapsedTime) {
		if(myClock > myCooldown) {
			spawnUnit();
			myClock = 0;
		}
		myClock += elapsedTime;
	}
	
	private void spawnUnit() {
		GameElement unit = myFactory.createElement(myMap.getSpawnLocation());
		myMap.addGameElement(unit);
	}

}
