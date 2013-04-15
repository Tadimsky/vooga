package vooga.scroller.level_editor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import vooga.scroller.util.Sprite;


public class LevelWriter {

    private static final String NEW_LINE = System.getProperty("line.separator");
    private static final String BEGIN_LEVEL = "/level";
    private static final String BEGIN_KEY = "/key";
    private static final String KEY_CREATOR = "abcdefghijklmnopqrstuvwxyz" +
    		"0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()";
    private static final char SPACE = '?';
    private int myKeyCounter;
    private Map<String, Character> myMap;
    private FileWriter myFileWriter;
    private LEGrid myGrid;

    public void createFile (File file, LEGrid levelGrid) {
        myGrid = levelGrid;
        myKeyCounter = 0;
        myMap = new HashMap<String,Character>();
        try {
            myFileWriter = new FileWriter(file);
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        writeLevel();
        writeKey();
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
                        if(!myMap.containsKey(s.getClass().getCanonicalName())){
                            myMap.put(s.getClass().getCanonicalName(), KEY_CREATOR.charAt(myKeyCounter));
                            myKeyCounter++;
                        }
                        myFileWriter.write(myMap.get(s.getClass().getCanonicalName()));
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
        try{
            myFileWriter.write(BEGIN_KEY);
            for(String key:keySet){
                myFileWriter.write(NEW_LINE);
                myFileWriter.write(""+myMap.get(key)+'='+key);
            }
            myFileWriter.close();
        }
        catch(IOException e){
         // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
    
    }

}
