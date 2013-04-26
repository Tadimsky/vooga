package vooga.scroller.level_editor.level_state;

import java.awt.Dimension;
import java.awt.Graphics2D;
import vooga.scroller.level_management.SpriteManager;
import vooga.scroller.sprites.state.SpriteState;
import vooga.scroller.view.GameView;

/**
 * Default State for a level.
 * 
 * @author Scott Valentine
 *
 */
public class DefaultState implements LevelState {

    private SpriteManager mySpriteManager;
    
    public DefaultState(SpriteManager spriteManager){
        mySpriteManager = spriteManager;
    }


    @Override
    public void paint (Graphics2D pen) {
        mySpriteManager.paint(pen);
    }

    @Override
    public void update (double elapsedTime, Dimension bounds, GameView gameView) {
        mySpriteManager.updateSprites(elapsedTime, bounds, gameView);
    }

}
