package vooga.rts.state;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import util.input.PositionObject;
import vooga.rts.commands.Command;
import vooga.rts.controller.PositionCommand;
import vooga.rts.gui.Menu;
import vooga.rts.gui.menus.MainMenu;


/**
 * This class hasn't really been refactored much. Will leave for later.
 * 
 * @author Challen Herzberg-Brovold, Jonno Schmidt
 * 
 */
public class MenuState extends SubState implements Observer {

    private Map<Integer, Menu> myMenus;
    private int myCurrentMenu;

    public MenuState (Observer observer) {
        super(observer);
        myMenus = new HashMap<Integer, Menu>();
        addMenu(0, new MainMenu());
    }

    public void addMenu (int index, Menu menu) {
        myMenus.put(index, menu);
        menu.addObserver(this);
    }

    public void setMenu (int index) {
        if (myMenus.containsKey(index)) {
            myCurrentMenu = index;
        }
    }

    @Override
    public void receiveCommand (Command command) {
        if (command.getMethodName().equals("leftclick")) {
            PositionCommand left = (PositionCommand) command;
            getCurrentMenu()
                    .handleMouseDown((int) left.getPosition().x, (int) left.getPosition().y);
        }
        else
            if (command.getMethodName().equals("move")) {
                PositionCommand move = (PositionCommand) command;
                getCurrentMenu().handleMouseMovement((int) move.getPosition().x,
                                                     (int) move.getPosition().y);
            }
        // At some point, will need a menu controller and use actions to clean this up
    }

    @Override
    public void update (double elapsedTime) {
        getCurrentMenu().update(elapsedTime);
    }

    private Menu getCurrentMenu () {
        return myMenus.get(myCurrentMenu);
    }

    @Override
    public void paint (Graphics2D pen) {
        getCurrentMenu().paint(pen);
    }

    public void activate () {
        setMenu(0);
    }

    public void onLeftMouseUp (PositionObject o) {
        getCurrentMenu().handleMouseDown((int) o.getX(), (int) o.getY());
    }

    public void onMouseMove (PositionObject o) {
        getCurrentMenu().handleMouseMovement((int) o.getX(), (int) o.getY());
    }

    @Override
    public void update (Observable arg0, Object arg1) {
        setChanged();
        notifyObservers(arg1);
    }
}
