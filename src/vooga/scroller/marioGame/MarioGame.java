
package vooga.scroller.marioGame;
import java.awt.Dimension;
import arcade.games.ArcadeInteraction;
import arcade.games.GameData;
import arcade.games.UserGameData;
import util.Location;
import vooga.scroller.level_management.splash_page.SplashPage;
import vooga.scroller.marioGame.splash_page.MarioSplashPage;
import vooga.scroller.marioGame.spritesDefinitions.MarioLib;
import vooga.scroller.marioGame.spritesDefinitions.players.Mario;
import vooga.scroller.model.ScrollerGame;
import vooga.scroller.scrollingmanager.OmniScrollingManager;
import vooga.scroller.scrollingmanager.ScrollingManager;
import vooga.scroller.sprites.superclasses.Player;
import vooga.scroller.util.Pixmap;
import vooga.scroller.view.GameView;


/**
 * A sample scroller game. By implementing the methods required by ScrollerGame,
 * the designer defines the components of the game. Most important, is the get LevelFileNames method.
 */

public class MarioGame extends ScrollerGame {

    // constants
    public static final String TITLE = "Mario Demo";
    public static final String LEVELS_DIR = "src/vooga/scroller/marioGame/sampleLevels/";
    public static final String SPLASH_DIR = "MARIO SPLASH.png";


    /**
     * main --- where the program starts
     * @param args
     */
    public static void main (String args[]) {
        // view of user's content
        ScrollerGame test = new MarioGame(null);
        test.run();
    }


    public MarioGame (ArcadeInteraction arcade) {
        super(arcade);
    } 

    @Override
    protected String[] setLevelFileNames () {
        String[] levelsFiles = {"longlevel.level"};
        return levelsFiles;
    }



    @Override
    protected String setTitle () {
        return TITLE;
    }



    @Override
    protected ScrollingManager setScrollingManager () {
        return new OmniScrollingManager();
    }



    @Override
    protected Player setPlayer (ScrollingManager sm, GameView gameView) {
        return new Mario(new Location(), new Dimension(20, 32), gameView, sm);
    }



    @Override
    protected String setLevelsDirPath () {
        return LEVELS_DIR;

    }

    @Override
    public UserGameData generateNewProfile () {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public GameData generateNewGameProfile () {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    protected SplashPage setSplashPage () {
        return new MarioSplashPage(MarioLib.makePixmap("MARIO SPLASH.png"), 0, getDisplay(), getScrollingManager());
    }

}
