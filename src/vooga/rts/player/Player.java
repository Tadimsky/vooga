package vooga.rts.player;

import java.awt.Graphics2D;
import java.util.Observable;
import java.util.Observer;
import vooga.rts.IGameLoop;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.manager.*;
import vooga.rts.map.GameMap;

public abstract class Player implements IGameLoop, Observer {    

    private ResourceManager myResourceManager;
    private UnitManager myUnitManager;
    private BuildingManager myBuildingManager;
    private int myTeamID;
    private GameMap myMap;
    
    
    public Player() {
        myResourceManager = new ResourceManager();
        myUnitManager= new UnitManager();
    }
    
    public Player(GameMap map) {
        this();
        myMap = map;
    }

    public ResourceManager getResourceManager () {
        return myResourceManager;
    }

    public ResourceManager removeResources (InteractiveEntity i) {
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
