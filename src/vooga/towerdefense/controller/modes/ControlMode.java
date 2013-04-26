package vooga.towerdefense.controller.modes;

import java.awt.Point;
import vooga.towerdefense.controller.Controller;
import vooga.towerdefense.gameElements.GameElement;


/**
 * A class used to help the controller figure out how to handle input from the
 * view.
 * 
 * @author Jimmy Longley
 * @author Angelica Schwartz
 */
public abstract class ControlMode {

    /**
     * handles a click on the map screen.
     * @param p
     * @param controller
     */
    public abstract void handleMapClick (Point p, Controller controller);
    
    /**
     * handles mouse dragging on the map screen.
     * @param p
     * @param controller
     */
    public abstract void handleMapMouseDrag (Point p, Controller controller);

}
