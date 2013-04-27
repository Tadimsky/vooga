package vooga.rts.util;

import java.awt.Dimension;
import java.awt.geom.Point2D;


public class IsometricConverter {

    /**
     * Creates a Dimension 3D from a standard Dimension.
     * It converts the dimension using the standard isometric conversion factors.
     * 
     * @param view The dimension of the image.
     * @return The dimension of the
     */
    public static Dimension3D toIsometric (Dimension view) {
        double width = view.getWidth();
        double depth = width * Camera.ISO_HEIGHT;
        double height = view.getHeight() - (depth * Camera.ISO_HEIGHT);
        return new Dimension3D(width, depth, height);
    }
    
    public static Location3D calculatePaintLocation(Location3D basecenter, Dimension3D worldsize) {
        // How much to move to get to the left most position in view        
        Location3D change = Camera.instance().deltaviewtoWorld(new Point2D.Double(-worldsize.getWidth() * Camera.ISO_HEIGHT, 0));
        // add the existing world location
        change.add(basecenter);
        // add the height of the entity
        change.add(0, 0, worldsize.getHeight()); 
        
        // the top left coordinate of the item
        return change;
    }

}
