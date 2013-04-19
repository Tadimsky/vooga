package vooga.rts.gamedesign.sprite.gamesprites.interactive.buildings;

import java.awt.Dimension;
import java.util.List;
import java.util.ArrayList;

import vooga.rts.IObservable;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.units.Unit;
import vooga.rts.player.IProductionObserver;
import vooga.rts.util.Location;
import vooga.rts.util.Location3D;
import vooga.rts.util.Pixmap;
import vooga.rts.util.Sound;
/**
 * This class is a subclass of Building to deal with production buildings.
 * Has rallypoints which is where newly produced units go to. 
 * 
 * @author Junho Oh
 */
public class ProductionBuilding extends Building implements IObservable{
    private Location3D myRallyPoint;
    private List<Unit> myProducables; //for testing really, need to make it work with xml file
    private List<IProductionObserver> myObservers;
    
    public ProductionBuilding (Pixmap image,
                               Location3D center,
                               Dimension size,
                               Sound sound,
                               int playerID,
                               int health) {
        super(image, center, size, sound, playerID, health);
        myRallyPoint = new Location3D(getWorldLocation().getX(), getWorldLocation().getY() + 50, 0);
        myProducables = new ArrayList<Unit>();
        myObservers = new ArrayList<IProductionObserver>();
    }

    @Override
    public void getOccupied (Unit unit) {
        // does nothing if building isnt occupied. 
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
     * Registers an IProductionObserver (a player) as its Observer.
     */
	public void register(IProductionObserver newObserver) {
		myObservers.add(newObserver);
	}
	
	//TODO: this should work together with Occupy! When another player occupies
	// the building, it should unregister the current player and register the
	// new one.
    
    //NOTE:this can now be done in GameBuildingManager.
	/**
	 * Unregisters an IProductionObserver (a player) so that it will not be
	 * notified anymore when ProductionBuilding updates.
	 */
	public void unregister(IProductionObserver deleteObserver) {
		int observerIndex = myObservers.indexOf(deleteObserver);
		myObservers.remove(observerIndex);
		
	}

	/**
	 * Notifies all the IProductionObserver that are currently observing of
	 * the change.
	 */
	public void notifyProductionObserver(Unit newProduction) {
		for (IProductionObserver observer: myObservers) {
			observer.addProduction(newProduction);
		}
	}
}
