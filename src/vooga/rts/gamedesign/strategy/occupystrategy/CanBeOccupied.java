package vooga.rts.gamedesign.strategy.occupystrategy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import vooga.rts.action.InteractiveAction;
import vooga.rts.commands.Command;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.units.Unit;
import vooga.rts.gamedesign.state.OccupyState;
import vooga.rts.util.Information;


/**
 * This class implements OccupyStrategy and is used as an instance in
 * InteractiveEntity for objects that can be occupied by types of Units
 * specified.
 * 
 * @author Wenshun Liu
 * 
 */
public class CanBeOccupied implements OccupyStrategy {
    public static final int DEFAULT_MAX_OCCUPIERS = 10;

    private List<Integer> myOccupierHashCodes;
    private int myMaxOccupiers;
    private int myOccupierID;

    /**
     * Creates a new occupy strategy that represents an entity that can be
     * occupied. It is created with a list of what entities are occupying it,
     * the current player id that occupys it, and the max number of entities
     * that can occupy it.
     */
    public CanBeOccupied () {
        myOccupierHashCodes = new ArrayList<Integer>();
        myMaxOccupiers = DEFAULT_MAX_OCCUPIERS;
        myOccupierID = 0;
    }

    /**
     * Gets occupied by the Unit passed in by first checking if the Unit is
     * valid to perform the action, and then updating the state of the
     * strategy and the Unit.
     * 
     * @param entity The InteractiveEntity that has this strategy and will be
     *        occupied
     * @param occupier the Unit that wishes to occupy the InteractiveEntity
     */
    public void getOccupied (InteractiveEntity entity, Unit occupier) {
        if (myOccupierHashCodes.size() < myMaxOccupiers) {
            if (myOccupierID == 0) {
                myOccupierID = occupier.getPlayerID();
            }
            myOccupierHashCodes.add(occupier.hashCode());
            entity.setChanged();

            occupier.getEntityState().setOccupyState(OccupyState.OCCUPYING);
            occupier.setVisible(false);
            entity.notifyObservers(occupier);
        }
    }

    /**
     * Gets the hash code of all the occupiers
     * 
     * @return the list of all the occupiers
     */
    public List<Integer> getOccupiers () {
        return myOccupierHashCodes;
    }

    /**
     * Creates and adds occupy strategy specific actions to the
     * InteractiveEntity passed in
     */
    public void createOccupyActions (final InteractiveEntity entity) {
        addDeoccupyAction(entity);
    }

    /**
     * Creates and adds the action, in which the occupied entity will remove
     * and return all its occupiers back to the original player.
     * 
     * @param entity the object that is occupied.
     */
    private void addDeoccupyAction (final InteractiveEntity entity) {

        String commandName = "deoccupy";
        entity.addAction(commandName, new InteractiveAction(entity) {
            @Override
            public void update (Command command) {
            }

            @Override
            public void apply () {
                myOccupierID = 0;
                Iterator<Integer> it = myOccupierHashCodes.iterator();
                while (it.hasNext()) {
                    Integer hashCode = it.next();
                    entity.setChanged();
                    entity.notifyObservers(hashCode);
                    it.remove();
                }
            }
        });
        entity.addInfo(commandName, new Information(commandName, "this deoccupies errboday",
                                                    "buttons/unload.gif", null));
    }

    /**
     * 
     * Applies this CanBeOccupied strategy to another InteractiveEntity that is
     * passed in, by setting it as the InteractiveEntity's strategy and
     * recreating the actions.
     * 
     * @param other the InteractiveEntity that will receive the strategy.
     */

    public void affect (InteractiveEntity entity) {
        OccupyStrategy newOccupy = new CanBeOccupied();
        newOccupy.createOccupyActions(entity);
        entity.setOccupyStrategy(newOccupy);
    }

}
