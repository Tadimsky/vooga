package vooga.fighter.view;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import vooga.fighter.controller.ViewDataSource;

/**
 * Displays a title at the top center of the screen, and a trail of text
 * starting from the center of the screen down.
 * 
 * @author Wayne You
 * 
 */
public class ScoreScreenLayout extends CanvasLayout {
    protected static final int TITLE_DISTANCE_FROM_TOP = 30;
    protected static final int LINE_HEIGHT             = 20;
    
    @Override
    public void paintComponents(Graphics2D pen, ViewDataSource data,
            Dimension screenSize) {
        boolean titleMade = false;
        int lineCount = 0;
        
        for (int n = 0; n < data.ObjectNumber(); n++) {
            if (!titleMade && (data.getPaintable(n) instanceof HUDTitleText)) {
                Point2D.Double location = new Point2D.Double(
                        screenSize.width / 2, TITLE_DISTANCE_FROM_TOP);
                data.getPaintable(n).paint(pen, location, data.getSize(n));
                titleMade = true;
            }
            else if (data.getPaintable(n) instanceof HUDText) {
                Point2D.Double location = new Point2D.Double(
                        screenSize.width / 2, (screenSize.height / 2)
                                + (LINE_HEIGHT * lineCount));
                data.getPaintable(n).paint(pen, location, data.getSize(n));
                lineCount++;
            }
            else {
                defaultPaint(pen, data, n);
            }
        }
    }
    
}
