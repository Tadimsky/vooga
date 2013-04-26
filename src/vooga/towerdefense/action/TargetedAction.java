package vooga.towerdefense.action;

import java.util.ArrayList;
import java.util.List;

import vooga.towerdefense.gameElements.GameElement;

/**
 * Actions that require a target or multiple targets. 
 * 
 * @author XuRui
 *
 */

public class TargetedAction extends Action {

	private List<GameElement> myTargets;

	public TargetedAction(){
		super();
		setTargetTracking(true);
		myTargets = new ArrayList<GameElement>();
	}

	@Override
	public void executeAction(double elapsedTime) {
		// TODO Auto-generated method stub

	}

	/**
	 * Adds one game element to the current list of targets
	 * 
	 * @param target
	 */
	public void addTarget (GameElement target) {
		myTargets.add(target);
	}

	/**
	 * Adds a list of targets to the current targets of this action
	 * 
	 * @param targets
	 */
	public void addTargets (List<GameElement> targets) {
		myTargets.addAll(targets);
	}

	/**
	 * Sets the target list to a new list
	 * 
	 * @param newTargets
	 */
	public void setTargets (List<GameElement> newTargets) {
		myTargets = newTargets;
	}


	public List<GameElement> getTargets() {
		return myTargets;
	}
	

	/**
	 * Updates targeted follow up action accordingly, adds all targets to targeted actions by default.
	 * To be overridden if action wants to add targets in a different manner.
	 * 
	 * @param targets
	 */
    public void updateTargetedFollowUpActions(List<GameElement> targets){
    	for (TargetedAction a: getTargetedFollowUpActions()){
    		a.addTargets(targets);
    	}
    	
    }

	/**
	 * Adds each target to every single follow up action
	 * @param targets
	 */
	public void addOneTargetToAllFollowUpActions(List<GameElement> targets){
		for (GameElement target: targets){
			for (TargetedAction a: getTargetedFollowUpActions()){
				a.addTarget(target);
			}
		}

	}


}
