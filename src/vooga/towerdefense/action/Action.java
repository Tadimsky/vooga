package vooga.towerdefense.action;

import java.util.ArrayList;
import java.util.List;

/**
 * An Action is the super class of all actions that can be executed by game elements.
 * This includes attacks, upgrades, path creation and anything the developer may wish to implement.
 * 
 * @author Matthew Roy
 * @author XuRui
 * @author Zhen Gou
 * 
 */
public abstract class Action {
	public static final String NULL_POINTER_EXCEPTION = "Attribute(s) does not exist for element.";
	
    private boolean myEnabledState;
    private boolean targetTracking;
    private List<Action> myFollowUpActions;

    public Action () {
        myFollowUpActions = new ArrayList<Action>();
        myEnabledState = true;
    }

    /**
     * Executes action after update clears the execute condition.
     */
    public abstract void executeAction (double elapsedTime);
    
    /**
     * Executes action after update clears the execute condition.
     */
    public void updateFollowUpActions(double elapsedTime) {
        for (Action a : getFollowUpActions()) {
            a.update(elapsedTime);
        }
    }

    /**
     * Update action status and executes action if it needs to
     * 
     * @param elapsedTime
     */
    public void update (double elapsedTime) {
        if (isEnabled()) {
            executeAction(elapsedTime);
            updateFollowUpActions(elapsedTime);
        }
    }

    /**
     * Adds a new action to occur after action is done executing
     * @param action
     */
    public void addFollowUpAction (Action action) {
        myFollowUpActions.add(action);
    }

    public void addFollowUpActions (List<Action> action) {
        myFollowUpActions.addAll(action);
    }
    
    /**
     * Returns list of followup actions
     * @return
     */
    public List<Action> getFollowUpActions () {
        return myFollowUpActions;
    }
    
    
    /**
     * Returns list of all targeted follow up actions
     * @return
     */
    public List<TargetedAction> getTargetedFollowUpActions () {
     	List<TargetedAction> actions = new ArrayList<TargetedAction>();
    	for (Action a: getFollowUpActions()){
    		if (a.isTargetTracking()){
    			actions.add((TargetedAction) a);
    		}
    	}
    	return actions;
    }

    public boolean isEnabled () {
        return myEnabledState;
    }

    public void setEnabled (boolean isEnabled) {
        myEnabledState = isEnabled;
    }
    
    public boolean isTargetTracking(){
    	return targetTracking;
    }
    
    public void setTargetTracking(boolean isTargeting){
    	targetTracking = isTargeting;
    	
    }
}
