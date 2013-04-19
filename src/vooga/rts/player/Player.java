package vooga.rts.player;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import vooga.rts.IGameLoop;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.units.Unit;
import vooga.rts.manager.*;
import vooga.rts.map.GameMap;

public abstract class Player implements IGameLoop, IProductionObserver {    
	
	private IndividualResourceManager myResourceManager;
    private UnitManager myUnitManager;
    private BuildingManager myBuildingManager;
    private int myTeamID;
    private GameMap myMap;
    
    
    public Player() {
        myResourceManager = new IndividualResourceManager();
        myUnitManager= new UnitManager();
        
    }
    
    public Player(GameMap map) {
        this();
        myMap = map;
    }
    
    public void addProduction(Unit newProduction) {
    	myUnitManager.addUnit(newProduction);
    	System.out.println("player adds new unit!");
    }
    
    public void updateResource(HashMap<String, Integer> updatedResources) {
    	myResourceManager.setResources(updatedResources);
    	System.out.println("player gets resource!");
    }

    public IndividualResourceManager getIndividualResourceManager () {
        return myResourceManager;
    }

    public UnitManager getUnits () {
        return myUnitManager;
    }

    public BuildingManager getBuildings () {
        return myBuildingManager;
    }

    public int getTeamID () {
        return myTeamID;
    }
    
    public void setTeamID (int id) {
        myTeamID = id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vooga.rts.IGameLoop#update(double)
     */
    @Override
    public void update (double elapsedTime) {
        myUnitManager.update(elapsedTime);
    }

    /*
     * (non-Javadoc)
     * 
     * @see vooga.rts.IGameLoop#paint(java.awt.Graphics2D)
     */
    @Override
    public void paint (Graphics2D pen) {
        myUnitManager.paint(pen);
    }
    
    public GameMap getMap () {
        return myMap;
    }
}
