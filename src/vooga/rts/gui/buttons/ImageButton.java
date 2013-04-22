package vooga.rts.gui.buttons;

import java.awt.Dimension;
import vooga.rts.gui.Button;
import vooga.rts.util.Location;

public class ImageButton extends Button {

    public ImageButton (String image, Dimension size, Location pos) {
        super(image, size, pos);
    }

    @Override
    public void update (double elapsedTime) {
        setChanged();
        notifyObservers();
    }

    @Override
    public void processClick () {
        update(0);
    }

    @Override
    public void processHover () {
        // TODO Auto-generated method stub
    }
}
