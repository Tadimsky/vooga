package vooga.towerdefense.model.tiles.factories;

import util.Location;
import vooga.towerdefense.model.GameMap;
import vooga.towerdefense.model.Tile;

public class SpawnTileFactory extends PathTileFactory { 

    @Override
    public Tile createTile (Location center, GameMap map) {
        map.setSpawnLocation(center);
        return super.createTile(center, map);
    }

}
