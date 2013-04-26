package vooga.scroller.model;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import vooga.scroller.level_editor.controllerSuite.LEController;
import vooga.scroller.level_editor.library.BackgroundLib;
import vooga.scroller.level_editor.library.ISpriteLibrary;
import vooga.scroller.level_management.splash_page.SplashPage;
import vooga.scroller.scrollingmanager.ScrollingManager;
import vooga.scroller.sprites.superclasses.Player;
import vooga.scroller.util.PlatformerConstants;
import vooga.scroller.view.GameView;
import arcade.games.ArcadeInteraction;
import arcade.games.Game;

/**
 * 
 * @author Scrolling Platformer Team
 *
 */
public abstract class ScrollerGame extends Game {
    private Model myModel;
    private GameView myDisplay;
    private ScrollingManager myScrollingManager;
    private Player myPlayer;
    private String myTitle;
    private String[] myLevels;
    private SplashPage mySplashPage;

    public ScrollerGame (ArcadeInteraction arcade) {
        super(arcade);
        intializeInstanceVariables();
        makeModel();
    }
    
    private void intializeInstanceVariables() {
        myScrollingManager = setScrollingManager();
        myDisplay = new GameView(PlatformerConstants.DEFAULT_WINDOW_SIZE, myScrollingManager);
        myPlayer = setPlayer(myScrollingManager,myDisplay);
        myLevels = setLevelFileNamesPath();
        myTitle = setTitle();
        mySplashPage = setSplashPage();
    }

    protected abstract ScrollingManager setScrollingManager ();
    
    protected abstract Player setPlayer (ScrollingManager sm, GameView gameView);
    
    protected abstract String setTitle ();

    protected abstract String[] setLevelFileNames();
    
    protected abstract String setLevelsDirPath();
    
    protected abstract SplashPage setSplashPage();
    
    private String[] setLevelFileNamesPath() {
        String[] res = new String[setLevelFileNames().length];
        for (int i=0; i<res.length; i++) {
            res[i]=setLevelsDirPath()+setLevelFileNames()[i];
        }
        return res;
    }
    
    private void makeModel() {
        myModel = new Model(myDisplay, myScrollingManager, myPlayer, mySplashPage, myLevels);
        myModel.addPlayerToLevel();
        myDisplay.setModel(myModel);
    }

    @Override
    public void run() {
     // container that will work with user's OS
        JFrame frame = new JFrame(myTitle);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // add our user interface components
        frame.getContentPane().add(myDisplay, BorderLayout.CENTER);
        // display them
        frame.pack();
        frame.setVisible(true);
        // start animation
        myDisplay.start();
    }
    
    public static void runLevelEditor(ISpriteLibrary lib, String...filenames){
        BackgroundLib bgLib = new BackgroundLib(filenames);
        LEController con = new LEController(lib,bgLib);
        con.start();
    }
    
    public GameView getDisplay(){
        return myDisplay;
    }
    
    protected ScrollingManager getScrollingManager(){
        return myScrollingManager;
    }
    

    
}
