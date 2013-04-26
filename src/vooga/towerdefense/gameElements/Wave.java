package vooga.towerdefense.gameElements;

import java.util.Map;
import vooga.towerdefense.action.Action;
import vooga.towerdefense.model.GameMap;

public class Wave {
    private GameMap myGameMap;
    private Map<Boolean, Action> myWaveConditions;
    
    /**
     * 
     * @param gameMap 
     * @param waveConditions
     */
    public Wave(GameMap gameMap, Map<Boolean, Action> waveConditions) {
        myGameMap = gameMap;
        myWaveConditions = waveConditions;
    }
    
    public void update(double elapsedTime) {
        for (Boolean condition : myWaveConditions.keySet()) {
            if (condition) {
                Action action = myWaveConditions.get(condition);
                action.executeAction(elapsedTime);
            }
        }
    }
}
