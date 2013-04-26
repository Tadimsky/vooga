package vooga.towerdefense.factories.elementfactories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import vooga.towerdefense.action.Action;
import vooga.towerdefense.action.actionlist.SpawnGameElementsWithoutInitiator;
import vooga.towerdefense.factories.examplesfactories.ExampleUnitFactory;
import vooga.towerdefense.gameElements.GameElement;
import vooga.towerdefense.gameElements.Wave;
import vooga.towerdefense.model.GameMap;
import vooga.towerdefense.model.tiles.Tile;
import vooga.towerdefense.util.Location;


public class WaveFactory {

    private final static double DEFAULT_SPAWN_DELAY = 300;
    private final static double DEFAULT_DURATION = 1000 * 90;

    public static Wave createWave (ExampleUnitFactory ElementFactory,
                                   int numUnits,
                                   GameMap gameMap,
                                   Tile spawnTile,
                                   double spawnDelay,
                                   double duration) {
        
        Location spawnLocation = new Location(spawnTile.getCenter().getX(),
                                              spawnTile.getCenter().getY());
        Action spawnEnemies = new SpawnGameElementsWithoutInitiator(ElementFactory,
                                             gameMap, spawnLocation,
                                             numUnits, spawnDelay);
        Map<Boolean, Action> waveConditions =
                new HashMap<Boolean, Action>();
        waveConditions.put(true, spawnEnemies);
        return new Wave(gameMap, waveConditions);
    }

    //FIXME: I set these to example unit factories for testing
    public static Wave createWave (ExampleUnitFactory unitFactory, int numUnits,
                                   GameMap gameMap, Tile spawnTile) {
        return createWave(unitFactory, numUnits, gameMap, spawnTile,
                          DEFAULT_SPAWN_DELAY, DEFAULT_DURATION);
    }
}
