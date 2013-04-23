package vooga.rts.gamedesign.sprite.gamesprites.interactive.buildings;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import vooga.rts.action.InteractiveAction;
import vooga.rts.commands.Command;
import vooga.rts.commands.PositionCommand;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.units.Soldier;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.units.Unit;
import vooga.rts.util.Camera;
import vooga.rts.util.Location3D;
import vooga.rts.util.Pixmap;
import vooga.rts.util.Sound;


/**
 * 
 * @author Kevin Oh
 * 
 */
public class Barracks extends Building {

    private List<InteractiveEntity> myInteractiveEntities;

    public Barracks (Pixmap image,
                     Location3D center,
                     Dimension size,
                     Sound sound,
                     int playerID,
                     int health,
                     double buildTime) {
        super(image, center, size, sound, playerID, health, buildTime);
        myInteractiveEntities = new ArrayList<InteractiveEntity>();

        setRallyPoint(new Location3D(300, 400, 0));
        // myProductionStrategy = new CanProduce(this);
    }

    // public void addProductionActions (ProductionBuilding productionBuilding) {
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

    @Override
    public void addActions () {
        put("s", new InteractiveAction(this) {
            private Location3D myLocation;

            @Override
            public void apply () {
                getEntity().move(myLocation);
            }

            @Override
            public void update (Command command) {
                PositionCommand click = (PositionCommand) command;
                myLocation = Camera.instance().viewtoWorld(click.getPosition());
            }
        });
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
        try {
            // getActions().get(0).apply(2); //2: for testing. make Barrack create new Units of
            // different team.
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        for (InteractiveEntity ie : myInteractiveEntities) {
            ie.update(elapsedTime);
        }

    }

}
