package vooga.rts.state;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import util.input.PositionObject;
import vooga.rts.commands.ClickCommand;
import vooga.rts.commands.Command;
import vooga.rts.commands.PositionCommand;
import vooga.rts.gui.Menu;
import vooga.rts.gui.menus.MainMenu;
import vooga.rts.gui.menus.MultiMenu;
import vooga.rts.gui.menus.SetupMenu;
import vooga.rts.networking.communications.ExpandedLobbyInfo;


/**
 * The menu state. Still hasn't been subject to refactor.
 * 
 * @author Challen Herzberg-Brovold, Jonno Schmidt
 * 
 */
public class MenuState extends SubState implements Observer {

    private Map<Integer, Menu> myMenus;
    private int myCurrentMenu;

    public MenuState (Observer observer, JFrame f) {
        super(observer);
        myMenus = new HashMap<Integer, Menu>();
        addMenu(0, new MainMenu());
        addMenu(1, new MultiMenu(f));
        addMenu(2, new SetupMenu());
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
    
    public Menu getMenu (int index) {
        return myMenus.get(index);
    }

    @Override
    public void receiveCommand (Command command) {
        if (command.getMethodName().equals(ClickCommand.LEFT_CLICK)) {
            ClickCommand left = (ClickCommand) command;
            getCurrentMenu()
                    .handleMouseDown((int) left.getPosition().x, (int) left.getPosition().y);
        }
        else if (command.getMethodName().equals(PositionCommand.MOUSE_MOVE)) {
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

    public Menu getCurrentMenu () {
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
    public void update (Observable o, Object a) {
        if (o instanceof MainMenu) {
            int s = (Integer) a;
            if (s == 0) { // If user clicked single player
                setMenu(2); // take them to the setup
            }
            else if (s == 1) { // if user clicked multi player
                ((MultiMenu) getMenu(1)).setFrame();
                setMenu(1); // take them to the multi player menu
            }
            else {
                setChanged();
                notifyObservers();
            }
        }
        else if (o instanceof MultiMenu) {
            MultiMenu m = (MultiMenu) o;
            m.unsetFrame();
            setMenu(2); // take them to the setup
            ExpandedLobbyInfo e = (ExpandedLobbyInfo) a;
            ((SetupMenu) getCurrentMenu()).setLobbyInfo(e);
        }
        else if (o instanceof SetupMenu) {
            setChanged();
            notifyObservers();
        }
        else {
            setChanged();
            notifyObservers();
        }
    }
}
