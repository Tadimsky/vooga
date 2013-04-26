package vooga.scroller.collision_manager;

import vooga.scroller.sprites.interfaces.Locatable;
import vooga.scroller.util.Direction;

public class CollisionDirection {
    
   
    private static final int COLLISION_GRANULARITY = 25; //This is how much "wiggle" room you want when deciding what exactly is a collision 

    /**
     * This method takes in two sprites and returns the collision direction of 
     * sprite1 and sprite2. For example, if sprite1 collided with sprite2 from 
     * the top, collisionDirection(sprite1, sprite2) would return Direction.TOP. 
     * 
     * @param Sprite sprite1
     * @param Sprite sprite2
     * @return one of four Direction enums
     */
    public Direction collisionDirection (Locatable sprite1, Locatable sprite2) {  
        if (checkTop(sprite1, sprite2)) return Direction.TOP;
        else if (checkBottom(sprite1, sprite2)) return Direction.BOTTOM;
        else if (checkLeft(sprite1, sprite2)) return Direction.LEFT;
        else if (checkRight(sprite1, sprite2)) return Direction.RIGHT;        
        return null;
    }
    
    private boolean checkTop(Locatable sprite1, Locatable sprite2) {
        return (sprite1.getBottom() >= sprite2.getTop() && sprite1.getBottom() <= sprite2.getTop() + COLLISION_GRANULARITY);
    }
    
    private boolean checkBottom(Locatable sprite1, Locatable sprite2) {
        return (sprite1.getTop() <= sprite2.getBottom() && sprite1.getTop() >= sprite2.getBottom() - COLLISION_GRANULARITY);
    }
    
    private boolean checkLeft(Locatable sprite1, Locatable sprite2) {
        return (sprite1.getRight() >= sprite2.getLeft() && sprite1.getRight() <= sprite2.getLeft() + COLLISION_GRANULARITY);
    }
    
    private boolean checkRight(Locatable sprite1, Locatable sprite2) {
        return (sprite1.getLeft() <= sprite2.getRight() && sprite1.getLeft() >= sprite2.getRight() - COLLISION_GRANULARITY);
    }
    

}
