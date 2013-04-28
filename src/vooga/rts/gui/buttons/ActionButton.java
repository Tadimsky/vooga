package vooga.rts.gui.buttons;

import java.awt.Dimension;
import java.awt.Graphics2D;
<<<<<<< HEAD
import vooga.rts.commands.InformationCommand;
import vooga.rts.util.Location;
=======
import util.Location;
import vooga.rts.commands.InformationCommand;
>>>>>>> origin/networking-refactoring


public class ActionButton extends ImageButton {
    
    private InformationCommand myInfoCommand;

    public ActionButton (String image, Dimension size, Location pos, InformationCommand c) {
        super(image, size, pos);
        myInfoCommand = c;
    }
    
    public ActionButton (InformationCommand c, Dimension size, Location pos) {
        super(c.getInfo().getButtonImage(), size, pos);
        myInfoCommand = c;
    }
    
    @Override
    public void paint (Graphics2D pen) {
        super.paint(pen);
        if (isFocused) {
            pen.drawString(myInfoCommand.getInfo().getName(), (int) myPos.getX(), (int) myPos.getY());
        }
    }
    
    @Override
    public void processClick() {
        setChanged();
        notifyObservers(myInfoCommand);
    }


}
