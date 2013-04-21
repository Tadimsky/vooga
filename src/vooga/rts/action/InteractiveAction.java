package vooga.rts.action;

import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.units.Unit;

public abstract class InteractiveAction implements Action {
    
    private InteractiveEntity myEntity;
    public InteractiveAction(InteractiveEntity ie) {
        myEntity = ie;
    }
    
    public InteractiveEntity getEntity() {
        return myEntity;
    }
    
    public void apply(InteractiveEntity i){
    	return;
    }
}
