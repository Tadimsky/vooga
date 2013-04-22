package vooga.rts.action;

import vooga.rts.commands.Command;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;

/**
 * Interactive actions are an implementation of actions designed to be used 
 * with interactive entities (i.e. entities that can be controlled by the player).
 * The important aspect is that it contains the IE which it will act upon when 
 * called from that entities action list.
 * 
 * @author Challen Herzberg-Brovold
 */
public abstract class InteractiveAction implements Action {

    private InteractiveEntity myEntity;

    public InteractiveAction (InteractiveEntity ie) {
        myEntity = ie;
    }
    
    /**
     * 
     * @return returns the entity to be acted on by the actions apply() method
     */
    public InteractiveEntity getEntity () {
        return myEntity;
    }

    public void apply(InteractiveEntity i) {
    	return;
    }
    
	@Override
	public void apply() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Command command) {
		// TODO Auto-generated method stub
		
	}
}
