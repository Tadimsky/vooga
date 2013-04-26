package vooga.fighter.view;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import util.Text;
import vooga.fighter.controller.ViewDataSource;

/**
 * Displays Text objects in lines starting from the center of the screen and
 * moving down.
 * 
 * @author Wayne You
 * 
 */


public class EndMatchLayout extends CanvasLayout {
    private static final int TEXT_HEIGHT = 30;
    
    @Override
    public void paintComponents(Graphics2D pen, ViewDataSource data,
            Dimension screenSize) {
        int textCount = 0;
        
        for (int n = 0; n < data.ObjectNumber(); n++) {
            if (data.getPaintable(n) instanceof Text) {
                Point2D.Double location = new Point2D.Double(
                        screenSize.width / 2, (screenSize.height / 2)
                                + (textCount * TEXT_HEIGHT));
                data.getPaintable(n).paint(pen, location, data.getSize(n));
                textCount++;
            }
            else {
                defaultPaint(pen, data, n);
            }
        }
    }
    
}
