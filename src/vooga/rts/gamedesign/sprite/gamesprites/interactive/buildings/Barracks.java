package vooga.rts.gamedesign.sprite.gamesprites.interactive.buildings;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import vooga.rts.gamedesign.Interval;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.units.Soldier;
import vooga.rts.util.Location3D;
import vooga.rts.util.Pixmap;
import vooga.rts.util.Sound;


/**
 * 
 * @author Kevin Oh
 * 
 */
public class Barracks extends ProductionBuilding {
    public static final int PRODUCE_TIME = 90;
    private Interval myBuildInterval;

    private List<InteractiveEntity> myInteractiveEntities;

    public Barracks (Pixmap image,
                     Location3D center,
                     Dimension size,
                     Sound sound,
                     int playerID,
                     int health,
                     int buildTime) {
        super(image, center, size, sound, playerID, health, buildTime);
        myInteractiveEntities = new ArrayList<InteractiveEntity>();
        initProducables();
        addProductionActions(this);
        setRallyPoint(new Location3D(300, 400, 0));
        myBuildInterval = new Interval(PRODUCE_TIME);
    }

    /*
     * FOR TESTING: CALLED IN CONSTRUCTOR TO INITIALIZE PRODUCABLE LIST
     */
    private void initProducables () {
        addProducable(new Soldier());
    }

    public void addProductionActions (ProductionBuilding productionBuilding) {
        /*
         * getActions().add(new ProductionAction("soldier",null,"I maketh un soldier",
         * productionBuilding.getWorldLocation()){
         * 
         * @Override
         * public void apply(int playerID) {
         * Unit newProduction = (Unit) getProducables().get(0).copy();
         * Location3D newProductionLoc = new Location3D(getProducedFrom());
         * newProduction.setWorldLocation(newProductionLoc.getX(), newProductionLoc.getY(), 0);
         * //these below are for testing purposes
         * newProduction.move(getRallyPoint());
         * //this part below will not be in actual implementation as I will notify player/unit
         * manager that a new unit should be added to the player
         * myInteractiveEntities.add(newProduction);
         * getGameBuildingManager().distributeProduct(newProduction, playerID);
         * //notifyProductionObserver(newProduction);
         * }
         * });
         */
    }

    @Override
    public void paint (Graphics2D pen) {
        for (int i = 0; i < myInteractiveEntities.size(); i++) {
            myInteractiveEntities.get(i).paint(pen);
        }
        super.paint(pen);
    }

    @Override
    public void update (double elapsedTime) {
        super.update(elapsedTime);
        myBuildInterval.decrementCooldown();
        if (myBuildInterval.allowAction()) {
            try {
                // getActions().get(0).apply(2); //2: for testing. make Barrack create new Units of
                // different team.
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            myBuildInterval.resetCooldown();
        }
        for (InteractiveEntity ie : myInteractiveEntities) {
            ie.update(elapsedTime);
        }

    }

}
