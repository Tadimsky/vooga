package vooga.fighter.view;

import java.awt.Dimension;
import java.util.Observable;

/**
 * A small default size PercentBar.
 * 
 * @author Wayne You
 * 
 */
public class HUDPercentBarSmall extends HUDPercentBar {
    
    public HUDPercentBarSmall() {
        super();
        myBarSize = new Dimension(150, 30);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        Double value = null;
        try {
            value = (Double) getObservedValue(o);
        }
        catch (SecurityException e) {}
        catch (IllegalArgumentException e) {
            System.err.println("Expected Double for HUDPercentBarSmall");
        }
        catch (NoSuchFieldException e) {
            System.err.println(myFieldName
                    + " is not a member of the class observed.");
        }
        catch (IllegalAccessException e) {
            System.err.println("Illegal access in HUDPercentBarSmall.");
        }
        
        myCurrentValue = value;
    }
}
