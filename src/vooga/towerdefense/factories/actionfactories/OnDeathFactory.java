package vooga.towerdefense.factories.actionfactories;

import vooga.towerdefense.action.Action;
import vooga.towerdefense.action.response.OnDeath;
import vooga.towerdefense.gameelements.GameElement;

/**
 * This is an action factory for creating OnDeath actions.
 * 
 * @author Zhen Gou
 * 
 */

public class OnDeathFactory extends ActionFactory {

	public OnDeathFactory() {
		super();
	}

	/**
	 * Builds an OnDeath action for element
	 */

	@Override
	public Action buildAction(GameElement element) {
		Action action = new OnDeath(element);
		return action;
	}
}
