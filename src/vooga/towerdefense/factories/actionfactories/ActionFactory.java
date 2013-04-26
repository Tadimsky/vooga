package vooga.towerdefense.factories.actionfactories;

import java.util.ArrayList;
import java.util.List;

import vooga.towerdefense.action.Action;
import vooga.towerdefense.gameElements.GameElement;
import vooga.towerdefense.model.GameMap;


/**
 * Creates actions based on definitions of the ActionFactory
 * NEEDS TO BE INITIALIZED BEFORE ANYTHING HAPPENS
 * 
 * @author Matthew Roy
 * 
 */
public abstract class ActionFactory {

    private GameMap myMap;
    private List<ActionFactory> myFollowUpActions;

    public ActionFactory () {
        myFollowUpActions = new ArrayList<ActionFactory>();
    }

    /**
     * Places in all of the objects that the factory could need to function
     * Cannot create actions until initialized
     */
    public void initialize (GameMap map) {
        myMap = map;
    }
    
    public GameMap getMap() {
        return myMap;
    }

    // TODO: Not sure if this is the best way to implement this. There needs to be a way to give in
    // a list of followup actions to the action
    public void addFollowUpActionsFactories (ActionFactory addToList) {
        myFollowUpActions.add(addToList);
    }

    // a list of followup actions to the action
    public void addFollowUpActionsFactories (List<ActionFactory> addToList) {
        myFollowUpActions.addAll(addToList);
    }
    
    public List<ActionFactory> getFollowUpActions() {
        return myFollowUpActions;
    }

    /**
     * THIS IS HOW YOU MAKE A NEW INSTANTIATION OF ACTION
     * 
     * @param e
     * @return
     */
    public Action createAction (GameElement e) {
        Action myAction = buildAction(e);
        myAction.addFollowUpActions(createFollowUpActions(e));
        return myAction;
    }

    /**
     * 
     * @param e
     * @param target
     * @return
     */
    public Action createTargetedAction (GameElement e, GameElement target) {
        Action myAction = buildTargetedAction(e, target);
        myAction.addFollowUpActions(createFollowUpActions(e, target));
        return myAction;
    }

    /**
     * Used to just create the single action
     * 
     * @param e
     * @return
     */
    protected abstract Action buildAction (GameElement e);

    /**
     * Used to just create the single action
     * 
     * @param e
     * @return
     */
    protected Action buildTargetedAction (GameElement e, GameElement target){
    	return buildAction(e);
    }

    /**
     * Used to create the following actions
     * 
     * @param e
     * @return
     */
    protected List<Action> createFollowUpActions (GameElement e) {
        List<Action> followingActions = new ArrayList<Action>();
        for (ActionFactory a : myFollowUpActions) {
            followingActions.add(a.createAction(e));
        }
        return followingActions;
    }

    /**
     * Used to create the following actions
     * 
     * @param e
     * @return
     */
    protected List<Action> createFollowUpActions (GameElement e, GameElement target) {
        List<Action> followingActions = new ArrayList<Action>();
        for (ActionFactory a : myFollowUpActions) {
            followingActions.add(a.createTargetedAction(e, target));
        }
        return followingActions;
    }

}
