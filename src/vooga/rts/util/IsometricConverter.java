package vooga.rts.util;

import java.awt.Dimension;


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
        double depth = width * 1 / 2;
        double height = view.getHeight() - depth;
        return new Dimension3D(width, depth, height);
    }
    
    public static Location3D calculateBaseCenter(Location3D center, Dimension3D worldsize) {
        double z = 0;
        double x = center.getX();
        
        return null;
    }

}
