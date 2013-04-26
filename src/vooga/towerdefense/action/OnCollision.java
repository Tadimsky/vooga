package vooga.towerdefense.action;

import vooga.towerdefense.gameElements.GameElement;


/**
 * Defines actions on collision. 
 * 
 * @author XuRui
 *
 */
public class OnCollision extends TargetedAction{
	
	private GameElement myInitiator;

	public OnCollision(GameElement initiator){
		myInitiator = initiator;
	}
	

	@Override 
	public void update(double elapsedTime){
		if (collisionDetected()){
			executeAction(elapsedTime);
		}
	}
	@Override
	public void executeAction(double elapsedTime) {
		updateFollowUpActions(elapsedTime);
		
	}
	
	/**
	 * Checks if collision with any of the valid targets is detected 
	 * @return
	 */
	public boolean collisionDetected(){
		for (GameElement target: getTargets()){
			if (myInitiator.intersects(target)){
				return true;
			}
		}
		return false;
		
	}
}
