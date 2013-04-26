package vooga.towerdefense.gameeditor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import org.w3c.dom.Element;
import util.XMLTool;


/**
 * Controls the game editor & makes the XML
 * file based on input from the game editor.
 * 
 * @author Angelica Schwartz
 * @author Leonard Ng'eno
 * @author Yoshida
 */
public class GameEditorController extends JFrame {
    /**
     * default serialized id.
     */
    private static final long serialVersionUID = 1L;
    private static final String TITLE_KEYWORD = "GAME EDITOR";
    private static final String XML_EXTENSION = ".xml";
    private static final String GAME_TAG = "game";
    private static final String VIEW_TAG = "view";
    private static final String GAME_ELEMENT_TAG = "gameelement";
    private static final String LEVEL_TAG = "level";
    private static final String IMAGE_TAG = "image";
    private static final String TYPE_TAG = "type";
    private static final String MAP_TAG = "map";
    private static final String DIMENSION_TAG = "dimension";
    private static final String WIDTH_TAG = "width";
    private static final String HEIGHT_TAG = "height";
    private static final String TILE_TAG = "tile";
    private static final String GRID_TAG = "grid";
    private static final String RULES_TAG = "rules";
    private static final String SCREEN_LOCATION_TAG = "location";
    private static final String ATTRIBUTES_TAG = "attributes";
    private static final String ACTIONS_TAG = "actions";
    private static final String FACTORY_INDICATOR = "Factory";
    private static final Dimension SIZE = new Dimension(700, 700);
    private static final String UNIT_INDICATOR = "Unit";
    private static final String RESOURCE_PATH = "/src/vooga/towerdefese/resources/";
    public static final String CLASS_INDICATOR_STRING = ".class";
    private Dimension mySize;
    private Dimension myMapSize;
    private List<String> myCreatedUnits;
    private String myName;
    private XMLTool myXMLDoc;
    private Element myRoot;
    private Element myViewParent;
    private Element myGameElementParent;
    private Element myMapParent;
    private Element myLevelParent;
    private ActionXMLWriter myActionParser;
    
