package vooga.scroller.level_management;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import util.input.Input;
import vooga.scroller.scrollingmanager.ScrollingManager;
import vooga.scroller.sprites.interfaces.IDoor;
import vooga.scroller.sprites.superclasses.Player;
import vooga.scroller.util.IGameComponent;
import vooga.scroller.view.GameView;
import vooga.scroller.level_editor.Level;
import vooga.scroller.level_management.splash_page.SplashPage;

/**
 * Manages the flow and order of levels in gameplay.
 * 
 * @author Scott Valentine
 *
 */
public class LevelManager {

    // TODO: this string exists somewhere else (need to consolidate)
    //TODO: fix design such that there is no need for developer to specify default (we could
    // just make a place holder file... seems like a cop-out
    private static final String DEFAULT_INPUT_CONTROLS = "vooga/scroller/marioGame/controls/SplashMapping";
    
    private Input myInput;
    private IGameComponent myInitialLevel;
    private IGameComponent myCurrentLevel;
    private GameView myView;
    
        
    /**
     * Creates a new level manager based on the view used by individual levels.
     * @param gameView to be used in constructing individual levels.
     */
    public LevelManager(ScrollingManager sm, GameView gameView, SplashPage splashPage, String ...levelFileNames) {   
        myView = gameView;
        LevelFactory lf = new LevelFactory(this, sm, gameView);
        myInitialLevel = lf.linkLevels(splashPage, lf.generateLevels(levelFileNames));        
        //myCurrentLevel = myLevels.get(DEFAULT_START_LEVEL_ID); 
        myInput = new Input(DEFAULT_INPUT_CONTROLS, gameView);
        setCurrentLevel(myInitialLevel);
    }
    
    /**
     * Creates a new level manager based on the view used by individual levels.
     * @param gameView to be used in constructing individual levels.
     */
    public LevelManager(ScrollingManager sm, GameView gameView, SplashPage splashPage, Level ...levels) {   
        myView = gameView;
        LevelFactory lf = new LevelFactory(this, sm, gameView);
        List<IGameComponent> gameComponents = new ArrayList<IGameComponent>();
        for (int i=0; i<levels.length; i++) {
            gameComponents.add(levels[i]);
        }
        myInitialLevel = lf.linkLevels(splashPage, gameComponents);        
        //myCurrentLevel = myLevels.get(DEFAULT_START_LEVEL_ID); 
        myInput = new Input(DEFAULT_INPUT_CONTROLS, gameView);
        setCurrentLevel(myInitialLevel);
    }
    
    
    /**
     * Gives the current level.
     * 
     * @return The current level
     */
    public IGameComponent getCurrentLevel() {
        return myCurrentLevel;
    }
    
    /**
     * Sets the current level to the specified ID.
     * 
     * @param id of the level to become the current level.
     */
    public void setCurrentLevel(IGameComponent level) {
        if(myCurrentLevel != null){
            myCurrentLevel.removeInputListeners(myInput);
            Player p = myCurrentLevel.getPlayer();
            myCurrentLevel = level;
            myCurrentLevel.addPlayer(p);
        }
        else{
            myCurrentLevel = level;
        }
        myCurrentLevel.addInputListeners(myInput);
    }
    
    /**
     * Map a door to a starting point. Bind door to this level manager.
     */
    public void put(IDoor door, IGameComponent nextLevel) {
        door.setNextLevel(nextLevel);
        door.setManager(this);
    }
    
    public void updateLevel(double elapsedTime, Dimension bounds) {        
        myCurrentLevel.update(elapsedTime, bounds, myView);
    }
}

