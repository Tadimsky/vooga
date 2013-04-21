package vooga.rts.action;

import vooga.rts.commands.Command;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.units.Unit;


/*
 * This is the abstract super class for Actions.
 * Actions will be used to execute methods on parts of the game.
 */
public interface Action {
    /**
     * Applies or executes the Action. This runs the code that was
     * defined for the apply method in any concrete classes.
     */
    public void apply ();
    
    /**
     * Updates the action with the command that created the action.
     * This lets the apply method take in parameters or other things
     * that are related to the command.
     * 
     * @param command The command that triggered this action.
     */
    public void update (Command command);
}
