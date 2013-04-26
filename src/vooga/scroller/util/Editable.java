
package vooga.scroller.util;

import java.awt.Dimension;
import java.awt.Image;
import vooga.scroller.sprites.Sprite;



/**
 * Interface specifies an object that can be edited by the LevelEditor. Implemented by Level.
 * LevelEditor will only see those methods pertaining to editing the Level, not those necessary
 * to run the Level.
 * 
 * @author Danny Goodman
 * 
 */
public interface Editable {

    public void changeBackground (IBackgroundView iBackgroundView);

    public void addSprite (Sprite s, int x, int y);

    public void deleteSprite (int x, int y);

    public void addStartPoint (int x, int y);

    public void addDoor (Sprite s, int x, int y);
    
}
