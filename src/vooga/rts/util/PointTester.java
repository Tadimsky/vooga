package vooga.rts.util;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;


public class PointTester {

    List<Location3D> myShapes;
    int width = 12;
    int height = 6;

    public PointTester () {
        myShapes = new ArrayList<Location3D>();
        genPoints();
    }

    private void genPoints () {

        for (int x = 0; x < 1000; x++) {
            for (int y = 0; y < 1000; y++) {

                Point2D nP = Camera.instance().worldToView(new Location3D(x* width , y * width ,0));

                /*
                 * Polygon diamond = new Polygon();
                 * diamond.addPoint((int)nP.getX(), (int)nP.getY() + height/2);
                 * diamond.addPoint((int)nP.getX() + width/2, (int)nP.getY());
                 * diamond.addPoint((int)nP.getX() + width, (int)nP.getY() + height /2);
                 * diamond.addPoint((int)nP.getX() + width/2, (int)nP.getY() + height);
                 */

                Ellipse2D diamond = new Ellipse2D.Double(nP.getX(), nP.getY(), 1, 1);

                myShapes.add(new Location3D(x * width, y * width, 0));
            }
        }
    }

    public void paint (Graphics2D pen) {

        for (Location3D loc : myShapes) {
            Point2D nP = Camera.instance().worldToView(loc);
            if (Camera.instance().isVisible(nP)) {
                Ellipse2D diamond = new Ellipse2D.Double(nP.getX(), nP.getY(), 3, 3);
                pen.draw(diamond);
            }
        }
        Camera.instance().paint(pen);
        
    }

}
