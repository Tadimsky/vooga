package vooga.fighter.model;

import java.awt.Dimension;
import java.util.List;
import vooga.fighter.model.objects.GameObject;
import vooga.fighter.model.objects.MenuObject;
import vooga.fighter.util.CollisionManager;


/**
 * Mode implementation for a menu.
 * 
 * @author Jack Matteucci
 * @modified Jerry Li
 * 
 */
public class MenuMode extends Mode {
    private String myMenuName;
    private List<MenuObject> myMenuObjects;
    private MenuGrid myMenuGrid;
    private String myChoice;
    private int myInputTicks;

    public MenuMode (CollisionManager manager, String menuName) {
        super(manager);
        myMenuName = menuName;
        myChoice = "";
        myInputTicks = 0;
    }

    @Override
    public void update () {
        removeAppropriateObjects();
        List<GameObject> myObjects = getMyObjects();
        for (GameObject object : myObjects) {
            object.update();
        }
        for (GameObject object : myObjects) {
            object.updateState();
        }
        handleCollisions();
        myInputTicks++;
    }

    public void setMenuGrid (MenuGrid grid) {
        myMenuGrid = grid;
    }

    public String getName () {
        return myMenuName;
    }

    public void setMenuObjects (List<MenuObject> menus) {
        myMenuObjects = menus;
        for (MenuObject menu : menus) {
            addObject(menu);
        }
    }

    public String getMenusNext (String value) {
        for (MenuObject menu : myMenuObjects) {
            if (menu.getValue().equals(value)) return menu.getNext();
        }
        return "";
    }

    public void setChoice (String choice) {
        myChoice = choice;
    }

    public void left () {
        myMenuGrid.left();
    }

    public void up () {
        myMenuGrid.up();
    }

    public void down () {
        myMenuGrid.down();
    }

    public void right () {
        myMenuGrid.right();
    }

    public void resetChoice () {
        myChoice = "";
    }

    public String peekChoice () {
        return myChoice;
    }

    public MenuObject getCurrentMenu () {
        return myMenuGrid.getCurrentObject();
    }

    public boolean inputReady () {
        if (myInputTicks > 3) {
            myInputTicks = 0;
            return true;
        }
        return false;
    }

}
