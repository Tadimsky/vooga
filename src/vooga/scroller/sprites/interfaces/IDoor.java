package vooga.scroller.sprites.interfaces;

import vooga.scroller.level_management.LevelManager;
import vooga.scroller.sprites.superclasses.Player;
import vooga.scroller.util.IGameComponent;

/**
 * Interface that acts as a door between certain defined locations within levels.
 * 
 * @author Scott Valentine
 *
 */
public interface IDoor {

    /**
     * Sets the next level that this door points to. This sets the level that the player goes to
     * when the player interacts with the door.
     * 
     * @param level is the level that this door will point to.
     */
    public void setNextLevel (IGameComponent level);

    /**
     * Gives the level that this door points to.
     * 
     * @return The level that this door point to.
     */
    public IGameComponent getNextLevel();
    
    /**
     * Sets the level manager that this door uses to move between levels.
     * 
     * @param lm is the level manager used by the door to move between levels.
     */
    public void setManager(LevelManager lm);
        
    /**
     * Takes the player to the next level. Ideally this is called when the player interacts
     * with the door in someway. 
     * 
     * @param player is the player that is taken to the next level.
     */
    public void goToNextLevel(Player player);
    
}
