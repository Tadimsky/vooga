package vooga.scroller.level_editor.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import util.Location;
import vooga.scroller.level_editor.controllerSuite.LEGrid;
import vooga.scroller.util.Sprite;


public class LevelWriter {

    private static final char EQUALS = '=';
    private static final String NEW_LINE = System.getProperty("line.separator");
    private static final String BEGIN_LEVEL = "/level";
    private static final String BEGIN_LIB_PATH = "/lib";
    private static final String BEGIN_KEY = "/key";
    private static final String BEGIN_SETTINGS = "/settings";
    private static final String KEY_CREATOR = "abcdefghijklmnopqrstuvwxyz" +
                                              "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()";
    private static final char SPACE = ' ';
    private static final String START_POINT = "StartPoint";
    private static final String BACKGROUND = "Background";
    private int myKeyCounter;
    private Map<String, Character> myMap;
    private FileWriter myFileWriter;
    private LEGrid myGrid;
    private Location myStartPoint;
    
    
    public void createFile (File file, LEGrid levelGrid, String libPath) {
        myGrid = levelGrid;
        myStartPoint = myGrid.removeStartPoint();
        myKeyCounter = 0;
        myMap = new HashMap<String, Character>();
        try {
            myFileWriter = new FileWriter(file);
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        writeLevel();
        writeLib(libPath);
        writeKey();
        writeSettings();
        myGrid.addStartPoint((int) myStartPoint.getX(), (int) myStartPoint.getY());
    }

    private void writeLevel () {
        try {
            myFileWriter.write(BEGIN_LEVEL);
            for (int i = 0; i < myGrid.getSize().height; i++) {
                myFileWriter.write(NEW_LINE);
                for (int j = 0; j < myGrid.getSize().width; j++) {
                    Sprite s = myGrid.getSprite(j, i);
                    if (s == null) {
                        myFileWriter.write(SPACE);
                    }
                    else {
                        String spriteName = s.getClass().getSimpleName();
                        if(!myMap.containsKey(spriteName)){
                            myMap.put(spriteName, KEY_CREATOR.charAt(myKeyCounter));
                            myKeyCounter++;
                        }
                        myFileWriter.write(myMap.get(spriteName));
                    }
                }
            }
            myFileWriter.write(NEW_LINE);
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    
    private void writeLib (String libPath) {
        try {
            myFileWriter.write(BEGIN_LIB_PATH);
            myFileWriter.write(NEW_LINE);
            myFileWriter.write(libPath);
            myFileWriter.write(NEW_LINE);
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void writeKey () {
        Set<String> keySet = myMap.keySet();
        try {
            myFileWriter.write(BEGIN_KEY);
            for (String key : keySet) {
                myFileWriter.write(NEW_LINE);
                myFileWriter.write("" + myMap.get(key) + EQUALS + key);
            }
            myFileWriter.write(NEW_LINE);
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void writeSettings () {
        try {
            myFileWriter.write(BEGIN_SETTINGS);
            myFileWriter.write(NEW_LINE);
            myFileWriter.write(START_POINT + EQUALS +
                               (int) myStartPoint.getX() + SPACE + (int) myStartPoint.getY());
            myFileWriter.write(NEW_LINE);
            myFileWriter.write(BACKGROUND + EQUALS +
                               myGrid.getBackground().getFileName());
            myFileWriter.close();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
