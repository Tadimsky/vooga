package vooga.rts.leveleditor.components;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * this class is responsible for generating a XML format map file
 * 
 * @author Richard Yang
 *
 */

public class MapLoader {
    
    private Scanner myScanner;
    private EditableMap myMap;
    private Map<Integer, String> myTileInformation;
    private Map<Integer, String> myTerrainInformation;
    private XMLParser myXMLParser;
   
    
    public MapLoader() {
        myMap = new EditableMap();
        myTileInformation = new HashMap<Integer, String>();
        myTerrainInformation = new HashMap<Integer, String>();
        myXMLParser = new XMLParser();
    }
    
    public void loadMapFile(File resourceFile) throws FileNotFoundException {
        
        Scanner myScanner = new Scanner(resourceFile);
        
        loadTitle();
        loadPlayers();
        loadSize();
        loadTileIndex();
        loadTerrainIndex();
        loadTiles();
        loadTerrains();
        loadResources();
        
        myScanner.close(); 

    }
    
    private void loadTitle() {
        String line = myScanner.nextLine();
        line = myScanner.nextLine();
        line = myScanner.nextLine();
    }
    
    
    
    private void loadPlayers() {
        
    }
    
    
    
    private void loadSize() {
    
    }
    
    
    
    private void loadTileIndex() {
    
    }
    
    
    
    private void loadTerrainIndex() {
    
    }
    
    
    
    private void loadTiles() {
    
    }
    
    

    private void loadTerrains() {
    
    }
    
    private void loadResources() {
        
    }
}
