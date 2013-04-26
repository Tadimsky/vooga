package vooga.fighter.controller;

import java.awt.BorderLayout; 
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.Timer;

import arcade.games.ArcadeInteraction;
import arcade.games.Game;
import arcade.games.GameData;
import arcade.games.UserGameData;
import util.Pixmap;
import vooga.fighter.model.loaders.MapLoader;
import util.Text;
import vooga.fighter.view.Canvas;
import vooga.fighter.controller.ControllerManager;
import vooga.fighter.controller.GameInfo;
import util.input.*;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * 
 * @author Jack Matteucci
 *
 */

public class GameManager { //extends Game{
	public static final Dimension SIZE = new Dimension(800, 600);
    public static final String TITLE = "Fighter!";
    public static final int THREE_TOP_HIGH_SCORES = 3;
    private Canvas myCanvas;
    private ControllerManager myControllerManager;
    private GameInfo myGameInfo;
    private String myFilePathway;

    public GameManager(){//ArcadeInteraction arcade) {
    	//super(arcade);
    	setFilePathway();
        myCanvas = new Canvas(SIZE); 
        myGameInfo = new GameInfo(new MapLoader().getMapNames());
        //myGameInfo.setHighScores(getArcade().getHighScores(THREE_TOP_HIGH_SCORES));
        ControllerFactory factory = makeFactory(myCanvas,myFilePathway);
        ControlProgressionManager progressionmanager = makeProgression(factory.getMap()), myFilePathway);
        myControllerManager = makeManager(myCanvas, myGameInfo, factory, progressionmanager,myFilePathway);
        JFrame frame = new JFrame(TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // add our user interface components
        frame.getContentPane().add(myCanvas, BorderLayout.CENTER);
        // display them
        frame.pack();
        frame.setVisible(true);
	}
    
    
	 public void run (){
		 myControllerManager.run();
	    }
	 
	 protected ControllerFactory makeFactory(Canvas canvas, String pathway){
		 return new ControllerFactory(canvas, pathway);
	 }
	 
	 protected ControllerManager makeManager(Canvas canvas, GameInfo info, ControllerFactory factory,
			 ControlProgressionManager progression, String pathway){
		 return new ControllerManager(canvas, info, factory, progression, pathway);
	 }
	 
	 protected ControlProgressionManager makeProgression(Map map, String pathway){
		 return new ControlProgressionManager(map, pathway);
	 }
	 		

	 public UserGameData generateNewProfile(){
		return myGameInfo;
	 }
	 
	 protected void setFilePathway(){
		 myFilePathway = "vooga.fighter.config";
	 }
	 
	 


}
