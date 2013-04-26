package vooga.towerdefense.factories.actionfactories;

import java.util.List;
import vooga.towerdefense.action.Action;
import vooga.towerdefense.action.FindTargets;
import vooga.towerdefense.gameElements.GameElement;
import vooga.towerdefense.util.Location;

/**
 * This is an action factory that creates find target actions that give all of their follow up actions 
 * the game elements inside their radius
 * 
 * @author Matthew Roy
 *
 */
public class FindTargetsFactory extends ActionFactory {
    
    private String myRadiusId;
    private List<ActionFactory> myFollowUpActionFactories;
   
    /**
     * 
     * @param AttrRadiusToSearch is the id of the attribute used for radius
     */
    public FindTargetsFactory (String AttrRadiusToSearch) {
        myRadiusId = AttrRadiusToSearch;
    }

    /**
     * Creates a find targets action with follow up actions added.
     * @param e
     * @return 
     */
    @Override
    protected Action buildAction (GameElement e) {
        Location searchCenter = e.getCenter();
        Action locateTargets = new FindTargets(getMap(), searchCenter, e.getAttributeManager().getAttribute(myRadiusId));
        for (ActionFactory a : myFollowUpActionFactories) {
            locateTargets.addFollowUpAction(a.buildAction(e));
        }
        return locateTargets;
    }

}
