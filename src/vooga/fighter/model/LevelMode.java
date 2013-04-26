package vooga.fighter.model;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import vooga.fighter.controller.ModeCondition;
import vooga.fighter.forces.Force;
import vooga.fighter.model.objects.AttackObject;
import vooga.fighter.model.objects.CharacterObject;
import vooga.fighter.model.objects.GameObject;
import vooga.fighter.model.objects.MapObject;
import vooga.fighter.model.utils.Health;
import vooga.fighter.util.CollisionManager;


/**
 * Represents one level in the game, i.e. one matchup of two or more characters.
 * 
 * @author James Wei, alanni
 * 
 */
public class LevelMode extends Mode {

    private List<CharacterObject> myCharacterObjects;
    private List <Force> myForces;
    private List<Health> myHealthStats;
    private List<Double> myScores;
    private MapObject myMap;
    private List<ModeCondition> myModeConditions;

    public LevelMode(CollisionManager manager) {
        super(manager);
        myCharacterObjects = new ArrayList<CharacterObject>();
        myHealthStats = new ArrayList<Health>();
    }

    /**
     * Updates level mode by calling update in all of its objects.
     */
    public void update() {
        loadAttacks();
        removeAppropriateObjects();
        updateHealth();
        applyForces(); 
        handleCollisions();
        List<GameObject> myObjects = getMyObjects();
        
        // object update() and updateState() have to be in separate loops
        for (GameObject object : myObjects) {
            object.update();
            if (object instanceof AttackObject) {
                System.out.printf("LevelMode update : got attack object in loop\n");
                System.out.printf("LevelMode update : attack hitbox x=%d y=%d w=%d h=%d\n", 
                                  object.getCurrentState().getCurrentRectangle().x,
                                  object.getCurrentState().getCurrentRectangle().y,
                                  object.getCurrentState().getCurrentRectangle().width,
                                  object.getCurrentState().getCurrentRectangle().height);
            }
        }
        for (GameObject object : myObjects) {
            object.updateState();
        }
    }
    
    public void updateHealth() {
        for (int i =0; i < myCharacterObjects.size(); i++) {
            myHealthStats.set(i, myCharacterObjects.get(i).getHealth());
        }
    }



    /**
     * Applies forces on character objects
     */
    public void applyForces(){
        for (CharacterObject ch : myCharacterObjects) {
            for (Force force: myForces) {
                 force.applyForce(ch);
            }
        }
    }
    /**
     * loads attacks from characters if they are new and aren't timed out
     */
    public void loadAttacks() {
        for (CharacterObject ch : myCharacterObjects) {
            for (AttackObject attack : ch.getAttackObjects()) {
                if (!(getMyObjects().contains(attack) || attack.shouldBeRemoved())) {
                    addObject(attack);
                }
            }
        }
    }
    
    /**
     * Sets the map for the level
     */
    public void setMap(MapObject map){
    	myMap = map;
    	addObject(map);
    }

    /**
     * sets forces for the level 
     */
    public void setForces(List<Force> forces){
    	myForces=forces;
    }
    
    /**
     * returns the map of the level 
     */
    public MapObject getMap(){
    	return myMap;
    }
    /**
     * Returns the list of CharacterObjects.
     */
    public List<CharacterObject> getCharacterObjects() {
        return myCharacterObjects;
    }
    
    public void addCharacter(CharacterObject character){
    	myCharacterObjects.add(character);
    }


    public List<Health> getHealthStats() {
        return myHealthStats;
    }

}
