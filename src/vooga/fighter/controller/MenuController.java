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
import vooga.fighter.model.objects.CharacterObject;
import vooga.fighter.model.objects.MenuObject;
import vooga.fighter.model.objects.MouseClickObject;
import vooga.fighter.model.objects.MouseObject;
import vooga.fighter.util.CollisionManager;
import vooga.fighter.util.Paintable;
import vooga.fighter.view.Canvas;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import util.input.*;
import java.util.ResourceBundle;


/**
 * Abstract MenuController class, details a class where
 * menu objects perform some functionality when selected
 * 
 * @author Jerry Li 
 * @author Jack Matteucci
 * 
 */
@InputClassTarget
public abstract class MenuController extends Controller {

    private static final String INPUT_PATHWAY = "vooga.fighter.config.menudefault";
    private List<ModeCondition> myEndConditions;
    
    /**
     * Initial constructor, used in reflection
     */
    public MenuController () {
        super();
    }
    
    /**
     * Concrete constructor, used when ControllerManager switches to this controller
     * @param name      name of controller
     * @param frame     canvas
     * @param manager   ControlerManager
     * @param gameinfo  GameInfo
     */
    public MenuController(String name, Canvas frame, ControllerDelegate manager, 
    		GameInfo gameinfo) {
    	super(name, frame, manager, gameinfo);
        setInput(manager.getInput());
        getInput().replaceMappingResourcePath(INPUT_PATHWAY);
        getInput().addListenerTo(this);
    	DisplayLoopInfo LoopInfo =  new DisplayLoopInfo(super.getMode());
    	setLoopInfo(LoopInfo);
    	myEndConditions = new ArrayList<ModeCondition>();
    	setupConditions();
    }
    
    /**
     * Loads the mode associated with MenuController (MenuMode)
     */
    public void loadMode() {
        Mode mode = new MenuMode(new CollisionManager(), super.getName());
        super.setMode(mode);
    }
    public void initializeMode () {
        MenuGrid grid = new MenuGrid(getMode().getName(), getMode());
        getMode().setMenuGrid(grid);
        getMode().setMenuObjects(grid.getMenuObjects());
        getMode().update();
    }
    
    /**
     * Creates mouse object when mouse is clicked
     * @param pos
     */
    @InputMethodTarget(name = "continue")
    public void mouseclick(PositionObject pos)  {
        getMode().addObject(new MouseClickObject(pos.getPoint2D()));
    }
    
    /**
     * Creates mouse object when mouse moves
     * @param pos
     */
    @InputMethodTarget(name = "move")
    public void mousemove(PositionObject pos)  {
        getMode().addObject(new MouseObject(pos.getPoint2D()));
    }
    
    /**
     * 
     */
    public void developerUpdate(){
        
    }
    
    @InputMethodTarget(name = "left")
    public void left(AlertObject alObj)  {
    	if(getMode().inputReady()) getMode().left();
    }
    
    @InputMethodTarget(name = "right")
    public void right(AlertObject alObj)  {
    	if(getMode().inputReady()) getMode().right();
    }
    
    @InputMethodTarget(name = "up")
    public void up(AlertObject alObj)  {
    	if(getMode().inputReady())  getMode().up();
    }
    
    @InputMethodTarget(name = "down")
    public void down(AlertObject alObj)  {
    	if(getMode().inputReady()) getMode().down();
    }
    
    @InputMethodTarget(name = "enter")
    public void enter(AlertObject alObj)  {
    	if(getMode().inputReady()) getMode().setChoice(getMode().getCurrentMenu().getValue());
    }
    
    public MenuMode getMode(){
    	return (MenuMode) super.getMode();
    }
    
    public Controller getController() {
        return this;
    }
    
    public void removeListener(){
    	super.removeListener();
    	getInput().removeListener(this);
    }
    
    protected void addEndCondition(ModeCondition condition){
    	myEndConditions.add(condition);
    }
    
    protected List<ModeCondition> getConditions(){
    	return myEndConditions;
    }
    
    
    public void setupConditions(){
    	addEndCondition(endcondition);
    }
    
    ModeCondition endcondition = new ModeCondition() {
    	public boolean checkCondition(Mode mode) {
			return !("".equals(getMode().peekChoice()));
    	}
    };

}
