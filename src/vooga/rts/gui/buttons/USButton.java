package vooga.rts.gui.buttons;

import java.awt.Dimension;
import vooga.rts.gui.Button;
import vooga.rts.util.Location;

public class USButton extends Button {

    public USButton (String image, Dimension size, Location pos) {
        super(image, size, pos);
    }

    @Override
    public void update (double elapsedTime) {        
        setChanged();
        
    }

    @Override
    public void processClick () {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void processHover () {
        // TODO Auto-generated method stub
        
    }

}
