package vooga.fighter.model.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import vooga.fighter.model.loaders.CharacterLoader;
import vooga.fighter.model.utils.Effect;
import vooga.fighter.model.utils.Health;
import vooga.fighter.model.utils.UpdatableLocation;
import util.Vector;


/**
 * Represents a character in the game, as well as the character's current state
 * in the given session.
 * 
 * @author James Wei, alanni, David Le
 * 
 */
public class CharacterObject extends GameObject {

    private Map<String, AttackObject> myAttacks;
    private List<Effect> myActiveEffects;
    private Health myHealth; 
    private List<AttackObject> currentAttacks; 
    private boolean facingRight;  
    private int movingDirection; 
    private static final int MOVE_BACK_AMOUNT=-2; 
    
    /**
     * Constructs a new CharacterObject.
     */
    public CharacterObject(String charName, UpdatableLocation center) {
        super();
        myAttacks = new HashMap<String, AttackObject>();
        myActiveEffects = new ArrayList<Effect>();
        myHealth = new Health();
        facingRight= true;  
        currentAttacks= new ArrayList<AttackObject>();
        setLoader(new CharacterLoader(charName, this));
        setCurrentState("stand");
        getCurrentState().setLooping(true);
        setLocation(center);
        setImageData();
        
    }

    /**
     * Updates the character for one game loop cycle. Applies effects currently
     * active on the character.
     */
    public void completeUpdate() {
        for (Effect effect : myActiveEffects) {
            effect.update();
        } 
    }
    
    /**
     * Calls GameObject's updateState() method as well as sets the default state to stand
     * if no other state actions are going on.
     */
    public void updateState() {
        super.updateState();
        if (getCurrentState().hasCompleted()) {
            setCurrentState("stand");            
        }
    }

    /**
     * Adds an effect to this character's list of active effects.
     */
    public void addActiveEffect(Effect effect) {
        effect.setOwner(this);
        myActiveEffects.add(effect);
    }

    /**
     * Removes an effect from this character's list of active effects.
     */
    public void removeActiveEffect(Effect effect) {
        myActiveEffects.remove(effect);
    }

    /**
     * Returns list of currently active effects on this character.
     */
    public List<Effect> getActiveEffects() {
        return myActiveEffects;
    }

    /**
     * Adds an AttackObject to the list of attacks available for this character.
     * Note that this attack will not be added to the list of game objects in a
     * level, thus it will not update and should be used solely for generating
     * other attack objects as needed. Overwrites any existing attack.
     */
    public void addAttack(String key, AttackObject object) {
        myAttacks.put(key, object);
    }

    /**
     * Creates and returns an attack object based on a given identifier. Returns null
     * if the specified attack does not exist.
     * 
     * Note: For now just using String to represent attack types, but this is obviously
     * subject to change.
     */
    public AttackObject createAttack(String key) {
        if (myAttacks.containsKey(key)) {
            return myAttacks.get(key);
        }
        else {
            return null;
        }
    }

    /**
     * Returns the health of the character.
     */
    public Health getHealth() {
        return myHealth;
    }

    /**
     * Returns whether or not the character has remaining health.
     */
    public boolean hasHealthRemaining() {
        return myHealth.hasHealthRemaining();
    }

    /**
     * Sets the health of the character
     */
    public void setHealth(int amount) {
        myHealth.setHealth(amount);
    }

    /**
     * Changes the player's health by a given amount. Positive input raises it, and
     * negative input decreases it. Returns health remaining.
     */
    public int changeHealth(int amount) {
        return myHealth.changeHealth(amount);
    }

    /**
     * Creates a new AttackObject by cloning the object identified by the given key
     * in the attacks map.
     */
    public void attack(String attack) {
        setCurrentState("weakPunch");
        UpdatableLocation characterLocation= getLocation(); 
        currentAttacks.add(new AttackObject(myAttacks.get(attack), new UpdatableLocation(characterLocation.getLocation().getX(), characterLocation.getLocation().getY())));
      
    }

    /**
     * Moves in given direction at speed of character
     */
    public void move(int direction) {
        setCurrentState("moveRight");
        movingDirection=direction;
        getLocation().translate(new Vector(direction, getProperty("speed")));
    }

    /**
     * Makes the character move back if it runs into another character or environmentobject
     */
    public void moveBack(){
    	getLocation().translate(new Vector(movingDirection, MOVE_BACK_AMOUNT*getProperty("speed")));
    }

    /**
     * Gets the direction the character is moving
     */
    public int getMovingDirection(){
    	return movingDirection;
    }
    
    /**
     * Will add jump method
     */
    public void jump() {        
    	//TODO: Add acceleration 
    } 
    
    /**
     * Characters should never be removed.
     */
    public boolean shouldBeRemoved() {
        return false;
    }
    
    /**
     * Checks to see if character is facing right
     */
    public boolean isFacingRight(){
    	return facingRight; 
    }
    
    /**
     * Sets the character to face left 
     */
    public void faceLeft(){
    	facingRight=false; 
    }
    
    /**
     * Sets the character to face right 
     */
    public void faceRight(){
    	facingRight=true; 
    }
    
    /**
     * Returns list of all attackObjects
     */
    public List<AttackObject> getAttackObjects(){
    	return currentAttacks; 
    }
    

    /**
     * Dispatches a colliding object to allow for proper collision handling. 
     */
    public void dispatchCollision(GameObject other) {
        other.handleCollision(this);
    }
    
    /**
     * Collision with another CharacterObject.
     */
    public void handleCollision(CharacterObject other) {
        System.out.println("CharacterObject handleCollision : Character collided with character");
    }
    
    /**
     * Collision with an AttackObject.
     */
    public void handleCollision(AttackObject other) {
        other.inflictDamage(this);
        System.out.println("CharacterObject handleCollision : Character collided with ATTACK");
        
    }
    
    /**
     * Collision with an EnvironmentObject.
     */
    public void handleCollision(EnvironmentObject other) {
        System.out.println("CharacterObject handleCollision : Character collided with environment");
    }
    
}
