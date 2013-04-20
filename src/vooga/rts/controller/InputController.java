package vooga.rts.controller;

import java.awt.geom.Rectangle2D;
import util.input.*;
import vooga.rts.commands.Command;
import vooga.rts.commands.DragCommand;
import vooga.rts.state.State;
import vooga.rts.util.Camera;
import vooga.rts.util.Location;
import vooga.rts.util.Location3D;


/**
 * After much thought, I've decided to only have one InputController. This controller
 * sends the formatted inputs to the main state, which relays them to the appropriate
 * state.
 * 
 * @author Challen Herzberg-Brovold
 * 
 */
@InputClassTarget
public class InputController implements Controller {

    private State myState;

    private Location myLeftMouse;
    private Rectangle2D myDrag;

    public InputController (State state) {
        myState = state;
    }

    @Override
    public void sendCommand (Command command) {
        myState.receiveCommand(command);
    }

    @InputMethodTarget(name = "onLeftMouseDown")
    public void onLeftMouseDown (PositionObject o) {
        myLeftMouse = new Location(o.getPoint2D());
    }

    @InputMethodTarget(name = "onLeftMouseUp")
    public void onLeftMouseUp (PositionObject o) {
        if (myDrag == null) {
            sendCommand(new PositionCommand("leftclick", o));
        }
        else {
            sendCommand(new DragCommand("drag", myDrag));
        }
        myLeftMouse = null;
        myDrag = null;
    }

    @InputMethodTarget(name = "onRightMouseUp")
    public void onRightMouseUp (PositionObject o) {
        sendCommand(new PositionCommand("rightclick", o));
    }

    @InputMethodTarget(name = "onMouseMove")
    public void mouseMove (PositionObject o) {
        sendCommand(new PositionCommand("move", o));
    }

    @InputMethodTarget(name = "onMouseDrag")
    public void onMouseDrag (PositionObject o) {
        if (!myLeftMouse.equals(null)) {
            Location3D world = Camera.instance().viewtoWorld(o.getPoint2D());
            Location3D leftWorldMouse = Camera.instance().viewtoWorld(myLeftMouse);
            double uX = world.getX() > leftWorldMouse.getX() ? leftWorldMouse.getX() : world.getX();
            double uY = world.getY() > leftWorldMouse.getY() ? leftWorldMouse.getY() : world.getY();
            double width = Math.abs(o.getX() - myLeftMouse.getX());
            double height = Math.abs(o.getY() - myLeftMouse.getY());
            myDrag = new Rectangle2D.Double(uX, uY, width, height);
        }
    }
}
