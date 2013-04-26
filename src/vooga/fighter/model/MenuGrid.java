package vooga.fighter.model;

import java.util.List;
import vooga.fighter.model.loaders.MenuGridLoader;
import vooga.fighter.model.objects.MenuObject;


public class MenuGrid {
    private final static String UNSELECTED = "Unselected";
    private final static String SELECTED = "Selected";
    private List<MenuObject> myMenuObjects;
    private MenuMode myDelegate;
    private MenuObject myCurrentMenuObject;

    public MenuGrid (String Id, MenuMode delegate) {
        myDelegate = delegate;
        MenuGridLoader menuGrid = new MenuGridLoader(Id, this, delegate);
        myMenuObjects = menuGrid.getMenuObjects();
        myCurrentMenuObject = menuGrid.getFirstMenuObject();
        myCurrentMenuObject.setCurrentState(SELECTED);
    }

    public List<MenuObject> getMenuObjects () {
        return myMenuObjects;
    }

    public MenuObject getCurrentObject () {
        return myCurrentMenuObject;
    }

    public void left () {
        for (MenuObject menu : myMenuObjects) {
            if (myCurrentMenuObject.getLeft() == menu.getNum()) {
                myCurrentMenuObject.setCurrentState(UNSELECTED);
                myCurrentMenuObject = menu;
                myCurrentMenuObject.setCurrentState(SELECTED);
                return;
            }
        }
    }

    public void right () {
        for (MenuObject menu : myMenuObjects) {
            if (myCurrentMenuObject.getRight() == menu.getNum()) {
                myCurrentMenuObject.setCurrentState(UNSELECTED);
                myCurrentMenuObject = menu;
                myCurrentMenuObject.setCurrentState(SELECTED);
                return;
            }
        }
    }

    public void up () {
        for (MenuObject menu : myMenuObjects) {
            if (myCurrentMenuObject.getUp() == menu.getNum()) {
                myCurrentMenuObject.setCurrentState(UNSELECTED);
                myCurrentMenuObject = menu;
                myCurrentMenuObject.setCurrentState(SELECTED);
                return;
            }
        }
    }

    public void down () {
        for (MenuObject menu : myMenuObjects) {
            if (myCurrentMenuObject.getDown() == menu.getNum()) {
                myCurrentMenuObject.setCurrentState(UNSELECTED);
                myCurrentMenuObject = menu;
                myCurrentMenuObject.setCurrentState(SELECTED);
                return;
            }
        }
    }

}
