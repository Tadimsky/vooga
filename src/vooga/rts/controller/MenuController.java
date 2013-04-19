package vooga.rts.controller;

import java.awt.Graphics2D;
import java.awt.MenuItem;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import vooga.rts.gui.Menu;
import vooga.rts.gui.menus.MainMenu;
import vooga.rts.input.PositionObject;


public class MenuController extends AbstractController implements Observer {

    private Map<Integer, Menu> myMenus;
    private int myCurrentMenu;

    public MenuController () {
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

    @Override
    public void activate () {
        setMenu(0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see vooga.rts.controller.AbstractController#onLeftMouseUp(vooga.rts.input.PositionObject)
     */
    @Override

    public void onLeftMouseUp (PositionObject o) {        
        getCurrentMenu().handleMouseDown((int) o.getX(), (int) o.getY());
    }

    @Override
    public void onMouseMove (PositionObject o) {
        getCurrentMenu().handleMouseMovement((int) o.getX(), (int) o.getY());
    }

    @Override
    public void update (Observable arg0, Object arg1) {
        System.out.println("I am hereby notified.");
        setChanged();
        notifyObservers(arg1);
    }

    @Override
    public MainState getGameState () {
        return MainState.Menu;
    }

}
