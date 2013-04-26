package vooga.scroller.sprites.animation.movement;

import vooga.scroller.sprites.Sprite;
import vooga.scroller.util.ISpriteView;

public class MoveRight extends SpriteMovement {
    
    public static final int STATE_ID = -5;

    
    private ISpriteView myStandView;

    public MoveRight (ISpriteView move, ISpriteView stand, double speed) {
        super(move, Sprite.RIGHT_DIRECTION, speed);
        myStandView = stand;
    }
    
    @Override
    public void deactivate(Sprite sprite) {
        super.deactivate(sprite);
        sprite.setView(myStandView);
    }
}
