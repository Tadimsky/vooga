package vooga.scroller.sprites.movement;

import java.awt.geom.Point2D;
import util.Vector;
import vooga.scroller.sprites.Sprite;


/**
 * Represents the back and forth movement between two points. All you need to do is give the constructor 
 * two points - one point that represents your start and one point that represents your end. You also 
 * need to give it the speed you want this sprite to move at.
 * <br>
 * <br>
 * Note - Before we were handling this with UpAndDown.java and LeftAndRight.java. This is our attempt at 
 * generalizing that kind of movement. 
 * 
 * 
 * @author Scott Valentine
 * @author Jay Wang
 * 
 */
public class BackAndForth implements Movement {

    private static final int MAX_ANGLE = 360;
    private Sprite myEntity;
    private Point2D myStart;
    private Point2D myEnd;
    private int mySpeed;
    private double myMaxX;
    private double myMinX;
    private double myAngle;


    /**
     * Creates a new back and forth movement that can only be used for the given sprite.
     * 
     * @param sprite on which this movement applies.
     * @param start point of the back and forth movement.
     * @param end point of the back and forth movement.
     * @param speed of the back and forth movement.
     */
    public BackAndForth (Sprite sprite, Point2D start, Point2D end, int speed) {
        myEntity = sprite;
        myStart = start;
        myEnd = end;
        mySpeed = speed;

        myMaxX = Math.max(start.getX(), end.getX());
        myMinX = Math.min(start.getX(), end.getX());
        
        myAngle = Vector.angleBetween(start, end);

        placeEntityOnLine();
        setInitialVelocity();
    }

    @Override
    public void execute () {
        applyBoundary();
    }

    private void setInitialVelocity () {
        myEntity.setVelocity(new Vector(myAngle, mySpeed));
    }

    private void placeEntityOnLine () {
        int toStart =
                normalizeAngle((int) Vector.angleBetween(myEntity.getCenter(), myStart) % MAX_ANGLE);
        int toEnd =
                normalizeAngle(((int) Vector.angleBetween(myEntity.getCenter(), myEnd) 
                        ) % MAX_ANGLE);

        if (toStart != toEnd) {
            moveEntityToLine();
        }
    }

    private void moveEntityToLine () {
        myEntity.setCenter(myStart.getX(), myStart.getY());
    }

    private int normalizeAngle (int angle) {
        if (angle < 0) {
            return MAX_ANGLE + angle;
        }
        else {
            return angle;
        }
    }

    private void applyBoundary () {
        //placeEntityOnLine ();

        if (myEntity.getX() > myMaxX) {
            myEntity.setCenter(myMaxX, myEntity.getY());
            myEntity.getVelocity().negate();
        }
        else if (myEntity.getX() < myMinX) {
            myEntity.setCenter(myMinX, myEntity.getY());
            myEntity.getVelocity().negate();
        }
//        else if (myEntity.getY() > myMaxY) {
//            myEntity.setCenter(myEntity.getX(), myMaxY);
//            myEntity.getVelocity().negate();
//        }
//        else if (myEntity.getY() < myMinY) {
//            myEntity.setCenter(myEntity.getY(), myMinY);
//            myEntity.getVelocity().negate();
//        }

    }

}
