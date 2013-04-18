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
    private ResourceBundle myTileResources;
    
    public MapSaver(EditableMap map) throws IOException {
        
        mySavingMap = map;
        myTerrainResources = ResourceBundle.getBundle(RESOURCE_PATH + "TerrainIndex" );
        myTileResources = ResourceBundle.getBundle(RESOURCE_PATH+"TileIndex");
    }
    
    
    public void generateMapFile(File mySaveFile) throws IOException {
       
        if(!mySaveFile.exists()) {
            mySaveFile.createNewFile();
        }
        
        myFileWriter = new FileWriter(mySaveFile);
         
        
        writeTitle();
        writePlayers();
        writeSize();
        writeTileIndex();
        writeTerrainIndex();
        writeTiles();
        writeTerrains();
        writeResources();
        
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
            myFileWriter.write("         <player ID="+ i +" X=" + (int)loc.getX() + " Y=" + (int)loc.getY() + " />\r\n");
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
    
    private void writeTileIndex() throws IOException {
        myFileWriter.write("      <tiles>\r\n");
        for(String str : myTileResources.keySet()) {
            String content = myTileResources.getString(str);
            String[] buffer = content.split("&");
            String name = buffer[0];
            String imageName = "tiles/" + buffer[1];
            myFileWriter.write("         <terrain ID=" + str + " image=\"" + imageName+"\""+  " name=\"" + name  + "\" />\r\n");
        }
        
        myFileWriter.write("      </tiles>\r\n");
                
    }
    
    
    private void writeTerrainIndex() throws IOException {
        
        myFileWriter.write("      <terraintype>\r\n");
        for(String str : myTerrainResources.keySet()) {
            String content = myTerrainResources.getString(str);
            String[] buffer = content.split("&");
            String name = buffer[0];
            String imageName = "terrain/" + buffer[1];
            int walkability = Integer.parseInt(buffer[2]);
            myFileWriter.write("         <terrain ID=" + str  + " image=\"" + imageName + "\""+ " name=\"" + name + "\"" + " walkability =" + walkability + " />\r\n");
        }
        myFileWriter.write("      </terraintype>\r\n");
        myFileWriter.write("   </resources>\r\n");
    }
    
    private void writeTiles() throws IOException {
        int x = mySavingMap.getMyXSize();
        int y = mySavingMap.getMyYSize();
        myFileWriter.write("   <graphic>\r\n");
        for(int i = 0 ; i < x ; i++) {
            myFileWriter.write("      ");
            for(int j = 0 ; j < y ; j++) {
                EditableNode node = mySavingMap.getMapNode(i, j);
                char c = (char)(node.getMyTile().getMyID());
                myFileWriter.write(c);
            }
            myFileWriter.write("\r\n");
            
        }
    
        myFileWriter.write("   </graphic>\r\n");
    }
    
    private void writeTerrains() throws IOException {
        myFileWriter.write("   <terrain>\r\n");
        int layers = mySavingMap.getLayerNumber();
        for(int i = 1 ; i < layers + 1 ; i++) {
            MapLayer bufferLayer = mySavingMap.getLayer(i);
            
            myFileWriter.write("      <layer level = " + i +">\r\n");
            for(Terrain ter : bufferLayer.getTerrainSet()) {
                int ID = ter.getMyID();
                int x = (int)ter.getMyLocation().getX();
                int y = (int)ter.getMyLocation().getY();
                myFileWriter.write("         <terrain ID=" + ID + " X=" + x + " Y=" + y + " />\r\n");    
            }
            myFileWriter.write("      </layer>\r\n");
        }
        myFileWriter.write("   </terrain>\r\n");
    }
    
    private void writeResources() throws IOException{
        myFileWriter.write("   <resources>\r\n");
        for(Resource res : mySavingMap.getResourceSet()) {
            String name = res.getMyName();
            int x = res.getMyX();
            int y = res.getMyY();
            myFileWriter.write("      <resource type=\"" + name + "\" X=" + x + " Y=" + y + " />\r\n");    
        }
        myFileWriter.write("   </resources>\r\n");
        myFileWriter.write("</map>\r\n");
    }
}
