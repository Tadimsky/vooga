package vooga.rts.leveleditor.components;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import vooga.rts.leveleditor.Exceptions.MapNotMatchException;

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
        
        myScanner = new Scanner(resourceFile);
        
        loadTitle();
        try {
            loadPlayers();
            loadSize();
            loadTileIndex();
            loadTerrainIndex();
            loadTiles();
            loadTerrains();
            loadResources();
        }
        catch (MapNotMatchException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        
        
        myScanner.close(); 
    }
    
    public void loadMapFile(String filePath) throws FileNotFoundException {
        File bufferFile = new File(filePath);
        loadMapFile(bufferFile);
    }
    
    private void loadTitle() {
        String line = myScanner.nextLine();
        line = myScanner.nextLine();
        line = myScanner.nextLine();
        myMap.setMyMapName(myXMLParser.getTitle(line));
        line = myScanner.nextLine();
        myMap.setMyDescription(myXMLParser.getTitle(line));
    
    }
    
    
    
    private void loadPlayers() throws MapNotMatchException {
       String line = myScanner.nextLine();  
       line = myScanner.nextLine();
       while(line.contains("player") && line.contains("ID")) {
           String[] content = myXMLParser.splitByBlanks(myXMLParser.splitSlash(line));
           if(content.length != 4) throw new MapNotMatchException();
           String x = myXMLParser.cutKeyAndValue(content[2])[1];
           String y = myXMLParser.cutKeyAndValue(content[3])[1];
           myMap.addPlayer(Integer.parseInt(x),Integer.parseInt(y));
           line = myScanner.nextLine();
       }
    }
    
    
    
    private void loadSize() throws MapNotMatchException {
        String line = myScanner.nextLine();
        while( !line.contains("tilesize")) {
            line = myScanner.nextLine();
        }
        String[] sizeContent = myXMLParser.splitByBlanks(myXMLParser.splitSlash(line));
        if(sizeContent.length != 3) throw new MapNotMatchException();
        String tileWidth = myXMLParser.cutKeyAndValue(sizeContent[1])[1];
        String tileHeight = myXMLParser.cutKeyAndValue(sizeContent[1])[1];
        line = myScanner.nextLine();
        String[] amountContent = myXMLParser.splitByBlanks(myXMLParser.splitSlash(line));
        if(sizeContent.length != 3) throw new MapNotMatchException();
        String xCount = myXMLParser.cutKeyAndValue(amountContent[1])[1];
        String yCount = myXMLParser.cutKeyAndValue(amountContent[2])[1];
        myMap.setMyXSize(Integer.parseInt(xCount));
        myMap.setMyYSize(Integer.parseInt(yCount));
        myMap.initializeMap(Integer.parseInt(tileWidth), Integer.parseInt(tileHeight));
        
    }
    
    
    
    private void loadTileIndex() throws MapNotMatchException {
        String line = myScanner.nextLine();
        while (!(line.contains("tile") && line.contains("ID"))) {
            line = myScanner.nextLine();
        }
        while(line.contains("tile") && line.contains("ID")) {
            
            String[] tileContent = myXMLParser.splitByBlanks(myXMLParser.splitSlash(line));
            if(tileContent.length != 4) throw new MapNotMatchException();
            String tileID = myXMLParser.cutKeyAndValue(tileContent[1])[1];
            String tileImagePath = myXMLParser.cutKeyAndValue(tileContent[2])[1];
            String tileName = myXMLParser.cutKeyAndValue(tileContent[3])[1];
            myTileInformation.put(Integer.parseInt(tileID), tileName + "&" + tileImagePath);
            line = myScanner.nextLine();            
        }
    }
    
    private void loadTerrainIndex() throws MapNotMatchException {
        String line = myScanner.nextLine();
        while (!(line.contains("terrain") && line.contains("ID"))) {
            line = myScanner.nextLine();
        }
        while(line.contains("terrain") && line.contains("ID")) {
            
            String[] terrainContent = myXMLParser.splitByBlanks(myXMLParser.splitSlash(line));
            
            if(terrainContent.length != 5) throw new MapNotMatchException();
            String terrainID = myXMLParser.cutKeyAndValue(terrainContent[1])[1];
            String terrainImagePath = myXMLParser.cutKeyAndValue(terrainContent[2])[1];
            String terrainName = myXMLParser.cutKeyAndValue(terrainContent[3])[1];
            String terrainWalkability = myXMLParser.cutKeyAndValue(terrainContent[4])[1];
            myTerrainInformation.put(Integer.parseInt(terrainID), terrainName + "&" + terrainImagePath + "&" + terrainWalkability);
            line = myScanner.nextLine();            
        }
        System.out.println(myTerrainInformation.size());
        for(int i = 1 ; i<myTerrainInformation.size() + 1 ; i++) {
            System.out.println(myTerrainInformation.get(i));
        }
    }
    
    
    
    private void loadTiles() {
        String line = myScanner.nextLine();
        while( !line.contains("<graphic>")) {
            line = myScanner.nextLine();
        }
        line = myScanner.nextLine();
        for(int i= 0 ; i < myMap.getMyXSize() ; i++) {
            String[] tileIndex = myXMLParser.splitByBlanks(line);
            for(int j = 0; j < myMap.getMyYSize() ; j++) {
                String tileID = tileIndex[j];
                if( !tileID.equals("0")) {
                    myMap.getMapNode(i, j).setTile(Integer.parseInt(tileID));
                }
            }
            line = myScanner.nextLine();
        }
    }
    
    

    private void loadTerrains() {
    
    }
    
    private void loadResources() {
        
    }
    
    public static void main(String[] args) {
        MapLoader myLoader = new MapLoader();
        try {
            myLoader.loadMapFile(System.getProperty("user.dir") + "./src/test.xml");
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
