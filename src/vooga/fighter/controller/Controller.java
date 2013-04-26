package vooga.fighter.controller;

import vooga.fighter.view.Canvas;
import vooga.fighter.game.*;


import util.Location;
import util.Pixmap;
import util.input.Input;
import vooga.fighter.model.*;
import vooga.fighter.view.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.util.List;
import util.input.*;
import javax.swing.Timer;




/**
 * 
 * @author Jerry Li
 * @author Jack Matteucci
 *
 * This class is the basic controller from which all subclasses extend.  Only should
 * you subclass this controller if there are major changes to be made to the actual
 * mode, as subclassing this controller gives you the freedom to choose what mode it 
 * is attached to.
 *
 *
 */

public abstract class Controller{
    public static final String DEFAULT_RESOURCE_PACKAGE = "vooga.fighter.config.";
    public static final String DEFAULT_IMAGE_PACKAGE = "vooga.fighter.images.";
    public static final String NEXT = "Next";
    public static final String BACK = "Back";
    public static final String EXIT = "EXIT";
    public static final String SPLASH = "Splash";
    public static final String CONTROL = "Control";
    private String myFilepath;
    
    protected ControllerDelegate myManager;
    private String myName;
    private String myPath;
    private Canvas myCanvas;
    private GameInfo myGameInfo;
    private ResourceBundle mySplashResource;
    private String mySplashPath;

    private Input myInput;
    public static final int FRAMES_PER_SECOND = 25;
    // better way to think about timed events (in milliseconds)
    public static final int ONE_SECOND = 1000;
    public static final int DEFAULT_DELAY = ONE_SECOND / FRAMES_PER_SECOND;
    private Timer myTimer;
    private Mode myMode;
    private DisplayInfo myDisplayInfo;

    /**
     * Constructor
     */
    public Controller(){
    
    }
    
    /**
     * sets this controller's name
     * @param name
     */
    public void initializeName(String name) {
        myName = name;
    }

    /**
     * Constructor
     * @param name
     * @param frame
     * @param manager
     * @param gameinfo
     */
    public Controller(String name, Canvas frame, ControllerDelegate manager, GameInfo gameinfo) {
        myName = name;
        myCanvas = frame;
        mySplashResource = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + SPLASH);
        mySplashPath = DEFAULT_IMAGE_PACKAGE+ mySplashResource.getString(CONTROL);
        myManager = manager;
        myGameInfo = gameinfo;
        loadMode();
    }
    
    /**
     * Instantiates a new controller with input parameters
     * @param name - name of this controller
     * @param frame - View for this controller
     * @param manager - the ControllerDelegate manager that handles switching between controllers
     * @param gameinfo - the GameInfo object that holds general data about the current 
     * 					game state (num players, gameMode, etc.)
     * @return the newly instantiated controller
     */
    public abstract Controller getController(String name, Canvas frame, ControllerDelegate manager, GameInfo gameinfo);
    
    /**
     * sets this Controller's input
     * @param input
     */
    protected void setInput(Input input) {
    	myInput = input;
    }

    /**
     * returns this Controller's name
     * @return myName
     */
    public String getName() {
        return myName;
    }
    
    /**
     * sets the name of this controller
     * @param name
     */
    public void setName(String name) {
        myName = name;
    }
    
    /**
     * gets this controller's input object
     * @return myInput
     */
    protected Input getInput() {
    	return myInput;
    }

    /**
     * gets this controller's view
     * @return myCanvas
     */
    protected Canvas getView() {
        return myCanvas;
    }

    /**
     * gets this controller's filepath
     * @return myPath
     */
    protected String getPath() {
        return myPath;
    }
    
    /**
     * gets this controller's manager. The manager is the class that handles
     * switching of controllers.
     * @return myManager
     */
    protected ControllerDelegate getManager() {
        return myManager;
    }

    /**
     * returns this controller's GameInfo
     * @return myGameInfo
     */
    protected GameInfo getGameInfo() {
        return myGameInfo;
    }
    
    /**
     * returns this controller's mode
     * @return myMode
     */
    protected Mode getMode() {
    	return myMode;
    }

    /**
     * sets this controller's mode and initializes it
     * @param mode
     */
    protected void setMode(Mode mode) {
        myMode = mode;
        initializeMode();
    }
    
    /**
     * This method is to used by any subclasses as it loads the Mode in 
     * this superclass's constructer (so that it happens before any Mode methods are called)
     */
    public abstract void loadMode();
    
    /**
     * sets the loopinfo of this controller
     * @param loopinfo - contains painting info for this controller
     */
    protected void setLoopInfo(DisplayInfo loopinfo){
    	myDisplayInfo = loopinfo;
    	myCanvas.setViewDataSource(myDisplayInfo);
    }

    /**
     * displays the splash screen for this controller, if subclassed and overwritten
     */
    protected void displaySplash() {
    }
    
    /**
     * creates the splash screen for this controller, if subclassed and overwritten
     */
    protected void generateSplash() {
    }

    /**
     * starts this controller. starts the timer and begins the update/paint loop.
     */
    public void start() {
        final int stepTime = DEFAULT_DELAY;
        // create a timer to animate the canvas
         myTimer = new Timer(stepTime, 
                               new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                myMode.update();
                myDisplayInfo.update();
                myCanvas.paint();
                checkConditions();
                
            }
        });
        // start animation
        myTimer.start();
    }

    /**
     * Exits program.
     */
    public void exit () {
        System.exit(0);
    }

    /**
     * stops this controller's timer
     */
    public void stop () {
        myTimer.stop();

    }

    /**
     * removes this controller's input listener.
     */
    protected void removeListener() {
    	getInput().removeListener(this);
    }
    
    /**
     * checks for the completion of end conditions
     */
    protected abstract void checkConditions();
 
    /**
     * Notifies the controllerdelegate that the current game state is completed
     * @param choice
     */
    protected abstract void notifyEndCondition(String choice);
    
    /**
     * instantiates a new controller
     * @return this
     */
    public abstract Controller getController();
    
    /**
     * Update for special cases desired by the developer
     */
    protected abstract void developerUpdate();
    
    /**
     * initializes this controller's mode
     */
    protected abstract void initializeMode();

        


}
