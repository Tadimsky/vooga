package vooga.rts.gamedesign.strategy.occupystrategy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import vooga.rts.action.InteractiveAction;
import vooga.rts.commands.Command;
import vooga.rts.gamedesign.sprite.gamesprites.GameEntity;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.units.Unit;
import vooga.rts.gamedesign.state.DetectableState;
import vooga.rts.gamedesign.state.MovementState;
import vooga.rts.gamedesign.state.OccupyState;
import vooga.rts.gamedesign.strategy.Strategy;
import vooga.rts.util.Location3D;


/**
 * 
 * This class implements OccupyStrategy and is used as an instance in
 * InteractiveEntity for objects that can be occupied by types of Units specified.
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
     * occupied. It is created with a list of the entities that are occupying
     * it, the player ID that is currently occupying it, and the max number of
     * entities that can occupy it.
     */
    public CanBeOccupied () {
        myOccupierHashCodes = new ArrayList<Integer>();
        myMaxOccupiers = DEFAULT_MAX_OCCUPIERS;
        myOccupierID = 0;
    }

    /**
     * Gets occupied by the Unit that's passed in. First checks if the unit
     * is valid to occupy. Then updates the status of the CanBeOccupied
     * strategy and the unit.
     * 
     * @param entity the InteractiveEntity that owns the CanBeOccupied
     * strategy and will be occupied by the Unit
     * @param occupier the Unit that wishes to occupy
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
     * Creates and adds occupy strategy specific actions to the
     * InteractiveEntity that owns the strategy.
     * 
     * @param entity the InteractiveEntity that owns this strategy
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
        entity.addAction("deoccupy", new InteractiveAction(entity) {
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
    }

    /**
     * Returns the hash code of the list of occupiers.
     */
    public List<Integer> getOccupiers () {
        return myOccupierHashCodes;
    }

    /**
     * Applies this CanBeOccupied strategy to the InteractiveEntity passed in
     * by setting the strategy for the InteractiveEntity, and recreating the
     * actions.
     * 
     * @param other the InteractiveEntity that will receive the effect of
     * this OccupyStrategy
     */
	public void affect(InteractiveEntity other) {
		OccupyStrategy newOccupy = new CanBeOccupied();
		newOccupy.createOccupyActions(other);
		other.setOccupyStrategy(newOccupy);
	}
}
