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

import vooga.fighter.util.Pixmap;
import vooga.fighter.util.Text;
import vooga.fighter.view.Canvas;
import vooga.fighter.controller.ControllerManager;
import vooga.fighter.controller.GameInfo;
import vooga.fighter.game.*;
import vooga.fighter.input.Input;
import vooga.fighter.input.InputClassTarget;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;



public class GameManager{
	public static final Dimension SIZE = new Dimension(800, 600);
    public static final String TITLE = "Fighter!";
    private Canvas myCanvas;
    private ControllerManager myControllerManager;
    private GameInfo myGameInfo;
  

    public GameManager() {
        myCanvas = new Canvas(SIZE); 
        //DisplayMode dm = new DisplayMode(SIZE.width,SIZE.height, 16, DisplayMode.REFRESH_RATE_UNKNOWN);
        // container that will work with user's OS
        JFrame frame = new JFrame(TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // add our user interface components
        frame.getContentPane().add(myCanvas, BorderLayout.CENTER);
        // display them
        frame.pack();
        frame.setVisible(true);
        myGameInfo = new GameInfo();
        myControllerManager = new ControllerManager(myCanvas, myGameInfo);
	}
    
    
	 public void run (){
		 myControllerManager.run();
	    }

}
