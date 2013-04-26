package vooga.scroller.level_editor.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import util.Location;
import vooga.scroller.level_editor.controllerSuite.LEController;
import vooga.scroller.level_editor.controllerSuite.LEGrid;
import vooga.scroller.sprites.Sprite;


/**
 * LevelWriter takes in an LEGrid and a file to write to and saves the Level.
 * 
 * @author Danny Goodman, Deo Fagnisse
 * 
 */
public class LevelWriter {

    private static final String RESOURCE_PATH = "vooga.scroller.level_editor.model.SaveLoad";
    private static final char SPACE = ' ';
    private static final String NEW_LINE = System.getProperty("line.separator");
    private int myKeyCounter;
    private ResourceBundle myResources = ResourceBundle.getBundle(RESOURCE_PATH);
    private Map<String, Character> myMap;
    private FileWriter myFileWriter;
    private LEGrid myGrid;
    private Location myStartPoint;

    /**
     * Creates a save file from a LEGrid
     * 
     * @param file
     * @param levelGrid
     * @param libPath
     */
    public void createFile (File file, LEGrid levelGrid, String libPath) {
        myGrid = levelGrid;
        myStartPoint = myGrid.removeStartPoint();
        myKeyCounter = 0;
        myMap = new HashMap<String, Character>();
        try {
            myFileWriter = new FileWriter(file);
        }
        catch (IOException e) {
            LEController.showErrorMsg(myResources.getString("FILE_ERROR"));
        }
        writeLevel();
        writeLib(libPath);
        writeKey();
        writeSettings();
        myGrid.addStartPoint((int) myStartPoint.getX(), (int) myStartPoint.getY());
    }

    private void writeLevel () {
        try {
            myFileWriter.write(myResources.getString("BEGIN_LEVEL"));
            for (int i = 0; i < myGrid.getSize().height; i++) {
                myFileWriter.write(NEW_LINE);
                for (int j = 0; j < myGrid.getSize().width; j++) {
                    Sprite s = myGrid.getSprite(j, i);
                    if (s == null) {
                        myFileWriter.write(SPACE);
                    }
                    else {
                        String spriteName = s.getClass().getSimpleName();
                        if (!myMap.containsKey(spriteName)) {
                            myMap.put(spriteName,
                                      myResources.getString("KEY_CREATOR").charAt(myKeyCounter));
                            myKeyCounter++;
                        }
                        myFileWriter.write(myMap.get(spriteName));
                    }
                }
            }
            myFileWriter.write(NEW_LINE);
        }
        catch (IOException e) {
            LEController.showErrorMsg(myResources.getString("WRITING_ERROR"));
        }

    }

    private void writeLib (String libPath) {
        try {
            myFileWriter.write(myResources.getString("BEGIN_LIB_PATH"));
            myFileWriter.write(NEW_LINE);
            myFileWriter.write(libPath);
            myFileWriter.write(NEW_LINE);
        }
        catch (IOException e) {
            LEController.showErrorMsg(myResources.getString("WRITING_ERROR"));
        }
    }

    private void writeKey () {
        Set<String> keySet = myMap.keySet();
        try {
            myFileWriter.write(myResources.getString("BEGIN_KEY"));
            for (String key : keySet) {
                myFileWriter.write(NEW_LINE);
                myFileWriter.write("" + myMap.get(key) + myResources.getString("EQUALS") + key);
            }
            myFileWriter.write(NEW_LINE);
        }
        catch (IOException e) {
            LEController.showErrorMsg(myResources.getString("WRITING_ERROR"));
        }

    }

    private void writeSettings () {
        try {
            myFileWriter.write(myResources.getString("BEGIN_SETTINGS"));
            myFileWriter.write(NEW_LINE);
            myFileWriter.write(myResources.getString("START_POINT") +
                               myResources.getString("EQUALS") +
                               (int) myStartPoint.getX() + SPACE + (int) myStartPoint.getY());
            myFileWriter.write(NEW_LINE);
            myFileWriter.write(myResources.getString("BACKGROUND") +
                               myResources.getString("EQUALS") +
                               myGrid.getBackground().getFileName());
            myFileWriter.close();
        }
        catch (IOException e) {
            LEController.showErrorMsg(myResources.getString("WRITING_ERROR"));
        }
    }
}
