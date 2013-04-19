package vooga.rts.util;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import vooga.rts.gui.Window;


public class Camera {

    private static final double ISO_WIDTH = 1.0;
    private static final double ISO_HEIGHT = 0.5;
    public static final double MOVE_SPEED = Window.SCREEN_SIZE.getWidth() / 4;
    public static final double VISION = 100.0;

    private static Camera myInstance;

    private Dimension myScreenSize;
    private Dimension myMapSize;

    private Location3D myWorldCenter;

    public Camera (Location3D playerLoc) {
        myWorldCenter = new Location3D(playerLoc);
        myScreenSize = Window.SCREEN_SIZE;
    }

    public void setMapSize (Dimension mapSize) {
        myMapSize = mapSize;
    }

    /**
     * Converts a world location into a view location.
     * This is used to convert something that is on the map
     * into the coordinates for painting on the screen.
     * 
     * @param world
     * @return
     */
    public Point2D worldToView (Location3D world) {
        double x = (world.getX() - myWorldCenter.getX()) * ISO_WIDTH;
        x += -(world.getY() - myWorldCenter.getY()) * ISO_WIDTH;
        x += myScreenSize.getWidth() / 2;

        double y = (world.getX() - myWorldCenter.getX()) * ISO_HEIGHT;
        y += (world.getY() - myWorldCenter.getY()) * ISO_HEIGHT;
        y += -world.getZ();
        y += myScreenSize.getHeight() / 2;
        return new Location(x, y);
    }

    /**
     * Converts view location to the world location.
     * This turns something that is drawn on the screen into a place
     * on the game map.
     * 
     * @param view
     * @return
     */
    public Location3D viewtoWorld (Point2D view) {
        return viewtoWorld(view, myWorldCenter);

        /*
         * int Zvalue = 0;
         * double x = (ISO_HEIGHT * view.getX()) + view.getY() + Zvalue;
         * x += -(myScreenSize.getHeight() * ISO_HEIGHT);
         * x += -(myScreenSize.getWidth() * ISO_HEIGHT * ISO_HEIGHT);
         * x += myWorldCenter.getX();
         * 
         * double y = view.getY() + myWorldCenter.getY() + Zvalue;
         * y += -myScreenSize.getHeight() * ISO_HEIGHT;
         * y += -view.getX() * ISO_HEIGHT;
         * y += myScreenSize.getWidth() * (ISO_HEIGHT * ISO_HEIGHT);
         * 
         * return new Location3D(x, y, Zvalue);
         */
    }

    private Location3D viewtoWorld (Point2D view, Location3D camera) {
        double Zvalue = camera.getZ();

        double x = (ISO_HEIGHT * view.getX()) + view.getY() + Zvalue;
        x += -(myScreenSize.getHeight() * ISO_HEIGHT);
        x += -(myScreenSize.getWidth() * ISO_HEIGHT * ISO_HEIGHT);
        x += camera.getX();

        double y = view.getY() + camera.getY() + Zvalue;
        y += -myScreenSize.getHeight() * ISO_HEIGHT;
        y += -view.getX() * ISO_HEIGHT;
        y += myScreenSize.getWidth() * (ISO_HEIGHT * ISO_HEIGHT);

        return new Location3D(x, y, Zvalue);
    }

    private Location3D deltaviewtoWorld (Point2D delta) {
        double Zvalue = 0;
        double x = (ISO_HEIGHT * delta.getX()) + delta.getY() + Zvalue;
        double y = delta.getY() + Zvalue - delta.getX() * ISO_HEIGHT;

        return new Location3D(x, y, Zvalue);
    }

    /**
     * Returns whether the specified location is visible by the camera or not.
     * 
     * @param world The location in the world
     * @param size The size of the object in the world
     * @return whether the object is visible
     */
    /*
     * public boolean isVisible (Location3D world) {
     * // TODO
     * Rectangle2D intersect =
     * new Rectangle2D.Double(myWorldCenter.getX(), myWorldCenter.getY(),
     * myWorldSize.getWidth(), myWorldSize.getHeight());
     * // System.out.println("Rectangle: " + intersect);
     * return intersect.contains(world.to2D());
     * }
     */

    /**
     * Returns whether the specified location is visible by the camera or not.
     * This location is in view coordinates.
     * 
     * @param screen The location in view coordinates.
     * @return Whether it is visible or not.
     */
    public boolean isVisible (Point2D screen) {
        return !(screen.getX() < -VISION || screen.getY() < -VISION ||
                 screen.getX() > myScreenSize.getWidth() + VISION || screen.getY() > myScreenSize
                .getHeight() + VISION);
    }

    public boolean isVisible (Location3D world) {
        Location3D temp = new Location3D(world);
        temp.negate();
        temp.add(myWorldCenter);
        return !(temp.getX() < -myScreenSize.getWidth() ||
                 temp.getY() < -myScreenSize.getHeight() ||
                 temp.getX() > myScreenSize.getWidth() + VISION || temp.getY() > myScreenSize
                .getHeight());
    }

    public void paint (Graphics2D pen) {
        double x = myScreenSize.getWidth() / 2;
        double y = myScreenSize.getHeight() / 2;
        double height = 10;
        double width = 10;
        // pen.setStroke();

        Polygon p = new Polygon();
        p.addPoint((int) (x - width / 2), (int) y);
        p.addPoint((int) x, (int) y);

        p.addPoint((int) (x + width / 2), (int) y);
        p.addPoint((int) x, (int) y);

        p.addPoint((int) x, (int) (y - height / 2));
        p.addPoint((int) x, (int) y);

        p.addPoint((int) x, (int) (y + height / 2));
        p.addPoint((int) x, (int) y);

        pen.draw(p);
        // System.out.println(myWorldCenter);
        // pen.fill(new Ellipse2D.Double(x - width / 2, y - width / 2, width, height));
    }

    public static Camera instance () {
        if (myInstance == null) {
            myInstance = new Camera(new Location3D(VISION, VISION, 0));
        }
        return myInstance;
    }

    public void setWorldLocation (Location3D center) {
        myWorldCenter = center;
    }

    /**
     * Moves the camera by a specified change. This change
     * is represented in view coordinates and will be
     * converted into moving the camera's world location.
     * 
     * @param change The amount to move the camera by
     */
    public void moveCamera (Location change) {
        Location3D tochange = deltaviewtoWorld(change);
        Location3D temp = new Location3D(tochange);
        temp.add(myWorldCenter);

        if (myMapSize == null) {
            return;
        }
        if (temp.getX() < VISION || temp.getX() > myMapSize.getWidth() * ISO_HEIGHT - VISION) {
            tochange.add(-tochange.getX(), 0, 0);
        }
        if (temp.getY() < VISION || temp.getY() > myMapSize.getHeight() * ISO_HEIGHT - VISION) {
            tochange.add(0, -tochange.getY(), 0);
        }
        myWorldCenter.add(tochange);
    }
}
