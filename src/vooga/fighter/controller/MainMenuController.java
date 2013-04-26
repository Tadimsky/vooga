package vooga.fighter.controller;

import vooga.fighter.controller.Controller;
import vooga.fighter.controller.ControllerDelegate;
import vooga.fighter.controller.GameInfo;
import vooga.fighter.view.Canvas;
import java.util.ResourceBundle;


/**
 * 
 * Details a MainMenuController class, where start game etc. selections are
 * @author Jack Matteucci
 * @author Jerry Li
 * 
 * This is a great class to reference when trying to understand how to extend the
 * menu controller hierarchy
 * 
 */
public class MainMenuController extends MenuController {

    private ResourceBundle myResources;

    /**
     * Initial constructor. Called by ControllerFactory initially
     * using reflection. 
     */
    public MainMenuController () {
        super();
    }

    /**
     * Concrete constructor, used when controller is switched to 
     * @param name      name of controller
     * @param frame     canvas
     * @param manager   ControllerManager
     * @param gameinfo  GameInfo
     */
    public MainMenuController(String name, Canvas frame, ControllerDelegate manager, 
                              GameInfo gameinfo) {

        super(name, frame, manager, gameinfo);
        getGameInfo().reset();
        setInput(manager.getInput());
        getInput().addListenerTo(this);
    }

    /**
     * Returns concrete controller, used when controller is switched to
     */
    public Controller getController(String name, Canvas frame, ControllerDelegate manager, GameInfo gameinfo) {
        Controller controller = new MainMenuController(name, frame, manager, gameinfo);
        return controller;
    }

    /**
     * Checks this controller's end conditions and notifies the 
     * delegate
     */
    @Override
    public void notifyEndCondition(String choice) {
        removeListener();
        getMode().resetChoice();
        getManager().notifyEndCondition(getMode().getMenusNext(choice));
    }

    /**
     * Removes the input listener
     */
    public void removeListener(){
        super.removeListener();
        getInput().removeListener(this);
    }

    /**
     * Checks condition
     */
    public void checkConditions(){
        for(ModeCondition condition: getConditions()){
            if(condition.checkCondition(getMode())) notifyEndCondition(getMode().peekChoice());
        }
    }

}
