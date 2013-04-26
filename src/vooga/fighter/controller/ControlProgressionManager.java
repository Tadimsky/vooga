package vooga.fighter.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Handles the logic of switching controllers
 * Right now juts gets the controller from a Map, but in the future,
 * if someone wants to implement a tournament system, they can subclass this
 * class.
 * 
 * @author Jack Matteucci
 * @author Jerry Li
 *
 */
public class ControlProgressionManager {
                
	private Map<String, Controller> myControllerMap;
	
	/**
	 * Constructs progression manager with map
	 * @param controllerMap
	 */
	public ControlProgressionManager(Map<String, Controller> controllerMap) {
		myControllerMap = controllerMap;
		
	}
	
	/**
	 * Returns the next controller by using name to get the controller
	 * @param condition    name of controller
	 * @return
	 */
	public Controller getNextController(String condition) {
	    try {
	        return myControllerMap.get(condition);
	    }
	    catch (Exception e) {
	        throw new NullPointerException("No such level"); 
	    }
	}
	


}

