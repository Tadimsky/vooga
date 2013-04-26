package vooga.towerdefense.factories.actionfactories;

import vooga.towerdefense.action.Action;
import vooga.towerdefense.action.actionlist.FollowPath;
import vooga.towerdefense.gameElements.GameElement;


/**
 * Factory for creating FollowPath action, to create followPath actions of different path
 * need different instances of this factory.
 * 
 * Design tradeoff: seems bad to need different followpath factory for different paths
 * however, we don't  pass in Path in run time because we need to override buildAction method
 * 
 * @author Zhen Gou
 *
 */

public class FollowPathFactory extends ActionFactory{
	
	@Override
	protected Action buildAction(GameElement elementToMove) {
		return new FollowPath(elementToMove,getMap().getShortestPath(elementToMove.getCenter(), getMap().getDestination()));
		
	}
	
	
}
