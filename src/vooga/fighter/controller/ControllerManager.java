
package vooga.fighter.controller;

import util.input.*;

import java.awt.Dimension;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.input.Input;
import vooga.fighter.view.Canvas;
/**
 * Handles switching controllers. Uses ControllerFactory and 
 * ControlProgressionManager
 * 
 * @author Jack Matteucci
 * @author Jerry Li
 */

public class ControllerManager implements ControllerDelegate{
        
       
	private Map<String, Controller> myControllerMap;
	private List<Controller> myControllerList;
	private Controller myCurrentController;
	private Canvas myCanvas;
	private GameInfo myGameInfo;
	private ControlProgressionManager myProgressionManager;
	private static final String INPUT_PATHWAY = "vooga.fighter.config.menudefault";
	private Input myInput;
	
	/**
	 * Constructs a ControllerManager
	 * @param frame        Canvas
	 * @param gameinfo     GameInfo
	 * @param factory      Factory
	 * @param progressionmanager   ControlProgressionManager(Handles logic of switching)
	 */
	public ControllerManager(Canvas frame, GameInfo gameinfo, ControllerFactory factory,
			ControlProgressionManager progressionmanager) {
		myCanvas = frame;
		myInput = new Input(INPUT_PATHWAY, myCanvas);
		myControllerMap = factory.getMap();
		myGameInfo = gameinfo;
		myProgressionManager = progressionmanager;
		//myProgressionManager.setControllerProgression(myControllerMap);
		myCurrentController = myProgressionManager.getNextController("MainMenu");
		String name = myCurrentController.getName();
		myCurrentController = myCurrentController.getController(name, frame, this, gameinfo);
	}
	
	/**
	 * Starts current controller
	 */
	public void run(){
		myCurrentController.start();
	}
	
	/**
	 * the implemented ControllerDelegate method
	 */
	public void notifyEndCondition(String string) {
		switchController(string);
	}
	
	/**
	 * Switches controllers
	 * @param condition    the controller name
	 */
	private void switchController(String condition) {
		myCurrentController.stop();
		myCurrentController = myProgressionManager.getNextController(condition);
		System.out.println("<controllermanager> now the controller is: " + myCurrentController.getName() );
		myCurrentController = myCurrentController.getController();
		String name = myCurrentController.getName();
		myCurrentController = myCurrentController.getController(name, myCanvas, this, myGameInfo);
		myCurrentController.start();	
	}      
	
	/**
	 * Exits game
	 */
	public void exit(){
		System.exit(0);
	}
	
	/**
	 * Returns input
	 */
	public Input getInput() {
	    return myInput;
	}
}
