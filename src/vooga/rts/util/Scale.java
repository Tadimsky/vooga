package vooga.rts.util;

import java.awt.Graphics2D;
import vooga.rts.gui.Window;

public class Scale {

    
    public static int scaleX (int x) {
        return (int) (x * (Window.SCREEN_SIZE.getWidth() / Window.D_X));
    }
    
    public static int scaleX (double x) {
        return scaleX((int) x);
    }
    
    public static int scaleY (int y) {
        return (int) (y * (Window.SCREEN_SIZE.getHeight() / Window.D_Y));
    }
    
    public static int scaleY (double y) {
        return scaleY((int) y);
    }
    
    public static void scalePen(Graphics2D pen) {
        float x = ((float) Window.SCREEN_SIZE.getWidth() / (float) Window.D_X);
        float y = ((float) Window.SCREEN_SIZE.getHeight() / (float) Window.D_Y);
        pen.scale(x, y);
    }

    public static void unscalePen (Graphics2D pen) {
        float x = ((float) Window.D_X) / ((float) Window.SCREEN_SIZE.getWidth());
        float y = ((float) Window.D_Y) / ((float) Window.SCREEN_SIZE.getHeight());
        pen.scale(x, y);
    }
}
