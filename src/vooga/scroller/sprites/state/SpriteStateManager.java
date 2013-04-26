package vooga.scroller.sprites.state;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import vooga.scroller.sprites.Sprite;


/**
 * Limitations: can't do the same state multiple times.
 * 
 * @author Scott Valentine
 *
 */
public class SpriteStateManager {

    
    private static final SpriteState DEFAULT_STATE = new DefaultSpriteState();

    private static final int DEFAULT_ID = -1;
    
    private Sprite mySprite;
    private Map<Integer, SpriteState> myStates;
    private Queue<SpriteState> myActiveStates;
     
    
    
    /**
     * Initializes a state manager with the default state.
     * 
     * @param sprite is the sprite for which this state manager controls the states.
     */
    public SpriteStateManager(Sprite sprite) {
        myActiveStates = new PriorityQueue<SpriteState>();
        myStates = new HashMap<Integer, SpriteState>();
        myStates.put(DEFAULT_ID, DEFAULT_STATE);
        myActiveStates.add(DEFAULT_STATE);
        mySprite = sprite;
    }
    
    
    /**
     * Update the active states in this manager.
     * 
     * @param elapsedTime
     * @param bounds
     */
    public void update (double elapsedTime, Dimension bounds) {
        Iterator<SpriteState> it = myActiveStates.iterator();
        
        while(it.hasNext()){
            it.next().update(mySprite, elapsedTime, bounds);
        }

//        for(SpriteState spState: myActiveStates){
//            spState.update(mySprite, elapsedTime, bounds);
//        }
    }

    /**
     * Paint the states active in this manager.
     * 
     * @param pen
     */
    public void paint (Graphics2D pen) {
        // only paint the first state (painting multiple states does not work).
       
        SpriteState sp = myActiveStates.peek();
        sp.paint(mySprite, pen);
    }
    
    /**
     * Activates the specified state.
     * @param stateID
     */
    public void activateState(int stateID){
        SpriteState state = myStates.get(stateID);
        if(!myActiveStates.contains(state) && state != null){
            state.activate(mySprite);
            System.out.println("add");
            myActiveStates.add(state);
        }
    }

    /**
     * Deactivates the specified state. If stateID is not valid, remove no state.
     * @param stateID
     */
    public void deactivateState(int stateID) {
        SpriteState state = myStates.get(stateID);
        if(state != null && myActiveStates.contains(state)){
            state.deactivate(mySprite);
            System.out.print("remove");
            myActiveStates.remove(myStates.get(stateID));
        }
    }
    
    /**
     * Adds a new possible state to this manager.
     * @param stateID
     * @param state
     */
    public void addState(int stateID, SpriteState state){
        myStates.put(stateID, state);
    }
    
    /**
     * Removes a possible state fromt this manager.
     * 
     * @param stateID
     */
    public void removeState(int stateID){
        myStates.remove(stateID);
    }
}
