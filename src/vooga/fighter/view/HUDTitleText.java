package vooga.fighter.view;

import java.util.Observable;

import util.Text;

/**
 * Large text HUD display for use in titles.
 * 
 * @author Wayne You
 * 
 */
public class HUDTitleText extends HUDText {
    private static final int LARGE_SIZE = 32;
    
    @Override
    public void update(Observable o, Object arg) {
        super.update(o, arg);
        myText.setFont(Text.FONT_SANSSERIF, LARGE_SIZE);
    }
}
