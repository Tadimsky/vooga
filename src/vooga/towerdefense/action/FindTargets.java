
package vooga.towerdefense.action;

import java.util.List;

import vooga.towerdefense.attributes.Attribute;
import vooga.towerdefense.gameElements.GameElement;
import vooga.towerdefense.model.GameMap;
import vooga.towerdefense.util.Location;


/**
 * Finds targets in area, passes information on to follow up actions. 
 * Only action class that holds the game map. 
 * 
 * @author Matthew Roy
 * @author Xu Rui
 * @author Zhen Gou
 */
public class FindTargets extends TargetedAction {

    private Attribute myScanningRadius;
    private Location mySource;
    private GameMap myMap;

    public FindTargets (GameMap map, Location source, Attribute attackRadius) {
        super();
        myScanningRadius = attackRadius;
        mySource = source;
        myMap = map;
    }
    
    @Override
    public void executeAction (double elapsedTime) {
        List<GameElement> newTargets= 
                   myMap.getTargetsWithinRadius(
                                                mySource, 
                                                myScanningRadius.getValue());
        for (GameElement e : newTargets) {
            if (e != null) {
                setTargets(newTargets);
            }
        }
        updateTargetedFollowUpActions(getTargets());
    }
}
