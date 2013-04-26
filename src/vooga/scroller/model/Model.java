package vooga.scroller.model;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import util.Location;
import vooga.scroller.level_editor.Level;
import vooga.scroller.level_management.LevelManager;
import vooga.scroller.level_management.splash_page.SplashPage;
import vooga.scroller.marioGame.spritesDefinitions.players.Mario;
import vooga.scroller.scrollingmanager.ScrollingManager;
import vooga.scroller.sprites.Sprite;
import vooga.scroller.sprites.animation.Animation;
import vooga.scroller.sprites.animation.MovingSpriteAnimationFactory;
import vooga.scroller.sprites.superclasses.Player;
import vooga.scroller.view.GameView;


/**
 * Represents a scrolling platformer.
 * *
 * 
 * @author Ross Cahoon
 * @author Jay Wang
 * @author Scott Valentine
 */

public class Model {
         
    private GameView myView;
    private Player myPlayer;
    private LevelManager myLevelManager;
    private ScrollingManager myScrollingManager;


    private static final String PLAYER_IMAGES = "walama.gif";
    // "mario.gif"
    //"walama.gif"
    private static final String DEFAULT_IMAGE_LOCATION = "/vooga/scroller/marioGame/images/";
    
    /**
     * Constructs a new Model based on the view and the scrolling manager used by the game.
     * 
     * @param gameView which is used to display/control game.
     * @param myScrollingManager used to control in-game scrolling.
     * @throws IOException 
     */

    public Model (GameView gameView, ScrollingManager sm, Player player, SplashPage splashPage, Level ...levels) {
        this(gameView, sm, player);
        myLevelManager = initializeLevelManager(splashPage, levels);
    }
    
    
    public Model (GameView gameView, ScrollingManager sm, Player player, SplashPage splashPage, String... levelFileNames) {
        this(gameView, sm, player);
        myLevelManager = initializeLevelManager(splashPage, levelFileNames);
    }

//    public Model (GameView gameView, ScrollingManager sm, Level level) {
//        this(gameView, sm, initTestPlayer(gameView, sm), level);
//    }


    private Model (GameView gameView, ScrollingManager sm, Player player) {
        myView = gameView;
        setScrollingManager(sm);
        myPlayer = player;
    }


    private static Player initTestPlayer (GameView gameView, ScrollingManager sm) {
        Player player = new Mario(new Location(), new Dimension(32, 32), gameView, sm);
        MovingSpriteAnimationFactory msaf = new MovingSpriteAnimationFactory(DEFAULT_IMAGE_LOCATION, PLAYER_IMAGES);
        Animation<Sprite> playerAnimation = msaf.generateAnimation(player);
        
        player.setView(playerAnimation);
        return player;
    }

    public void addPlayerToLevel () {
        myLevelManager.getCurrentLevel().addPlayer(myPlayer);
    }

    private LevelManager initializeLevelManager (SplashPage splashPage, Level[] levels) {
        return new LevelManager(myScrollingManager, myView, splashPage, levels);
    }


    private LevelManager initializeLevelManager (SplashPage splashPage, String[] levelFileNames) {
        return new LevelManager(myScrollingManager, myView, splashPage, levelFileNames);
    }


    private void setScrollingManager(ScrollingManager sm) {
        myScrollingManager = sm;
        myScrollingManager.initModel(this);
        myScrollingManager.initView(myView); 
    }

    /**
     * Draw all elements of the game.
     */
    public void paint (Graphics2D pen) {
        myLevelManager.getCurrentLevel().paint(pen);
    }

    /**
     * Updates all the game elements since the last update.
     * 
     * @param elapsedTime is the elapsed time since the last update.
     */
    public void update (double elapsedTime) {
        myLevelManager.getCurrentLevel().update(elapsedTime, myView.getSize(), myView);
    }

    /**
     * Gives various boundaries for the current level.
     * TODO: can these be consolidated into one function (seems like a lot extra things that aren't
     * really associated with the model.
     * (we could just return the current level or maybe the bounds of the level(this might be 
     * dependent on other things) )
     * 
     * @return
     */
    public double getRightBoundary () {
        return myLevelManager.getCurrentLevel().getRightBoundary();
    }

    public double getLeftBoundary () {
        return myLevelManager.getCurrentLevel().getLeftBoundary();
    }

    public double getUpperBoundary () {
        return myLevelManager.getCurrentLevel().getUpperBoundary();
    }

    public double getLowerBoundary () {
        return myLevelManager.getCurrentLevel().getLowerBoundary();
    }

    public Dimension getLevelBounds () {
        return myLevelManager.getCurrentLevel().getLevelBounds();
    }
    
    public Image getBackground() {
        return myLevelManager.getCurrentLevel().getBackground();
    }
    
    public Player getPlayer(){
        return myPlayer;
    }
    
}