    /**
     * Constructor.
     * 
     * @param size
     */
    public GameEditorController(Dimension size) {
        this.setTitle(TITLE_KEYWORD);
        myCreatedUnits = new ArrayList<String>();
        mySize = size;
        setSize(mySize);
        setPreferredSize(mySize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeXML();
        initializeGUI();
    }
    
    /**
     * starts a new XML file & initializes the parents of
     * 
     */
    private void initializeXML () {
        myXMLDoc = new XMLTool();
        myRoot = myXMLDoc.makeRoot(GAME_TAG);
        myViewParent = myXMLDoc.makeElement(VIEW_TAG);
        myXMLDoc.addChild(myRoot, myViewParent);
        myMapParent = myXMLDoc.makeElement(MAP_TAG);
        myXMLDoc.addChild(myRoot, myMapParent);
        myGameElementParent = myXMLDoc.makeElement(GAME_ELEMENT_TAG);
        myXMLDoc.addChild(myRoot, myGameElementParent);
        myLevelParent = myXMLDoc.makeElement(LEVEL_TAG);
        myXMLDoc.addChild(myRoot, myLevelParent);
        myActionParser = new ActionXMLWriter(myXMLDoc);
    }
    
    /**
     * starts the visual for the game editor.
     */
    private void initializeGUI () {
        StartUpScreen screen = new StartUpScreen(SIZE, this);
        getContentPane().add(screen, BorderLayout.CENTER);
        
        pack();
        setVisible(true);
    }
    
    /**
     * saves the xml file.
     */
    public void saveFile () {
        myXMLDoc.writeFile(RESOURCE_PATH + myName + XML_EXTENSION);
    }
    
    /**
     * sets the name of this game.
     */
    public void setNameOfGame (String name) {
        myName = name;
    }
    
    /**
     * adds a level to the XML file.
     */
    public void addLevelToGame (String name, Map<String, String> rules, String actions) {
        Element thisLevel = myXMLDoc.makeElement(name);
        myXMLDoc.addChild(myLevelParent, thisLevel);
        Element ruleELement = myXMLDoc.makeElementsFromMap(RULES_TAG, rules);
        myXMLDoc.addChild(thisLevel, ruleELement);
        Element actionElement = myXMLDoc.makeElement(ACTIONS_TAG);
        myXMLDoc.addChild(thisLevel, myActionParser.parse(actionElement, actions));
        
    }
    
    /**
     * adds a map to the XML file.
     */
    public void addMapToGame (String name,
                              String image,
                              String width,
                              String height,
                              String tileSize,
                              String map) {
        Element thisMap = myXMLDoc.makeElement(name.trim());
        myXMLDoc.addChild(thisMap, IMAGE_TAG, image);
        myXMLDoc.addChild(thisMap, WIDTH_TAG, width);
        myXMLDoc.addChild(thisMap, HEIGHT_TAG, height);
        myXMLDoc.addChild(thisMap, TILE_TAG, tileSize);
        myXMLDoc.addChild(thisMap, GRID_TAG, map);
        myXMLDoc.addChild(myMapParent, thisMap);
        
        myXMLDoc.writeFile(RESOURCE_PATH + "tdmap1.xml");
    }
    
    public void addGameElementToGame (String type,
                                      String name,
                                      String path,
                                      Map<String, String> attributes,
                                      String actions) {
        if (type.equals(UNIT_INDICATOR)) {
            myCreatedUnits.add(name);
        }
        addGameElementToFile(myGameElementParent, type, name, path, attributes, actions);
    }
    
    /**
     * adds a game element to the XML file.
     * 
     * @param parent is the parent element
     * @param name is the game element's name
     * @param path is the image path
     * @param attributes is the map of attribute name to value
     * @param actions is the map of action name to value
     */
    private void addGameElementToFile (Element parent,
                                       String type,
                                       String name,
                                       String path,
                                       Map<String, String> attributes,
                                       String actions) {
        Element gameElement = myXMLDoc.makeElement(name);
        myXMLDoc.addChild(parent, gameElement);
        myXMLDoc.addChild(gameElement, IMAGE_TAG, path);
        myXMLDoc.addChild(gameElement, TYPE_TAG, type);
        Element attributeElement = myXMLDoc.makeElementsFromMap(ATTRIBUTES_TAG, attributes);
        myXMLDoc.addChild(gameElement, attributeElement);
        Element actionElement = myXMLDoc.makeElement(ACTIONS_TAG);
        myXMLDoc.addChild(gameElement, myActionParser.parse(actionElement, actions));
    }
    
    /**
     * gets the list of already created units.
     * 
     * @return a list of unit names as strings.
     */
    public List<String> getUnits () {
        return myCreatedUnits;
    }
    
    /**
     * adds a view to the XML file.
     */
    public void addViewToGame (String dimension,
                               List<String> viewInfo,
                               Map<String, List<String>> map) {
        Element viewscreen = myXMLDoc.makeElement("Dimension");
        myXMLDoc.addChild(myViewParent, viewscreen);
        myXMLDoc.addChild(viewscreen, DIMENSION_TAG, dimension);
        
        for (String s : viewInfo) {
            if (!s.equals("")) {
                String[] characteristics = s.split(" ");
                if (!characteristics[0].equals("")) {
                    Element screen = myXMLDoc.makeElement(characteristics[0]);
                    myXMLDoc.addChild(myViewParent, screen);
                    String noComma =
                            characteristics[1].substring(0, characteristics[1].length() - 1);
                    myXMLDoc.addChild(screen, WIDTH_TAG, noComma);
                    myXMLDoc.addChild(screen, HEIGHT_TAG, characteristics[2]);
                    myXMLDoc.addChild(screen, SCREEN_LOCATION_TAG, characteristics[3]);
                    if (characteristics[0].equals("MultipleScreenPanel")) {
                        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                            if (entry.getKey().equals(characteristics[3])) {
                                for (String str : entry.getValue()) {
                                    String[] values = str.split(" ");
                                    Element screen2 = myXMLDoc.makeElement(values[1]);
                                    myXMLDoc.addChild(screen, screen2);
                                    myXMLDoc.addChild(screen2, WIDTH_TAG, values[2]);
                                    myXMLDoc.addChild(screen2, HEIGHT_TAG, values[3]);
                                    myXMLDoc.addChild(screen2, SCREEN_LOCATION_TAG, values[0]);
                                }
                            }
                        }
                    }
                    
                }
            }
        }
        myXMLDoc.writeFile(RESOURCE_PATH + "view32.xml");
        
    }
    
    /**
     * sets the mapsize for this game.
     * 
     * @param mapSize
     */
    public void setMapSize (Dimension mapSize) {
        myMapSize = mapSize;
    }
    
