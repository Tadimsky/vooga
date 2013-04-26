package vooga.scroller.level_management.splash_page;

import java.awt.Dimension;
import util.Location;
import vooga.scroller.sprites.Sprite;
import vooga.scroller.util.ISpriteView;
import vooga.scroller.util.Pixmap;

public class Mouse extends Sprite {

    private static final Pixmap DEFAULT_IMAGE = new Pixmap("/vooga/scroller/images/sun.png");
    private static final Location DEFAULT_CENTER = new Location(0,0);
    private static final Dimension MOUSE_DIMENSIONS = new Dimension(40,40);
    
    public Mouse (ISpriteView image, Location center) {
        super(image, center, MOUSE_DIMENSIONS);
    }
    
    public Mouse(Location center){
        this(DEFAULT_IMAGE, center);
    }
    
    public Mouse(){
        this(DEFAULT_CENTER);
    }

}
