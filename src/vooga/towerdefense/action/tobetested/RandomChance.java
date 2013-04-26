package vooga.towerdefense.action.tobetested;

import vooga.towerdefense.action.Action;

/**
 * @author Matthew Roy
 *
 */
public class RandomChance extends Action {

    private double myChance;
    
    public RandomChance(double randomChance) {
        super();
        myChance = randomChance;
    }
    
    public void update(double elapsedTime) {
        if (Math.random() <= myChance) {
            super.update(elapsedTime);
        }
    }
    
    /**
     * 
     * @param elapsedTime 
     */
    @Override
    public void executeAction (double elapsedTime) {
        // TODO Auto-generated method stub

    }

}
