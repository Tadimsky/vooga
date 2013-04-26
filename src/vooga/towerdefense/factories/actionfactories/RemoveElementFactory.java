package vooga.towerdefense.factories.actionfactories;

import vooga.towerdefense.action.Action;
import vooga.towerdefense.action.actionlist.RemoveGameElement;
import vooga.towerdefense.gameElements.GameElement;

/**
 * This is an action factory that builds RemoveElement actions.
 * @author Zhen Gou
 *
 */
public class RemoveElementFactory extends ActionFactory {
	
	public RemoveElementFactory(){
	}
	
	@Override
	public Action buildAction(GameElement element){
		
		return new RemoveGameElement(getMap(), element);
	}



}
