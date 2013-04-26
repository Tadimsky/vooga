package vooga.scroller.level_management;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import util.Location;
import vooga.scroller.level_editor.Level;
import vooga.scroller.level_editor.controllerSuite.LEGrid;
import vooga.scroller.level_editor.model.LevelParser;
import vooga.scroller.level_management.splash_page.SplashPage;
import vooga.scroller.marioGame.spritesDefinitions.MarioLib;
import vooga.scroller.scrollingmanager.ScrollingManager;
import vooga.scroller.util.IGameComponent;
import vooga.scroller.util.Pixmap;
import vooga.scroller.view.GameView;


/**
 * Instantiates all of the levels for gameplay.
 * 
 * @author Scott Valentine, Dagbedji Fagnisse
 * 
 */
public class LevelFactory {

    private static String DEFAULT_LEVEL_FOLDER = "src/vooga/scroller/resources/sampleLevels/";
    private LevelManager myLevelManager;
    private LevelParser myLevelReader;
    private ScrollingManager mySM;
    private GameView myView;

    public LevelFactory (LevelManager lm, ScrollingManager sm, GameView gameView) {
        myLevelManager = lm;
        myLevelReader = new LevelParser();
        mySM = sm;
        myView = gameView;
    }

    public List<IGameComponent> generateLevels (String[] levelFileNames) {
        List<IGameComponent> gameComponents = new ArrayList<IGameComponent>();
        for (int i = 0; i < levelFileNames.length; i++) {
            Level curr = buildLevel(i + 1, loadGridFromFile(levelFileNames[i]));
            gameComponents.add(curr);
        }
    
        return gameComponents;
    }

    protected IGameComponent linkLevels (SplashPage splash, List<IGameComponent> levels) {
//        SplashPage splash =
//                new SplashPage(MarioLib.makePixmap(splashPage), 0, myView, mySM);
        splash.addDoor(new MarioLib.DoorPortal());
        myLevelManager.put(splash.getDoor(), levels.get(0));
    
        for (int i = 0; i < levels.size() - 1; i++) {
            myLevelManager.put(levels.get(i).getDoor(), levels.get(i + 1));
        }
        myLevelManager.put(levels.get(levels.size() - 1).getDoor(), splash);
    
        return splash;
    }

    private Level buildLevel (int id, LEGrid grid) {
        Level result = new Level(id, mySM, grid);
        return result;
    }

    private LEGrid loadGridFromFile (String filename) {
        // TODO: Factor this out. make editable.

        File f = (new File(filename)).getAbsoluteFile();
        LEGrid result = myLevelReader.makeGridFromFile(f);
        return result;
    }

}
