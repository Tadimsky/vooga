package vooga.fighter.view;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.Observable;

/**
 * Displays a bar representing a relative value.
 * 
 * @author Wayne You
 * 
 */
public class HUDBar extends HUDElement {
    public final int    OUTLINE_WIDTH = 4;
    
    protected double    myCurrentValue;
    protected double    myMaxValue;
    protected Dimension myBarSize;
    
    public class HUDBarValues {
        public double    myValue;
        public double    myMax;
        public Dimension mySize;
    }
    
    public HUDBar() {
        myCurrentValue = 0;
        myMaxValue = 0;
        myBarSize = new Dimension();
    }
    
    @Override
    public void update(Observable o, Object arg) {
        HUDBarValues values = null;
        try {
            values = (HUDBarValues) getObservedValue(o);
        }
        catch (SecurityException e) {}
        catch (IllegalArgumentException e) {
            System.err.println("Expected HUDBarValue for HUDBar");
        }
        catch (NoSuchFieldException e) {
            System.err.println(myFieldName
                    + " is not a member of the class observed.");
        }
        catch (IllegalAccessException e) {
            System.err.println("Illegal access in HUDBar.");
        }
        
        myCurrentValue = values.myValue;
        myMaxValue = values.myMax;
        myBarSize = values.mySize;
    }
    
    @Override
    public void paint(Graphics2D pen, Point2D center, Dimension size) {
        pen.setColor(java.awt.Color.GRAY);
        pen.fillRect((int) center.getX(), (int) center.getY(),
                (int) (center.getX() + myBarSize.width),
                (int) (center.getY() + myBarSize.height));
        pen.setColor(java.awt.Color.BLUE);
        pen.fillRect(
                (int) center.getX() + OUTLINE_WIDTH,
                (int) center.getY() + OUTLINE_WIDTH,
                (int) ((center.getX() - (OUTLINE_WIDTH * 2)) + ((myBarSize.width * myCurrentValue) / myMaxValue)),
                (int) ((center.getY() - (OUTLINE_WIDTH * 2)) + myBarSize.height));
    }
    
    /**
     * Sets the dimensions of the bar.
     * 
     * @param size
     */
    public void setSize(Dimension size) {
        myBarSize = size;
    }
    
}
