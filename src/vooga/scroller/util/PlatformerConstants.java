
package vooga.scroller.util;

import java.awt.Dimension;

/**
 * Class holding the constants to be used for the scrolling-platformer project
 * @author Ross
 *
 */
public class PlatformerConstants {
    
    /**
     * The size for Window objects
     */
    public static final Dimension DEFAULT_WINDOW_SIZE = new Dimension(800, 600);
    
    /**
     * The size for the frame of reference
     */
    public static final Dimension REFERENCE_FRAME_SIZE = DEFAULT_WINDOW_SIZE;
    
    /**
     * The size for the frame of action
     */
    
    public static final Dimension ACTION_FRAME_SIZE = new Dimension(REFERENCE_FRAME_SIZE.width + 200, REFERENCE_FRAME_SIZE.height + 200);

    public static final Dimension DEFAULT_LEVEL_SIZE = new Dimension(1600, 300);

    /**
     * Added to fully prevent instantiation of this utility class.
     */
    private PlatformerConstants() {
        
    }

}
