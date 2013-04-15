package vooga.fighter.controller;



import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Timer;

import util.Location;
import vooga.fighter.input.*;
import vooga.fighter.game.LevelMode;
import vooga.fighter.game.Mode;
import vooga.fighter.input.Input;
import vooga.fighter.input.InputClassTarget;
import vooga.fighter.util.Paintable;
import vooga.fighter.view.Canvas;


/**
 * 
 * @author Jerry Li
 * 
 * @Modified by Jack Matteucci
 * 
 */
@InputClassTarget

public class LevelController extends Controller {
    private static final String INPUT_PATHWAY = "vooga.fighter.input.Game1Mapping_en_US";

    public LevelController (String name, Canvas frame) {
        super(name, frame);
    }
	
    public LevelController(String name, Canvas frame, ControllerDelegate manager, 
    		GameInfo gameinfo) {
    	super(name, frame, manager, gameinfo);
    	//frame.setViewDataSource(this);
    	loadMode();
    	GameLoopInfo gameLoopInfo = new GameLoopInfo((LevelMode) super.getMode());
    	setGameLoopInfo(gameLoopInfo);
    	frame.setViewDataSource(gameLoopInfo);
    	
    }

    
    public void loadMode() {
        List<Integer> characterNames = myGameInfo.getCharacters();
        int mapID = myGameInfo.getMapName();
        Mode temp = new LevelMode(this, characterNames, mapID);
        setMode(temp);
    }


    /**
     * Checks special occurences of game state.
     */
    public void notifyEndCondition(String string) {
        
    }


    @Override
    public Controller getController (ControllerDelegate delegate, GameInfo gameinfo) {
        return new LevelController(super.getName(), super.getView(),
                                   delegate, gameinfo);
    }

    @Override
    protected Input makeInput () {
        Input input = new Input(INPUT_PATHWAY, super.getView());
        input.addListenerTo(this);
    	return input;
    }
    
    

	@Override
	public void notifyEndCondition() {
		// TODO Auto-generated method stub
		
	}

	@InputMethodTarget(name="jump")
	public void PlayerOneJump(){
		//fill in the method here
	}
    
}