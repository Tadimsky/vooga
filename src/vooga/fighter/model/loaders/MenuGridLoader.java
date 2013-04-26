package vooga.fighter.model.loaders;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import vooga.fighter.model.MenuGrid;
import vooga.fighter.model.MenuMode;
import vooga.fighter.model.ModelConstants;
import vooga.fighter.model.objects.MenuObject;
import vooga.fighter.model.utils.State;
import vooga.fighter.model.utils.UpdatableLocation;


public class MenuGridLoader extends ObjectLoader {


    private List<MenuObject> myMenuObjects;
    private MenuMode myDelegate;
    private MenuObject myObject;

    public MenuGridLoader (String menuname, MenuGrid grid, MenuMode delegate, String pathHierarchy) {
        super(ModelConstants.MENUGRIDLOADER_PATH_TAG, pathHierarchy);
        myDelegate = delegate;
        myMenuObjects = new ArrayList<MenuObject>();
        load(menuname, pathHierarchy);
    }

    /**
     * Loads map from xml data
     */
    protected void load (String menuname, String pathHierarchy) {
        Document doc = getDocument();
        NodeList menugridNodes = doc.getElementsByTagName(getResourceBundle().getString("MenuMode"));
        for (int i = 0; i < menugridNodes.getLength(); i++) {
            Element node = (Element) menugridNodes.item(i);
            String name = getAttributeValue(node, getResourceBundle().getString("MenuName"));
            if (name.equals(menuname)) {
                NodeList menuobjects = node.getElementsByTagName(getResourceBundle().getString("MenuObject"));
                for (int j = 0; j < menuobjects.getLength(); j++) {
                    Element node1 = (Element) menuobjects.item(j);
                    String MenuObjectName = getAttributeValue(node1, getResourceBundle().getString("MenuObjectName"));
                    MenuObject menuobject = new MenuObject(MenuObjectName, myDelegate, pathHierarchy);
                    if (j == 0) myObject = menuobject;
                    int xCoord = Integer.parseInt(getAttributeValue(node1, getResourceBundle().getString("XCoordinate")));
                    int yCoord = Integer.parseInt(getAttributeValue(node1, getResourceBundle().getString("YCoordinate")));
                    menuobject.setLocation(new UpdatableLocation(xCoord, yCoord));
                    String nextStateName = getAttributeValue(node1, getResourceBundle().getString("NextState"));
                    menuobject.setNext(nextStateName);
                    int xSize = Integer.parseInt(getAttributeValue(node1, getResourceBundle().getString("XSize")));
                    int ySize = Integer.parseInt(getAttributeValue(node1, getResourceBundle().getString("YSize")));
                    for (Object s : menuobject.getStates().values()) {
                        State state = (State) s;
                        for (int k = 0; k < state.getNumFrames(); k++) {
                            Dimension size = new Dimension(xSize, ySize);
                            state.populateSize(size, k);
                            Rectangle rect = new Rectangle(xSize, ySize);
                            state.populateRectangle(rect, k);
                            state.populateAllDelays(ModelConstants.FOUR_TICKS);
                        }
                    }
                    int gridnum = Integer.parseInt(getAttributeValue(node1, getResourceBundle().getString("GridNum")));
                    menuobject.setNum(gridnum);
                    int up = Integer.parseInt(getAttributeValue(node1, getResourceBundle().getString("Up")));
                    menuobject.setUp(up);
                    int down = Integer.parseInt(getAttributeValue(node1, getResourceBundle().getString("Down")));
                    menuobject.setDown(down);
                    int left = Integer.parseInt(getAttributeValue(node1, getResourceBundle().getString("Left")));
                    menuobject.setLeft(left);
                    int right = Integer.parseInt(getAttributeValue(node1, getResourceBundle().getString("Right")));
                    menuobject.setRight(right);
                    myMenuObjects.add(menuobject);
                }
            }
        }

    }

    public List<MenuObject> getMenuObjects () {
        return myMenuObjects;
    }

    public MenuObject getFirstMenuObject () {
        return myObject;
    }

    @Deprecated
    public void load (int id) {
        // Using a String
    }

}
