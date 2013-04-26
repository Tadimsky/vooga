package vooga.towerdefense.controller.modes;

import java.awt.Point;
import vooga.towerdefense.controller.Controller;
import vooga.towerdefense.gameElements.GameElement;

/**
 * The default game mode used when nothing is selected.
 * 
 * @author Jimmy Longley
 * @author Angelica Schwartz
 */
public class SelectMode extends ControlMode {

    private GameElement currentlySelectedItem;
    
    /**
     * handles a click on the map screen in Select mode.
     * @param p
     * @param controller
     */
    @Override
    public void handleMapClick (Point p, Controller controller) {
        currentlySelectedItem = controller.getItemAt(p);
        controller.displayElementInformation(currentlySelectedItem);
    }

    /**
     * handles mouse dragging on the map screen in Select mode.
     * @param p
     * @param controller
     */
    @Override
    public void handleMapMouseDrag (Point p, Controller controller) {
        //do nothing
    }
    
    /**
     * gets the currently selected item.
     * @return selected GameElement
     */
    public GameElement getCurrentlySelectedItem() {
        return currentlySelectedItem;
    }

}
