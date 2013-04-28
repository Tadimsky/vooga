package vooga.rts.action;

import vooga.rts.commands.ClickCommand;
import vooga.rts.commands.Command;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.util.Camera;
import vooga.rts.util.Location3D;

public class MoveAction extends InteractiveAction {

    private Location3D myLocation;
    private InteractiveEntity myEntity;
    
    public MoveAction (InteractiveEntity ie) {
        super(ie);
    }
    
    @Override
    public void apply () {
        if (myLocation != null) {
            getEntity().move(myLocation);
        }
    }

    @Override
    public void update (Command command) {
        System.out.println("is this thing on?");
        ClickCommand click = (ClickCommand) command;
        myLocation = Camera.instance().viewtoWorld(click.getPosition());
        if (myLocation.getX() < 0 || myLocation.getY() < 0) {
            myLocation = null;
        }
    }

}
