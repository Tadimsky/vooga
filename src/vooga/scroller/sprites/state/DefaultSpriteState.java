package vooga.scroller.sprites.state;

import java.awt.Dimension;
import java.awt.Graphics2D;
import util.Vector;
import vooga.scroller.sprites.Sprite;

public class DefaultSpriteState extends SpriteState{

    private static final int PRIORITY = Integer.MAX_VALUE;
    
    @Override
    public void update (Sprite sprite, double elapsedTime, Dimension bounds) {
        sprite.updateLastLocation();
        
        // do not change original velocity
        Vector v = new Vector(sprite.getVelocity());
        v.scale(elapsedTime);
        sprite.translate(v);
        
    }

    @Override
    public void paint (Sprite sprite, Graphics2D pen) {
        sprite.getView().paint(pen, sprite.getCenter(), sprite.getSize(), 0);
    }


    @Override
    public int getPaintPriority () {
        return PRIORITY;
    }

    @Override
    public void activate (Sprite sprite) {
        // does nothing special
        
    }

    @Override
    public void deactivate (Sprite sprite) {
        // does nothing special
        
    }

}