    /**
     * gets the mapsize for this game.
     * 
     * @return Dimension that is the mapsize
     */
    public Dimension getMapSize () {
        return myMapSize;
    }
    
    /**
     * uses reflection to display the next screen in the sequence.
     * 
     * @param nextScreenName is the next screen
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void displayNextScreen (String nextScreenName) throws ClassNotFoundException,
                                                         InstantiationException,
                                                         IllegalAccessException, SecurityException,
                                                         NoSuchMethodException,
                                                         IllegalArgumentException,
                                                         InvocationTargetException {
        if (nextScreenName != null) {
            Class[] args = { Dimension.class, GameEditorController.class };
            Class theClass = Class.forName(nextScreenName);
            Constructor cons = theClass.getConstructor(args);
            GameEditorScreen screen = (GameEditorScreen) cons.newInstance(mySize, this);
            getContentPane().add(screen);
            screen.display();
            
            pack();
            setVisible(true);
        }
        else {
            System.exit(0);
        }
    }
    
    /**
     * returns the parameters of the constructor in the desired class.
     * 
     * @param className the class path
     * @return a list of parameters as strings
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("rawtypes")
    public List<String> getParametersForAction (String className) throws ClassNotFoundException {
        List<String> parameters = new ArrayList<String>();
        Class c = Class.forName(className + FACTORY_INDICATOR);
        Constructor cons = c.getConstructors()[0];
        Class[] parameterClasses = cons.getParameterTypes();
        for (Class parameterClass : parameterClasses) {
            parameters.add(parameterClass.getSimpleName().toString());
        }
        return parameters;
    }
    
    /**
     * gets the available actions for the gam developer.
     * 
     * @param actionPackageName is the package where the action factories are.
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public List<String> getAvailableActions (String packageName) throws IOException,
                                                                ClassNotFoundException {
        List<String> actions = new ArrayList<String>();
        List<Class> actionClasses = getClassesInPackage(packageName);
        for (Class actionClass : actionClasses) {
            String action = actionClass.getSimpleName().toString();
            action = action.substring(0, action.length() - "Factory".length());
            actions.add(action);
        }
        return actions;
    }
    
    /**
     * Get the classes in this package.
     * 
     * @param packageName
     * @return list of classes in the package
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("rawtypes")
    public List<Class> getClassesInPackage (String packageName) throws IOException,
                                                               ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace(".", "/");
        URL resource = classLoader.getResource(path);
        File directory = new File(resource.getFile());
        if (directory.exists()) {
            File[] files = directory.listFiles();
            for (File file : files) {
                if (file.getName().endsWith(CLASS_INDICATOR_STRING)) {
                    classes.add(Class
                            .forName(packageName +
                                     "." +
                                     file.getName().subSequence(0,
                                                                file.getName().length()
                                                                        -
                                                                        CLASS_INDICATOR_STRING
                                                                                .length())));
                }
            }
        }
        return classes;
    }
    
    /**
     * gets the list of class names in the desired package.
     * 
     * @param packageName
     * @return a list of strings
     * @throws ClassNotFoundException
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    public List<String> getClassNamesInPackage (String packageName) throws IOException,
                                                                   ClassNotFoundException {
        List<String> names = new ArrayList<String>();
        List<Class> classes = getClassesInPackage(packageName);
        for (Class c : classes) {
            if (!c.getName().contains("$")) {
                names.add(c.getName().substring(packageName.length() + 1,
                                                c.getName().length()));
            }
            names.add(c.getName().substring(packageName.length() + 1,
                                            c.getName().length()));
        }
        return names;
    }
    
    /**
     * gets the attributes as strings for the game developer.
     * 
     * @return the attributes as a list of strings
     * @throws ClassNotFoundException
     */
    public List<String> getAttributes (String attributeClassName) throws ClassNotFoundException {
        List<String> fields = new ArrayList<String>();
        Field[] fieldList = getFieldsInClass(attributeClassName);
        for (Field field : fieldList) {
            fields.add(field.getName());
        }
        return fields;
    }
    
    /**
     * gets the list of field names in the desired class.
     * 
     * @param packageName
     * @return
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("rawtypes")
    private Field[] getFieldsInClass (String className) throws ClassNotFoundException {
        Class attributesClass = Class.forName(className);
        Field fieldList[] = attributesClass.getDeclaredFields();
        return fieldList;
    }
}
