package vooga.scroller.level_editor;

import java.awt.Dimension;
import util.Location;
import vooga.scroller.sprites.Sprite;
import vooga.scroller.util.Pixmap;

public class StartPoint extends Sprite{

    private static final String DEFAULT_FILENAME = "startPoint.png";
    private static final String DEFAULT_PATH = "/vooga/scroller/images/";
    private static final Pixmap DEFAULT_IMAGE = new Pixmap(DEFAULT_PATH,DEFAULT_FILENAME);
    private static final Location DEFAULT_LOC = new Location();
    private static final Dimension DEFAULT_SIZE = new Dimension(32,32);

    public StartPoint (){
        this(DEFAULT_IMAGE, DEFAULT_LOC, DEFAULT_SIZE);
    }
    
    public StartPoint (Pixmap image, Location center, Dimension size) {
        super(image, center, size);
    }

}
