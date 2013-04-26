package vooga.scroller.level_management.splash_page;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import util.input.Input;
import util.input.InputClassTarget;
import util.input.InputMethodTarget;
import vooga.scroller.marioGame.spritesDefinitions.MarioLib;
import vooga.scroller.scrollingmanager.ScrollingManager;
import vooga.scroller.sprites.Sprite;
import vooga.scroller.util.ISpriteView;
import vooga.scroller.util.Pixmap;
import vooga.scroller.view.GameView;

/**
 * 
 * Splash page uses for testing purposes. This can be used by the LevelEditor.
 * @author Scott Valentine
 *
 */
@InputClassTarget
public class TestSplashPage extends SplashPage {

    private static final ISpriteView DEFAULT_IMAGE = new Pixmap("/vooga/scroller/images/" ,"test_splash_page.png");
    private static final String INPUT_LOCATION = "vooga/scroller/resources/TestSplashMapping";
    private static final int DEFAULT_ID = 0;
    
    public TestSplashPage (GameView gameView,
                           ScrollingManager sm) {
        super(DEFAULT_IMAGE, DEFAULT_ID, gameView, sm);
    }

    @Override
    public List<Sprite> getSprites () {
        // TODO Auto-generated method stub
        return new ArrayList<Sprite>();
    }

    @Override
    public void addInputListeners (Input input) {
        input.replaceMappingResourcePath(getInputFilePath());
        input.addListenerTo(this);

    }

    @Override
    public void removeInputListeners (Input input) {
        input.removeListener(this);

    }

    @Override
    public String getInputFilePath () {
        // TODO Auto-generated method stub
        return INPUT_LOCATION;

    }
    
    @InputMethodTarget(name = "start")
    public void nextLevel () {
        getDoor().goToNextLevel(getPlayer());
    }
    

}
