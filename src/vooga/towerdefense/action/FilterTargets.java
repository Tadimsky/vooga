package vooga.towerdefense.action;

import java.util.ArrayList;
import java.util.List;
import vooga.towerdefense.attributes.Attribute;
import vooga.towerdefense.gameElements.GameElement;
import vooga.towerdefense.model.GameMap;
import vooga.towerdefense.util.Location;


/**
 * Filters targets using an affiliation system
 * 
 * 
 * @author Matthew Roy
 * @author XuRui
 * 
 */
public class FilterTargets extends TargetedAction {

    Attribute myTargetAffiliation;
    Attribute myNumTargets;

    public FilterTargets (Attribute targetAffiliation) {
        super();
        myTargetAffiliation = targetAffiliation;
    }

    public FilterTargets (Attribute targetAffiliation, Attribute numTargets) {
        super();
        myTargetAffiliation = targetAffiliation;
        myNumTargets = numTargets;
    }

    /**
     * 
     * @param elapsedTime
     */
    @Override
    public void executeAction (double elapsedTime) {
        List<GameElement> filteredTargets = new ArrayList<GameElement>();
        for (int i = 0; i < getTargets().size(); i++) {
            GameElement e = getTargets().get(i);
            Attribute affiliation = e.getAttributeManager().getAttribute("Affiliation");
            if (affiliation != null && affiliation.getValue() == myTargetAffiliation.getValue()) {
                filteredTargets.add(e);
                if (myNumTargets != null && myNumTargets.getValue() == myTargetAffiliation.getValue()) {
                    break;
                }
            }
        }
        updateTargetedFollowUpActions(filteredTargets);
    }
}
