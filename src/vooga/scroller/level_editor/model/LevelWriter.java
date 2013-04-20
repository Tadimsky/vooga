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
    private static final String BEGIN_KEY = "/key";
    private static final String BEGIN_SETTINGS = "/settings";
    private static final String KEY_CREATOR = "abcdefghijklmnopqrstuvwxyz" +
                                              "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()";
    private static final char SPACE = ' ';
    private static final String START_POINT = "StartPoint";
    private static final String END_POINT = "EndPoint";
    private int myKeyCounter;
    private Map<String, Character> myMap;
    private FileWriter myFileWriter;
    private LEGrid myGrid;
    private Location myStartPoint;
    private Location myPortal;

    public void createFile (File file, LEGrid levelGrid) {
        myGrid = levelGrid;
        myStartPoint = myGrid.removeStartPoint();
        myPortal = myGrid.removePortal();
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
        writeKey();
        writeSettings();
        myGrid.addStartPoint((int) myStartPoint.getX(), (int) myStartPoint.getY());
        myGrid.addDoor((int) myPortal.getX(), (int) myPortal.getY());
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
                        if(!myMap.containsKey(s.getClass().getName())){
                            myMap.put(s.getClass().getName(), KEY_CREATOR.charAt(myKeyCounter));
                            myKeyCounter++;
                        }
                        myFileWriter.write(myMap.get(s.getClass().getName()));
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
            myFileWriter.write(END_POINT + EQUALS +
                               (int) myPortal.getX() + SPACE + (int) myPortal.getY());
            myFileWriter.close();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
