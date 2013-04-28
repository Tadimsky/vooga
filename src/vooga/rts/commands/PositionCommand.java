package vooga.rts.commands;

import util.Location;
import util.input.PositionObject;


/**
 * A Position Command is different from a Command in that it stores the position
 * of where the command took place
 * 
 * @author Challen Herzberg-Brovold
 * 
 */
public class PositionCommand extends Command {

    public static String MOUSE_MOVE = "mousemove";

    public Location myPosition;

    /**
     * 
     * @param inputName
     * @param position the position of the command
     */
    public PositionCommand (String inputName, PositionObject position) {
        super(inputName);
        myPosition = new Location(position.getX(), position.getY());
    }

    /**
     * 
     * @return the position of the command.
     */
    public Location getPosition () {
        return myPosition;
    }
}
