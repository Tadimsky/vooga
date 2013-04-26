package games.scroller.marioGame.spritesDefinitions.players.resources;

import java.awt.Dimension;
import java.awt.Graphics2D;
import vooga.scroller.sprites.Sprite;
import vooga.scroller.sprites.state.SpriteState;




public class InvisibleState extends SpriteState{
    
    private static final int PRIORITY = Integer.MIN_VALUE;

    
    @Override
    public void update (Sprite sprite, double elapsedTime, Dimension bounds) {
        // does nothing extra        
    }

    @Override
    public void paint (Sprite sprite, Graphics2D pen) {
        // Invisible, do not paint.
    }

    @Override
    public int getPaintPriority () {
        return PRIORITY;
    }

    @Override
    public void activate (Sprite sprite) {
        // nothign special
        
    }

    @Override
    public void deactivate (Sprite sprite) {
        // nothing special
        
    }


}
