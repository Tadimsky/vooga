package vooga.scroller.sprites.superclasses;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import util.Location;
import vooga.scroller.level_management.IInputListener;
import vooga.scroller.scrollingmanager.ScrollingManager;
import vooga.scroller.sprites.interfaces.Locatable;
import vooga.scroller.util.ISpriteView;
import vooga.scroller.view.GameView;


/**
 * This is the superclass for any player the game designer creates. 
 * 
 * @author Jay Wang, Ross Cahoon, Scott Valentine
 * 
 */
public abstract class Player extends GameCharacter implements IInputListener, Locatable {

    private GameView myView;
    private Location myPaintCenter;
    private ScrollingManager myScrollingManager;

    public Player (ISpriteView image,
                   Location center,
                   Dimension size,
                   GameView gameView,
                   ScrollingManager sm, 
                   int health, 
                   int damage) {
        super(image, center, size, health, damage);
        myView = gameView;
        myPaintCenter = new Location(myView.getWidth() / 2, myView.getHeight() / 2);
        myScrollingManager = sm;
    }

    @Override
    public void update (double elapsedTime, Dimension bounds) {
        super.update(elapsedTime, bounds);
        myPaintCenter = myScrollingManager.playerPaintLocation(this);       
    }

    @Override
    public void paint (Graphics2D pen) {
        
        Point2D currentLocal = new Location(this.getCenter());
        this.setCenter(myPaintCenter.getX(), myPaintCenter.getY());
        
        super.paint(pen);
        this.setCenter(currentLocal.getX(), currentLocal.getY());
    }

    /**
     * Gives the location at which this sprite will be painted.
     * 
     * @return
     */
    public Location getPaintLocation () {
        return myPaintCenter;
    }
}
