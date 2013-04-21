package vooga.rts.gamedesign.sprite.gamesprites.interactive.buildings;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import vooga.rts.action.InteractiveAction;
import vooga.rts.commands.Command;
import vooga.rts.controller.PositionCommand;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.units.Soldier;
import vooga.rts.util.Camera;
import vooga.rts.util.Location3D;
import vooga.rts.util.Pixmap;
import vooga.rts.util.Sound;


/**
 * 
 * @author Kevin Oh
 * 
 */
public class Barracks extends ProductionBuilding {

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
        initProducables();
        setRallyPoint(new Location3D(300, 400, 0));
        //myProductionStrategy = new CanProduce(this);
    }

    /*
     * FOR TESTING: CALLED IN CONSTRUCTOR TO INITIALIZE PRODUCABLE LIST
     */
    private void initProducables () {
        addProducable(new Soldier());
    }

    @Override
    public void addActions () {
       put("s", new InteractiveAction(this) {
          private Location3D myLocation;
          
          @Override
          public void apply() {
              getEntity().move(myLocation);
          }
          
          @Override
          public void update(Command command) {          
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
