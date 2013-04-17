package vooga.rts.leveleditor.components;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;
import vooga.rts.util.Location;

/**
 * this class is responsible for generating a XML format map file
 * 
 * 
 * @author Richard Yang
 *
 */
public class MapSaver {
    
    private static final String RESOURCE_PATH = "vooga.rts.leveleditor.resource.";
    
    private FileWriter myFileWriter;
    private EditableMap mySavingMap;
    private ResourceBundle myTerrainResources;
    
    public MapSaver(EditableMap map) throws IOException {
        
        mySavingMap = map;
        myTerrainResources = ResourceBundle.getBundle(RESOURCE_PATH + "TerrainID" );
    }
    
    
    public void generateMapFile(File mySaveFile) throws IOException {
       
        if(!mySaveFile.exists()) {
            mySaveFile.createNewFile();
        }
        
        myFileWriter = new FileWriter(mySaveFile);
            
        mySavingMap.addPlayer(10, 10);
        mySavingMap.addPlayer(20, 20);
        mySavingMap.addPlayer(30, 30);
        mySavingMap.addPlayer(40, 40);
        mySavingMap.addPlayer(50, 50);
        
        writeTitle();
        writePlayers();
        writeSize();
        writeTiles();
        writeTerrainIndex();
        
        myFileWriter.close();
    }

    private void writeTitle() throws IOException {
        String name = mySavingMap.getMyMapName();
        String description = mySavingMap.getMyDescription();
        myFileWriter.write("<map>\r\n");
        myFileWriter.write("   <info>\r\n");
        myFileWriter.write("      <name>"+ name + "</name>\r\n");
        myFileWriter.write("      <desc>"+ description + "</desc>\r\n");
    } 

    private void writePlayers() throws IOException {
        Map<Integer , Location> buffer = mySavingMap.getAllPlayers();
        myFileWriter.write("      <players number = "+ buffer.size() +">\r\n");
        for(Integer i : buffer.keySet()) {
            Location loc = buffer.get(i);
            myFileWriter.write("         <player ID="+ i +" X=" + loc.getX() + " Y=" + loc.getY() + " />\r\n");
        }
        myFileWriter.write("      </players>\r\n");
        myFileWriter.write("   </info>\r\n");
    }
    
    private void writeSize() throws IOException {
        int width = mySavingMap.getMapNode(0, 0).getMyX();
        int height = mySavingMap.getMapNode(0, 0).getMyY();
        int x = mySavingMap.getMyXSize();
        int y = mySavingMap.getMyYSize();
        
        myFileWriter.write("   <resources>\r\n");
        myFileWriter.write("      <info>\r\n");
        myFileWriter.write("         tilesize width=" + width + " height=" + height + " />\r\n");
        myFileWriter.write("         tileamount X=" + x + " Y=" + y + " />\r\n");
        myFileWriter.write("      </info>\r\n");
    }
    
    private void writeTiles() throws IOException {
        int x = mySavingMap.getMyXSize();
        int y = mySavingMap.getMyYSize();
        myFileWriter.write("      <tiles>\r\n");
    
        for(int i = 0 ; i < x ; i++) {
            for(int j = 0 ; j < y ; j++) {
                int id = i*y + j ;
                String name = mySavingMap.getMapNode(i, j).getTileType();
                myFileWriter.write("         <tiles ID=" + id + " name=\"" + name + "\" />\r\n" );
            }
        }
        myFileWriter.write("      </tiles>\r\n");
    }
    
    private void writeTerrainIndex() throws IOException {
        
        myFileWriter.write("      <terraintype>\r\n");
        for(String str : myTerrainResources.keySet()) {
            String content = myTerrainResources.getString(str);
            String[] buffer = content.split("&");
            String name = buffer[0];
            int walkability = Integer.parseInt(buffer[1]);
            myFileWriter.write("         <terrain ID=" + str + " name=\"" + name + "\"" + " walkability =" + walkability + " />\r\n");
        }
        myFileWriter.write("      </terraintype>\r\n");
        myFileWriter.write("   </resources>\r\n");
    } 
}
