package vooga.fighter.view;

import java.awt.Dimension;
import java.util.Observable;

/**
 * Displays a bar with a value out of 100%.
 * 
 * @author Wayne You
 * 
 */
public class HUDPercentBar extends HUDBar {
    public class HUDPercentBarValues {
        public double    myValue;
        public Dimension mySize;
    }
    
    public HUDPercentBar() {
        myCurrentValue = 0;
        myMaxValue = 100;
        myBarSize = new Dimension();
    }
    
    @Override
    public void update(Observable o, Object arg) {
        HUDPercentBarValues values = null;
        try {
            values = (HUDPercentBarValues) getObservedValue(o);
        }
        catch (SecurityException e) {}
        catch (IllegalArgumentException e) {
            System.err.println("Expected HUDPercentBarValue for HUDPercentBar");
        }
        catch (NoSuchFieldException e) {
            System.err.println(myFieldName
                    + " is not a member of the class observed.");
        }
        catch (IllegalAccessException e) {
            System.err.println("Illegal access in HUDPercentBar.");
        }
        
        myCurrentValue = values.myValue;
        myBarSize = values.mySize;
    }
}
