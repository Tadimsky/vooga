package vooga.fighter.view;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.Observable;
import vooga.fighter.objects.utils.Health;
import vooga.fighter.util.Text;

public class HUDPlayerScoreAndHealth extends HUDPlayerValue {
    Text myPlayerHealth = new Text("");
    
    @Override
    public void update (Observable o, Object arg) {
        PlayerStatus newStatus = null;
        try {
            newStatus = (PlayerStatus)this.getObservedValue(o);
        }
        catch (SecurityException e) {}
        catch (IllegalArgumentException e) {
            System.err.println("Expected PlayerStatus for HUDPlayerScoreAndHealth");
        }
        catch (NoSuchFieldException e) {
            System.err.println(myFieldName + " is not a member of the class observed.");
        }
        catch (IllegalAccessException e) {
            System.err.println("Illegal access in HUDPlayerScoreAndHealth.");
        }
        
        if (newStatus == null) {
            return;
        }
        
        myPlayerNameText.setText(newStatus.myName);
        myPlayerValue.setText("Score: " + newStatus.myValue);
        myPlayerHealth.setText("Health: " + newStatus.myHealth);
    }
    
    public void paint (Graphics2D pen, Point2D center, Dimension size) {
        super.paint(pen, center, size);
        center.setLocation(center.getX(), center.getY() + HUDElement.DEFAULT_TEXT_HEIGHT);
        myPlayerHealth.paint(pen, center, java.awt.Color.BLACK);
    }
}
