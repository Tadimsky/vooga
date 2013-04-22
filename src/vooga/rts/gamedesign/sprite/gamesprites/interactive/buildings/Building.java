package vooga.rts.gamedesign.sprite.gamesprites.interactive.buildings;

import java.awt.Dimension;


import java.awt.Graphics2D;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import vooga.rts.action.Action;
import vooga.rts.action.ProductionAction;

import vooga.rts.gamedesign.sprite.gamesprites.interactive.IObserver;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.units.Soldier;
import vooga.rts.gamedesign.upgrades.UpgradeNode;

import vooga.rts.gamedesign.sprite.gamesprites.interactive.units.Unit;
import vooga.rts.gamedesign.strategy.occupystrategy.CanBeOccupied;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.gamedesign.upgrades.UpgradeTree;
import vooga.rts.util.Location3D;
import vooga.rts.util.Pixmap;
import vooga.rts.util.Sound;


/**
 * This is an abstract class that represents a building. It will be extended
 * by specific types of buildings such as AttackTower.
 * 
 * @author Ryan Fishel
 * @author Kevin Oh
 * @author Francesco Agosti
 * @author Wenshun Liu
 * 
 */

public class Building extends InteractiveEntity{
	private static int PRODUCE_TIME = 90;

    public static final int MAXHEALTH = 100;
    private static UpgradeTree myUpgradeTree;


    private Location3D myRallyPoint;
    private List<Unit> myProducables;
    private List<InteractiveEntity> myInteractiveEntities;
    private List<IObserver> myObservers;

    private static GameBuildingManager myGameBuildingManager;
    private int myBuildingID;

    
    
    public Building (Pixmap image,
                     Location3D center,
                     Dimension size,
                     Sound sound,
                     int playerID,
                     int health, double buildTime, UpgradeTree upgradeTree) {
        super(image, center, size, sound, playerID, MAXHEALTH, buildTime);
        myRallyPoint = new Location3D(getWorldLocation().getX(), getWorldLocation().getY() + 50, 0);
        myProducables = new ArrayList<Unit>();
        myInteractiveEntities = new ArrayList<InteractiveEntity>();
        
        myObservers = new ArrayList<IObserver>();
        if (upgradeTree != null) {
        	myUpgradeTree = upgradeTree;
        }
    }
    
	@Override
	public InteractiveEntity copy() {
		return new Building(getImage(), getWorldLocation(), getSize(), getSound(),
				getPlayerID(), getHealth(), getBuildTime(), getUpgradeTree());
	}

    @Override
    public UpgradeTree getUpgradeTree() {
    	return myUpgradeTree;
    }
    
    
    
    @Override
    public void paint(Graphics2D pen) {
        super.paint(pen);
        for(int i = 0; i < myInteractiveEntities.size(); i++) {
            myInteractiveEntities.get(i).paint(pen);
        }
    }
    
	/**
     * Adds the list of available upgrades into the list of available actions.
     */
    public void addUpgradeActions(UpgradeTree upgradeTree){
    	List<UpgradeNode> initialUpgrades = upgradeTree.getCurrentUpgrades();
    	addUpgradeActions(initialUpgrades);
    }
    
    public void addUpgradeActions(List<UpgradeNode> nodeList) {
    	/*
    	for (final UpgradeNode u: nodeList) {
   		 getActions().add(new Action(u.getUpgradeName(), null, "An upgrade action"){
   	            @Override
   	            public void apply(int playerID) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException{
   	                u.apply(playerID);
   	                getActions().remove(this);
   	                if (!u.getChildren().isEmpty()) {
   	                	addUpgradeActions(u.getChildren());
   	                }
   	            }
   	        });
   	        
    	}
    	*/
    }
    /*
     * returns the list of producables
     */
    public List<Unit> getProducables() {
        return myProducables;
    }
    
    /**
     * Returns the rally point of the production building. 
     * Will be used to move the newly created units to
     * @return myRallyPoint, the rally point of the 
     * production building 
     */
    public Location3D getRallyPoint() {
        return myRallyPoint;
    }
    /*
     * Test method to add an interactive entity to 
     */
    public void addProducable(Unit i) {
        myProducables.add(i);
    }
    
    /**
     * Sets the rally point of the production building
     * @param rallyPoint the location of the new rally point 
     */
    public void setRallyPoint(Location3D rallyPoint) {
    	myRallyPoint = rallyPoint;
    }
   

	/**
	 * TESTING PURPOSE.
	 * Mimics player's click on action. Now invoke action after certain time. 
	 */
	@Override
	public void update(double elapsedTime) {
		super.update(elapsedTime);
		PRODUCE_TIME -= elapsedTime;
		if(PRODUCE_TIME <= 0) { 
			try {
				//findAction("Boost1").apply(1);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			PRODUCE_TIME = 90;
		}
        for(InteractiveEntity ie : myInteractiveEntities) {
            ie.update(elapsedTime);
        }
		
	}
	
    /**
     * Registers an IProductionObserver (a player) as its Observer.
     */
    public void register (IObserver newObserver) {
        myObservers.add(newObserver);
    }
    
    
    // TODO: this should work together with Occupy! When another player occupies
    // the building, it should unregister the current player and register the
    // new one.

    // NOTE:this can now be done in GameBuildingManager.
    /**
     * Unregisters an IProductionObserver (a player) so that it will not be
     * notified anymore when ProductionBuilding updates.
     */
    public void unregister (IObserver deleteObserver) {
        int observerIndex = myObservers.indexOf(deleteObserver);
        myObservers.remove(observerIndex);

    }
    
    /**
     * Notifies all the IProductionObserver that are currently observing of
     * the change.
     */
    public void notifyProductionObserver (Unit newProduction) {
        for (IObserver observer : myObservers) {
            observer.addProduction(newProduction);
        }
    }

	@Override
	public void addActions() {
		
		
	}

    
}
