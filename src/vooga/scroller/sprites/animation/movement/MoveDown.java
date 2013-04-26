package vooga.scroller.sprites.animation.movement;

import util.inputExample.Sprite;
import vooga.scroller.util.ISpriteView;

public class MoveDown extends SpriteMovement {

    public static final int STATE_ID = -2;
    
    public MoveDown (ISpriteView view, double speed) {
        super(view, Sprite.DOWN_DIRECTION, speed);
        // TODO Auto-generated constructor stub
    }

}
