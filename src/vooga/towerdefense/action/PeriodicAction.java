package vooga.towerdefense.action;

import vooga.towerdefense.attributes.Attribute;

/**
 * @author Matthew Roy
 *
 */
public class PeriodicAction extends TargetedAction {
    private Attribute myCd;
    private double myTimer;
    
    public PeriodicAction (Attribute cd) {
        myCd    = cd;
        myTimer = 0;
    }
    
    public void update(double elapsedTime) {
        if (isEnabled()) {
            executeAction(elapsedTime);
        }
    }
    
    /**
     * 
     * @param elapsedTime 
     */
    @Override
    public void executeAction (double elapsedTime) {
        myTimer += elapsedTime;
        if (myTimer > myCd.getValue()) {
            updateFollowUpActions(elapsedTime);
            myTimer -= myCd.getValue();
        }
        
    }

}
