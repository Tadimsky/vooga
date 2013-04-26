package vooga.scroller.marioGame.spritesDefinitions.players.resources;

import java.awt.Dimension;
import java.awt.Graphics2D;
import vooga.scroller.sprites.Sprite;
import vooga.scroller.sprites.state.SpriteState;

public class HighHealth extends SpriteState{
    
    private static final int PRIORITY = Integer.MAX_VALUE;
    
    private static final double WIDTH_FACTOR = 1.5;
    private static final double HEIGHT_FACTOR = 2.0;

    @Override
    public void update (Sprite sprite, double elapsedTime, Dimension bounds) {
        // does nothing extra
    }

    @Override
    public void paint (Sprite sprite, Graphics2D pen) {
        sprite.getView().paint(pen, sprite.getCenter(), sprite.getSize());
    }

    @Override
    public int getPaintPriority () {
        return PRIORITY;
    }

    @Override
    public void activate (Sprite sprite) {
        sprite.scale(WIDTH_FACTOR, HEIGHT_FACTOR);
    }

    @Override
    public void deactivate (Sprite sprite) {
        sprite.scale(1/WIDTH_FACTOR, 1/HEIGHT_FACTOR);
    }
}
