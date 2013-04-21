package vooga.rts.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.buildings.Building;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.units.Unit;
import vooga.rts.player.Player;


/**
 * This class stores all the buildings in the game and manages their
 * distribution among players.
 * It provides methods to distribute building products among players.
 * 
 * @author Wenshun Liu
 * 
 */

public class GameBuildingManager {
    private List<Player> myPlayers;
    private List<Building> myUnassignedBuildings;
    private Map<Integer, ArrayList<Building>> myPlayerBuildings;

    public GameBuildingManager () {
        myPlayers = new ArrayList<Player>();
        myUnassignedBuildings = new ArrayList<Building>();
        myPlayerBuildings = new HashMap<Integer, ArrayList<Building>>();
    }

    public void addPlayer (Player player, int playerID) {
        myPlayers.add(player);
        myPlayerBuildings.put(playerID, new ArrayList<Building>());
    }
    
    public void addBuilding(Building building) {
    	if (building.getPlayerID() == 0) {
    		addUnassignedBuilding(building);
    	} else {
    		addBuildingToPlayer(building);
    	}
    }
    
    private void addUnassignedBuilding(Building building){
    	myUnassignedBuildings.add(building);
    }

    public List<Building> getUnassignedBuilding () {
        return myUnassignedBuildings;
    }

    public void occupyBuilding (Building building, int playerID) {
        if (building.getPlayerID() == 0) {
            int buildingIndex = myUnassignedBuildings.indexOf(building);
            myUnassignedBuildings.remove(buildingIndex);
        }
        else {
            ArrayList<Building> oldBuildingList = myPlayerBuildings.get(building.getPlayerID());
            int buildingIndex = oldBuildingList.indexOf(building);
            oldBuildingList.remove(buildingIndex);
            myPlayerBuildings.put(building.getPlayerID(), oldBuildingList);
        }
        building.setPlayerID(playerID);
        ArrayList<Building> newBuildingList = myPlayerBuildings.get(playerID);
        newBuildingList.add(building);
        myPlayerBuildings.put(playerID, newBuildingList);
    }

    public void distributeProduct (Unit newProduction, int playerID) {
        System.out.println("Player ID " + playerID + " will get new unit!");
        //myPlayers.get(playerID - 1).addProduction(newProduction);
    }
    
    private void addBuildingToPlayer(Building building) {
    	ArrayList<Building> oldBuildingList = myPlayerBuildings.get(building.getPlayerID());
		oldBuildingList.add(building);
		myPlayerBuildings.put(building.getPlayerID(), oldBuildingList);
    }
}
