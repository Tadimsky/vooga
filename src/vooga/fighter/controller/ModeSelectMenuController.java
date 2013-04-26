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
 * 
 * @author Jerry Li 
 * @author Jack Matteucci
 * 
 */


public class ModeSelectMenuController extends MenuController {
	
    private static final String FILE_NAME = "vooga.fighter.config.ModeCharacterNumbers";
    private ResourceBundle myResources;
    
    public ModeSelectMenuController () {
        super();

    }
        
    public ModeSelectMenuController(String name, Canvas frame, ControllerDelegate manager, 
                GameInfo gameinfo) {
        super(name, frame, manager, gameinfo);
        myResources = ResourceBundle.getBundle(FILE_NAME);
    }
    
    public Controller getController(String name, Canvas frame, ControllerDelegate manager, GameInfo gameinfo) {
        Controller controller = new ModeSelectMenuController(name, frame, manager, gameinfo);
        return controller;
    }
    /**
     * Checks this controller's end conditions
     */
    
    public void notifyEndCondition(String choice) {
    	removeListener();
    	getMode().resetChoice();
    		getGameInfo().setGameMode(choice);
    		System.out.println(choice);
    		getGameInfo().setNumCharacters(Integer.parseInt(myResources.getString(choice)));    		
    		getManager().notifyEndCondition(getMode().getMenusNext(choice));
    		
    }

    public void removeListener(){
    	super.removeListener();
    	getInput().removeListener(this);
    }
    
    public void checkConditions(){
    	for(ModeCondition condition: getConditions())
    		if(condition.checkCondition(getMode())) notifyEndCondition(getMode().peekChoice());
    }

    

}
