package vooga.towerdefense.factories.actionfactories;

import vooga.towerdefense.action.Action;
import vooga.towerdefense.action.actionlist.LaunchProjectile;
import vooga.towerdefense.gameElements.GameElement;

/**
 * This is an action factory that builds a LaunchProjectile action.
 * 
 * @author XuRui
 *
 */

public class LaunchProjectileFactory extends ActionFactory {
		
	private String myFactoryName;
	
	/**
	 * constructor
	 * @param factory
	 */
	public LaunchProjectileFactory(String factory){
		myFactoryName = factory;
	}

	/**
	 * Builds a LaunchProjectile action with GameElementFactory already added into corresponding attribute manager.
	 */
	@Override
	protected Action buildAction(GameElement e) {
		return new LaunchProjectile(getMap(), e.getCenter(), e.getAttributeManager().getGameElementFactory(myFactoryName));
	}
	

}
