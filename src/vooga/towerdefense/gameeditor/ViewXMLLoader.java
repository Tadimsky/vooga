package vooga.towerdefense.gameeditor;

import java.awt.Dimension;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import javax.swing.JPanel;
import org.w3c.dom.Element;
import util.XMLTool;
import vooga.towerdefense.controller.Controller;
import vooga.towerdefense.view.gamescreens.GameElementInformationScreen;
import vooga.towerdefense.view.gamescreens.GameStatsScreen;
import vooga.towerdefense.view.gamescreens.MapScreen;
import vooga.towerdefense.view.gamescreens.MultipleScreenPanel;
import vooga.towerdefense.view.gamescreens.ShopScreen;

/**
 * 
 * @author Erick Gonzalez
 */
public class ViewXMLLoader {
    private XMLTool myXMLTool;
    
    private static final String VIEW_TAG = "view";
    private static final String DIMENSION_TAG = "dimension";
    private static final String LOCATION_TAG = "location";
    private static final String MAPSCREEN_TAG = "MapScreen";
    private static final String SHOPSCREEN_TAG = "ShopScreen";
    private static final String GAME_STATS_SCREEN_TAG = "GameStatsScreen";
    private static final String GAME_ELEMENTS_SCREEN_TAG = "GameElementInformationScreen";
    private static final String MULTIPLE_SCREEN_PANEL_TAG = "MultipleScreenPanel";
    private static final String BORDER_LAYOUT_ADDITION = "BorderLayout.";
    
    public ViewXMLLoader(XMLTool xmlTool, String xmlFilePath) {
        myXMLTool = xmlTool;
    }
    
    public void makeView(Controller controller) throws IllegalArgumentException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Element viewElement = myXMLTool.getElement(VIEW_TAG);
        
        Map<String, Element> subElements = myXMLTool.getChildrenElementMap(viewElement);
        Element dimensionElement = subElements.get(DIMENSION_TAG);
        Dimension dimension = makeDimensionFrom(myXMLTool.getContent(dimensionElement));
        controller.getView().setSize(dimension);
        for (String s : subElements.keySet()) {
            if (!s.equals(MULTIPLE_SCREEN_PANEL_TAG)) {
                Element element = subElements.get(s);
                JPanel screen = getScreen(element, controller);
                Element locationElement = subElements.get(LOCATION_TAG);
                String location = BORDER_LAYOUT_ADDITION + myXMLTool.getContent(locationElement);
                controller.getView().addScreen(screen, location);
            }
            else {
                Element multiplePanelScreen = subElements.get(MULTIPLE_SCREEN_PANEL_TAG);
                JPanel multipleScreenPanel = getScreen(multiplePanelScreen, controller);
                Element locationElement = subElements.get(LOCATION_TAG);
                String location = BORDER_LAYOUT_ADDITION + myXMLTool.getContent(locationElement);
                multipleScreenPanel = createMultipleScreenPanel(multipleScreenPanel, multiplePanelScreen, controller);
                controller.getView().addScreen(multipleScreenPanel, location);
            }
        }
    }
    
    private JPanel createMultipleScreenPanel(JPanel panel, Element element, Controller controller) throws IllegalArgumentException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Map<String, Element> subMultiples = myXMLTool.getChildrenElementMap(element);
        for (String s : subMultiples.keySet()) {
            Element subScreenElement = subMultiples.get(s);
            JPanel subScreen = getScreen(subScreenElement, controller);
            Element locElement = myXMLTool.getElement(LOCATION_TAG);
            String location = BORDER_LAYOUT_ADDITION + myXMLTool.getContent(locElement);
            ((MultipleScreenPanel) panel).addScreen(subScreen, location);
        }
        return (MultipleScreenPanel) panel;
    }
    
    /**
     * adds this screen to the view.
     * @param element
     * @param controller
     * @return the screen
     * @throws ClassNotFoundException
     * @throws IllegalArgumentException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private JPanel getScreen(Element element, Controller controller) throws ClassNotFoundException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Map<String, Element> subElements = myXMLTool.getChildrenElementMap(element);
        Element dimensionElement = subElements.get(DIMENSION_TAG);
        Dimension dimension = makeDimensionFrom(myXMLTool.getContent(dimensionElement));
        Class c = Class.forName(myXMLTool.getContent(element));
        Constructor[] constructors = c.getConstructors();
        Constructor cons = constructors[0];
        JPanel screen = (JPanel) cons.newInstance(dimension, controller);
        if (myXMLTool.getContent(element).equals(MAPSCREEN_TAG)) {
            controller.getView().setMapScreen((MapScreen)screen);
        }
        else if (myXMLTool.getContent(element).equals(SHOPSCREEN_TAG)) {
            controller.getView().setShopScreen((ShopScreen)screen);
        }
        else if (myXMLTool.getContent(element).equals(GAME_ELEMENTS_SCREEN_TAG)) {
            controller.getView().setGameElementInfoScreen((GameElementInformationScreen)screen);
        }
        else if (myXMLTool.getContent(element).equals(GAME_STATS_SCREEN_TAG)) {
            controller.getView().setPlayerInfoScreen((GameStatsScreen)screen);
        }
        return screen;
    }
    
    /**
     * helper method to parse the dimension correctly.
     * @param dimensionString
     * @return
     */
    private Dimension makeDimensionFrom(String dimensionString) {
        String[] dimensionPieces = dimensionString.split(", ");
        String width = dimensionPieces[0];
        String height = dimensionPieces[1];
        return new Dimension(Integer.parseInt(width), Integer.parseInt(height));
    }
    
    
}
