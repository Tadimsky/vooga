package vooga.scroller.level_editor;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import vooga.scroller.level_editor.level_state.DefaultState;
import vooga.scroller.level_editor.level_state.LevelState;
import vooga.scroller.level_editor.level_state.PausedState;
import vooga.scroller.level_management.SpriteManager;
import vooga.scroller.view.GameView;

/**
 * Represents the manager for all the states in the level. This consists of active state 
 * and non active states. All states are referenced using an int stateID
 * 
 * @author Scott Valentine
 *
 */
public class LevelStateManager {

    // What to do about these. Leve them for reference for other parts of the game
    // the reason i used ID numbers to refer to states is to prevent other things from
    // having access to the states themselves.
    public static final int PAUSED_ID = 1;
    public static final int DEFAULT_ID = 0;
    
    private Map<Integer,LevelState> myLevelStates;
    private Collection<LevelState> myCurrentStates;
    private int myCurrentID;
    
    
    public LevelStateManager(SpriteManager spriteManager) {
        initLevelStates(spriteManager);
        myCurrentID = DEFAULT_ID;
        myCurrentStates = new ArrayList<LevelState>();
        myCurrentStates.add(myLevelStates.get(myCurrentID));
    }

    private void initLevelStates (SpriteManager spriteManager) {
        myLevelStates = new HashMap<Integer, LevelState>();

        PausedState paused = new PausedState(spriteManager);
        DefaultState normal = new DefaultState(spriteManager);
        
        myLevelStates.put(PAUSED_ID, paused);
        myLevelStates.put(DEFAULT_ID, normal);       
    }

    public void removeState(int stateID) {
        myCurrentStates.remove(myLevelStates.get(stateID));
    }
    
    public void addState(int stateID){
        myCurrentStates.add(myLevelStates.get(stateID));
    }
    
    public void update (double elapsedTime, Dimension bounds, GameView gameView) {        
        for(LevelState state: myCurrentStates){
            state.update(elapsedTime, bounds, gameView);
        }        
    }
    
    public void paint(Graphics2D pen){
        for(LevelState state: myCurrentStates){
            state.paint(pen);
        }    
    }
    
    /**
     * Whether or not the specified state (from the stateID) is currently active.
     * @param stateID
     * @return
     */
    public boolean isActive(int stateID) {
        return myCurrentStates.contains(myLevelStates.get(stateID));
    }

}
