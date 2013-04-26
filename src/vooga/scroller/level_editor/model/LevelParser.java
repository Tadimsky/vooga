package vooga.scroller.level_editor.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;
import util.Location;
import vooga.scroller.level_editor.controllerSuite.LEController;
import vooga.scroller.level_editor.controllerSuite.LEGrid;
import vooga.scroller.sprites.Sprite;
import vooga.scroller.sprites.interfaces.IDoor;
import vooga.scroller.util.IBackgroundView;
import vooga.scroller.util.Pixmap;


/**
 * LevelParser parses a saved level file and creates an LEGrid.
 * 
 * @author Danny Goodman, Deo Fagnisse
 */
public class LevelParser {

    private static final String RESOURCE_PATH = "vooga.scroller.level_editor.model.SaveLoad";

    private static final char SPACE = ' ';
    private static final String NEW_LINE = System.getProperty("line.seperator");
    private ResourceBundle myResources = ResourceBundle.getBundle(RESOURCE_PATH);
    private Scanner myScanner;
    private Map<Character, String> myCharacterMap;
    private List<String> myLevelStrings;
    private Location myStartPoint;
    private String myLibPath;
    private IBackgroundView myBackground;

    /**
     * Initialize instances variables.
     */
    public LevelParser () {
        myLevelStrings = new ArrayList<String>();
        myCharacterMap = new HashMap<Character, String>();
    }

    /**
     * Creates an LEGrid from a save file.
     * 
     * @param file
     * @return LEGrid
     */
    public LEGrid makeGridFromFile (File file) {
        myLevelStrings = new ArrayList<String>();
        myCharacterMap = new HashMap<Character, String>();
        try {
            myScanner = new Scanner(file);
        }
        catch (FileNotFoundException e) {
            LEController.showErrorMsg(myResources.getString("FILE_ERROR"));
        }
        parseLevel();
        myLibPath = parseLibPath();
        myCharacterMap = parseKey();
        myStartPoint = parseStartPoint();
        myBackground = parseBackground();
        return createGrid();
    }

    private String parseLibPath () {
        String result = "";
        String line = myScanner.nextLine();
        while (!line.equals(myResources.getString("BEGIN_KEY"))) {
            result = line;
            line = myScanner.nextLine();
        }
        return result;
    }

    private void parseLevel () {
        myScanner.findWithinHorizon(myResources.getString("BEGIN_LEVEL") +
                                    NEW_LINE, 0);
        String line = myScanner.nextLine();
        while (!line.equals(myResources.getString("BEGIN_LIB_PATH"))) {
            myLevelStrings.add(line);
            line = myScanner.nextLine();
        }
    }

    private Map<Character, String> parseKey () {
        Map<Character, String> result = new HashMap<Character, String>();
        String line = myScanner.nextLine();
        while (!line.equals(myResources.getString("BEGIN_SETTINGS"))) {
            result.put(line.charAt(0), line.substring(2));
            line = myScanner.nextLine();
        }
        return result;
    }

    private Location parseStartPoint () {
        String line = myScanner.nextLine();
        line = line.substring(myResources.getString("START_POINT").length() + 1);
        String[] splitLine = line.split(String.valueOf(SPACE));
        return new Location(Integer.parseInt(splitLine[0]),
                            Integer.parseInt(splitLine[1]));
    }

    private IBackgroundView parseBackground () {
        String line = myScanner.nextLine();
        line = line.substring(myResources.getString("BACKGROUND").length() + 1);
        return new Pixmap(line);
    }

    private LEGrid createGrid () {
        if (myLevelStrings.isEmpty()) { return null; }
        LEGrid grid = new LEGrid(myLevelStrings.get(1).length(), myLevelStrings.size());
        for (int i = 1; i < myLevelStrings.size(); i++) {
            for (int j = 0; j < myLevelStrings.get(1).length(); j++) {
                char c = myLevelStrings.get(i).charAt(j);
                if (c != SPACE) {
                    String name = myCharacterMap.get(c);

                    Sprite spr;
                    try {
                        spr =
                                (Sprite) Class.forName(myLibPath +
                                                       myResources.getString("SEPARATOR") +
                                                       name).newInstance();
                        if (IDoor.class.isAssignableFrom(spr.getClass())) {
                            grid.addDoorWithCoor(j, i - 1, spr);
                        }
                        else {
                            grid.addSpriteWithCoor(j, i - 1, spr);
                        }
                    }
                    catch (InstantiationException e) {
                        LEController.showErrorMsg(myResources.getString("PARSING_ERROR"));
                    }
                    catch (IllegalAccessException e) {
                        LEController.showErrorMsg(myResources.getString("PARSING_ERROR"));
                    }
                    catch (ClassNotFoundException e) {
                        LEController.showErrorMsg(myResources.getString("PARSING_ERROR"));
                    }
                }
            }
        }
        grid.addStartPoint((int) myStartPoint.getX(), (int) myStartPoint.getY());
        grid.changeBackground(myBackground);
        return grid;
    }
}
