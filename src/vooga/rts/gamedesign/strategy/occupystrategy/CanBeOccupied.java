package vooga.rts.gamedesign.strategy.occupystrategy;

import java.util.ArrayList;
import java.util.List;
import vooga.rts.action.InteractiveAction;
import vooga.rts.commands.Command;
import vooga.rts.gamedesign.sprite.gamesprites.GameEntity;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.units.Soldier;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.units.Unit;
import vooga.rts.gamedesign.state.DetectableState;
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
    private List<String> myValidOccupierType;
    private int myMaxOccupiers;
    private int myOccupierID;
    
    /**
     * Creates a new occupy strategy that represents an entity that can be 
     * occupied.  It is created with a list of what entities can occupy it,
     * what entities are occupying it, and the max number of entities that can
     * occupy it.
     */
    public CanBeOccupied () {
        myOccupierHashCodes = new ArrayList<Integer>();
        myValidOccupierType = new ArrayList<String>();
        myMaxOccupiers = DEFAULT_MAX_OCCUPIERS;
        myOccupierID = 0;
    }

    /**
     * Creates and adds occupy strategy specific actions to entity
     */
    public void createOccupyActions (final InteractiveEntity entity) {
        addOccupyAction(entity);
        addPukeAction(entity);
    }

    /**
     * Creates and adds occupy Action.
     * 
     * @param entity the object that will be occupied.
     */
    private void addOccupyAction (final InteractiveEntity entity) {
        entity.addAction("be occupied!", new InteractiveAction(entity) {
            @Override
            public void update (Command command) {

            }

            @Override
            // TODO : Cannot pass objects into apply
            public void apply () {
                /*
                 * System.out.println("Goes here!");
                 * if (myOccupierHashCodes.size() < myMaxOccupiers && verifyOccupier(entity, (Unit)
                 * i)) {
                 * System.out.println("Verified!!");
                 * // i.setVisible(false);
                 * // i.getEntityState().setDetectableState(DetectableState.NOTDETECTABLE);
                 * if (myOccupierID == 0) {
                 * myOccupierID = i.getPlayerID();
                 * }
                 * myOccupierHashCodes.add(i.hashCode());
                 * entity.setChanged();
                 * entity.notifyObservers(i);
                 * }
                 */
            }
        });
    }

    /**
     * Creates and adds the action, in which the occupied entity will remove
     * and return all its occupiers back to the original player.
     * 
     * @param entity the object that is occupied.
     */
    private void addPukeAction (final InteractiveEntity entity) {
        entity.addAction("puke all I have", new InteractiveAction(entity) {
            @Override
            public void update (Command command) {
            }

            @Override
            public void apply () {
                List<Integer> occupiers = myOccupierHashCodes;
                myOccupierID = 0;
                myOccupierHashCodes = new ArrayList<Integer>();
                for (int hashCode : occupiers) {
                    // u.setVisible(true);
                    // u.setWorldLocation(new Location3D());
                    entity.setChanged();
                    entity.notifyObservers(hashCode);
                }
            }
        });
    }

    /**
     * Adds a new type of object as a valid type of occupier.
     */
    public void addValidClassType (Unit validOccupier) {
        Class cls = validOccupier.getClass();
        String className = cls.getName();
        myValidOccupierType.add(className);
    }

    /**
     * Verifies if the occupier is able to occupy the entity.
     * 
     * @param entity the entity that will be occupied
     * @param occupier the occupier that wants to perform occupy action
     * @return whether the occupier can perform occupy action
     */
    private boolean verifyOccupier (GameEntity entity, InteractiveEntity occupier) {
        Class cls = occupier.getClass();
        if (myOccupierID != 0 && myOccupierID != occupier.getPlayerID()) {
            return false;
        }
        for (String s : myValidOccupierType) {
            while (cls != null) {
                String className = cls.getName();
                // System.out.println("class name to be compared: "+ className);
                if (className.equals(s)) {
                    return true;
                }
                cls = cls.getSuperclass();
            }
        }
        return false;
    }

    /**
     * Sets the entity's current occupier id, which represents the player id
     * that is currently occupying the entity.
     */
    public void setOccupierID (int id) {
        myOccupierID = id;
    }

    /**
     * Returns the entity's current occupier id, which represents the player id
     * that is currently occupying the entity.
     */
    public int getOccupierID () {
        return myOccupierID;
    }

    /**
     * Returns the hash code of the list of occupiers.
     */
    public List<Integer> getOccupiers () {
        return myOccupierHashCodes;
    }

    /**
     * Returns the max number of occupiers this entity can take.
     */
    public int getMaxOccupiers () {
        return myMaxOccupiers;
    }

    /**
     * TESTING FOR VERIFYING VALID OCCUPIER
     * 
     * @param argus
     */
    public static void main (String[] argus) {
        OccupyStrategy o = new CanBeOccupied();
        o.addValidClassType(new Unit());
        Soldier s = new Soldier();
        Unit u = new Unit();
        // System.out.println("Soldier: " + verifyOccupier(s));
        // System.out.println("Unit: " + verifyOccupier(u));
    }

}
