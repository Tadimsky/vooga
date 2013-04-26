package vooga.scroller.level_editor.level_state;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import util.Location;
import util.Text;
import vooga.scroller.level_management.SpriteManager;
import vooga.scroller.view.GameView;
/**
 * Represents the state where a level is paused. This turns off updates and paints of sprites
 * and turns off player-controlling inputs. This state cannot be resued for multiple level
 * Each level that is paused requires one instance of this class in its level state manager 
 * 
 * @author Scott Valentine
 *
 */
public class PausedState implements LevelState {

    
    
    private static final String DEFAULT_PAUSED_MESSAGE = "PAUSED";
    private static final Color DEFAULT_COLOR = Color.BLACK;
    private static final Location DEFAULT_LOCATION = new Location(100,100);
    
    private Text myMessage;
    private Location myTextLocation;
    private SpriteManager mySpriteManager;
    
    /**
     * Creates a new paused state that has access to all of the sprites (these are used
     * for painting while the game is paused).
     * 
     * @param spriteManager is the manager of all the sprites in a level.
     */
    public PausedState(SpriteManager spriteManager){
        myMessage = new Text(DEFAULT_PAUSED_MESSAGE);
        myTextLocation = DEFAULT_LOCATION;
        mySpriteManager = spriteManager;
    }


    @Override
    public void paint (Graphics2D pen) {
        // paints only the paused header so the user knows that the game is paused
        mySpriteManager.paint(pen);
        myMessage.paint(pen, myTextLocation, DEFAULT_COLOR);
    }


    @Override
    public void update (double elapsedTime, Dimension bounds, GameView gameView) {
        // TODO Auto-generated method stub       
        myTextLocation = new Location(gameView.getWidth()/2, gameView.getHeight()/2);
        
    }

}
