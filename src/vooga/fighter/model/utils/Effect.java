package vooga.fighter.model.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import vooga.fighter.model.ModelConstants;
import vooga.fighter.model.objects.CharacterObject;
import vooga.fighter.model.objects.GameObject;

/**
 * Represents an effect placed on a character (or several).
 * 
 * @author James Wei, alanni
 *
 */
public abstract class Effect {
    
    private Counter myCounter;
    private CharacterObject myOwner;
    private Map<String,Integer> myProperties;
    
    /**
     * Constructs a new Effect with no owner and zero duration.
     */
    public Effect() {
        this(null, 0);
    }
    
    /**
     * Constructs a new Effect with no owner and given duration.
     */
    public Effect(int duration) {
        this(null, duration);
    }    
    
    /**
     * Constructs a new Effect with the given owner.
     */
    public Effect(CharacterObject owner, int duration) {
        myCounter = new Counter(duration);
        myOwner = owner;
        myProperties = new HashMap<String,Integer>();
        addProperty(ModelConstants.EFFECT_PROPERTY_DURATION, duration);
    }
    
    /**
     * Adds a property for this object. Overwrites any existing value.
     */
    public void addProperty(String key, int value) {
        myProperties.put(key, value);
    }

    /**
     * Returns a property for this object. Returns -1 if property does not exist.
     */
    public int getProperty(String key) {
        if (myProperties.containsKey(key)) {
            return myProperties.get(key);
        } else {
            return -1;
        }
    }
    
    /**
     * Clears all properties.
     */
    public void clearProperties() {
        myProperties.clear();
    }
    
    /**
     * Designates the owner of this effect, aka the target.
     */
    public void setOwner(CharacterObject owner) {
        myOwner = owner;
    }
    
    /**
     * Returns the owner of this effect, aka the target.
     */
    public CharacterObject getOwner() {
        return myOwner;
    }
    
    /**
     * Returns the current count on the effect, or -1 if the counter is not yet active.
     */
    public int getCount() {
        return myCounter.getCount();
    }
    
    /**
     * Updates the counter of the effect and calls applyEffect().
     */
    public void update() {
        applyEffect();
        myCounter.decrementCounter();
    }
    
    /**
     * Returns true if the effect has ended.
     */
    public boolean hasEnded() {
        return !myCounter.hasCountRemaining();
    }
    
    /**
     * Applies the effect to its target. Called every update cycle.
     */
    public abstract void applyEffect();

    
    /**
     * Creates a clone of this effect. The clone should be a deep copy.
     */
    public abstract Effect getCloneOfEffect();    
    
}
