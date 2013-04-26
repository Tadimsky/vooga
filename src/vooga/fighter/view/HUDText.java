package vooga.fighter.view;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.Observable;

import util.Text;

/**
 * Displays some text on the screen.
 * 
 * @author Wayne You
 * 
 */
public class HUDText extends HUDElement {
    protected Text myText;
    
    public HUDText() {
        myText = new Text("");
    }
    
    @Override
    public void update(Observable o, Object arg) {
        String values = null;
        try {
            values = (String) getObservedValue(o);
        }
        catch (SecurityException e) {}
        catch (IllegalArgumentException e) {
            System.err.println("Expected String for HUDText");
        }
        catch (NoSuchFieldException e) {
            System.err.println(myFieldName
                    + " is not a member of the class observed.");
        }
        catch (IllegalAccessException e) {
            System.err.println("Illegal access in HUDText.");
        }
        
        myText.setText(values);
    }
    
    @Override
    public void paint(Graphics2D pen, Point2D center, Dimension size) {
        myText.paint(pen, center, java.awt.Color.BLACK);
    }
}
