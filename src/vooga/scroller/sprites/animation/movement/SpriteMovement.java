package vooga.scroller.sprites.animation.movement;

import java.awt.Dimension;
import java.awt.Graphics2D;
import util.Vector;
import vooga.scroller.sprites.Sprite;
import vooga.scroller.sprites.state.SpriteState;
import vooga.scroller.util.ISpriteView;

public class SpriteMovement extends SpriteState {
    
    private static final int PRIORITY = 0;

    private ISpriteView myView;
    private double myDirection;
    private double mySpeed;
    
    public SpriteMovement(ISpriteView view, double direction, double speed){
        myView = view;
        myDirection = direction;
        mySpeed = speed;
    }
    

    @Override
    public void update (Sprite sprite, double elapsedTime, Dimension bounds) {
        // does nothing special
    }

    @Override
    public void paint (Sprite sprite, Graphics2D pen) {
        myView.paint(pen, sprite.getCenter(), sprite.getSize());
    }

    @Override
    public int getPaintPriority () {
        return PRIORITY;
    }

    @Override
    public void activate (Sprite sprite) {
        Vector component = sprite.getVelocity().getComponentVector(myDirection);
        component.negate();
        sprite.addVector(component);
        sprite.addVector(new Vector(myDirection, mySpeed));

    }

    @Override
    public void deactivate (Sprite sprite) {
        Vector component = sprite.getVelocity().getComponentVector(myDirection);
        component.negate();
        sprite.addVector(component);

    }

}
