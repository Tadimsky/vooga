package vooga.scroller.sprites.animation.movement;

import util.inputExample.Sprite;
import vooga.scroller.util.ISpriteView;

public class MoveUp extends SpriteMovement {

    public static final int STATE_ID = -4;

    
    public MoveUp (ISpriteView view, double speed) {
        super(view, Sprite.UP_DIRECTION, speed);
    }

}
