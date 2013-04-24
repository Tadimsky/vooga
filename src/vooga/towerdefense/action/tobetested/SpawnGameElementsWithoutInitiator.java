package vooga.towerdefense.action.tobetested;

import java.util.ArrayList;
import java.util.List;
import vooga.towerdefense.action.PeriodicAction;
import vooga.towerdefense.factories.examplesfactories.ExampleUnitFactory;
import vooga.towerdefense.gameElements.GameElement;
import vooga.towerdefense.model.GameMap;
import vooga.towerdefense.model.tiles.Tile;
import vooga.towerdefense.util.Location;

/**
 * An action that spawns game elements.
 * 
 * @author Erick Gonzalez
 * @author Zhen Gou
 */
public class SpawnGameElementsWithoutInitiator extends PeriodicAction {
    private List<GameElement> myGameElements;
    private GameMap myGameMap;
    private Tile mySpawnTile;
    
    /**
     * 
     * @param elementFactory a game element factory 
     * @param map a game map
     * @param spawnLocation the location in which to spawn these game elements
     * @param numEnemies the number of enemies to spawn
     * @param spawnDelay the spawn delay of these enemies
     */
    public SpawnGameElementsWithoutInitiator(ExampleUnitFactory elementFactory, GameMap map, Location spawnLocation,
                      int numEnemies, double spawnDelay) {
        myGameElements = createGameElementsToSpawn(elementFactory,
                                                   numEnemies,
                                                   spawnLocation);
        myGameMap = map;
        mySpawnTile = map.getTile(spawnLocation);
        setCoolDown(spawnDelay);
     
    }

    @Override
    public void executeAction (double elapsedTime) {
    	updateTimer(elapsedTime);
    	if(isReady()&&hasNextGameElement()){
    		 spawnGameElements();
    		 resetTimer();
    		
    	}
       
        
    }
    
    private List<GameElement> createGameElementsToSpawn(ExampleUnitFactory elementFactory,
                                                        int numEnemies, Location spawnLocation) {
        List<GameElement> units = new ArrayList<GameElement>();
        for (int i = 0; i < numEnemies; ++i) {
            units.add(elementFactory.createElement(spawnLocation));
        }
        return units;
    }
    
    private void spawnGameElements() {
            GameElement gameElement = getNextGameElement();
            myGameMap.addToMap(gameElement, mySpawnTile);
        
    }
    
    private GameElement getNextGameElement() {
        GameElement u = myGameElements.iterator().next();
        myGameElements.remove(0);
        return u;
    }
    
    private boolean hasNextGameElement() {
        return myGameElements.iterator().hasNext();
    }
    
 
    

}
