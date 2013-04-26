package vooga.fighter.controller;

import util.Location;
import util.input.AlertObject;
import util.input.Input;
import util.input.InputClassTarget;
import util.input.InputMethodTarget;
import util.input.PositionObject;
import vooga.fighter.controller.Controller;
import vooga.fighter.controller.ControllerDelegate;
import vooga.fighter.controller.GameInfo;
import vooga.fighter.controller.OneVOneController;
import vooga.fighter.model.*;
import vooga.fighter.model.objects.MouseClickObject;
import vooga.fighter.util.Paintable;
import vooga.fighter.view.Canvas;

import java.awt.Dimension;
import java.util.List;
import java.util.ResourceBundle;


/**
 * Details a map select controller, where you can select maps
 * 
 * 
 * @author Jack Matteucci 
 * @author Jerry Li
 */


public class MapSelectController extends MenuController {
    
    /**
     * Initial constructor
     */
    public MapSelectController () {
        super();
    }
    
    /**
     * Concrete constructor, called when level is switched to by controllermanager
     * @param name      name of controller
     * @param frame     canvas
     * @param manager   ControllerManager
     * @param gameinfo  GameInfo
     */
    public MapSelectController(String name, Canvas frame, ControllerDelegate manager, 
                GameInfo gameinfo) {
        super(name, frame, manager, gameinfo);
    }
    
    /**
     * Returns concrete controller, used when level is switched to by controllermanager
     */
    public Controller getController(String name, Canvas frame, ControllerDelegate manager, GameInfo gameinfo) {
        Controller controller = new MapSelectController(name, frame, manager, gameinfo);
        return controller;
    }
    
    /**
     * Notifies the delegate when controller ends
     */
    public void notifyEndCondition(String choice) {
    	removeListener();
    	getMode().resetChoice();
		getGameInfo().setMapName(choice);
		getManager().notifyEndCondition(getMode().getMenusNext(choice));
    	}

    /**
     * Removes input
     */
    public void removeListener(){
    	super.removeListener();
    	getInput().removeListener(this);
    }
    
    /**
     * Checks conditions
     */
    public void checkConditions(){
    	for(ModeCondition condition: getConditions())
    		if(condition.checkCondition(getMode())) notifyEndCondition(getMode().peekChoice());
    }

}

