package vooga.rts.leveleditor.components;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import vooga.rts.util.Location;

/**
 * this class is responsible for generating a XML format map file
 * 
 * 
 * @author Richard Yang
 *
 */
public class MapSaver {

public void generateMapFile(EditableMap myMap , File mySavFile) throws IOException {
        
        if(!mySavFile.exists()) {
            mySavFile.createNewFile();
        }
            
        FileWriter myWriter = new FileWriter(mySavFile);
        
        writeTitle(myMap,myWriter);
        
        myMap.addPlayer(10, 10);
        myMap.addPlayer(20, 20);
        myMap.addPlayer(30, 30);
        myMap.addPlayer(40, 40);
        myMap.addPlayer(50, 50);
        
        writePlayers(myMap,myWriter);
        writeSize(myMap,myWriter);
        writeTiles(myMap,myWriter);
        
        myWriter.close();
    }

    public void writeTitle(EditableMap myMap, FileWriter myWriter) throws IOException {
        String name = myMap.getMyMapName();
        String description = myMap.getMyDescription();
        myWriter.write("<map>\r\n");
        myWriter.write("   <info>\r\n");
        myWriter.write("      <name>"+ name + "</name>\r\n");
        myWriter.write("      <desc>"+ description + "</desc>\r\n");
    } 

    public void writePlayers(EditableMap myMap, FileWriter myWriter) throws IOException {
        Map<Integer , Location> buffer = myMap.getAllPlayers();
        myWriter.write("      <players number = "+ buffer.size() +">\r\n");
        for(Integer i : buffer.keySet()) {
            Location loc = buffer.get(i);
            myWriter.write("         <player ID="+ i +" X=" + loc.getX() + " Y=" + loc.getY() + " />\r\n");
        }
        myWriter.write("      </players>\r\n");
        myWriter.write("   </info>\r\n");
    }
    
    public void writeSize(EditableMap myMap , FileWriter myWriter) throws IOException {
        int width = myMap.getMapNode(0, 0).getMyX();
        int height = myMap.getMapNode(0, 0).getMyY();
        int x = myMap.getMyXSize();
        int y = myMap.getMyYSize();
        
        myWriter.write("   <resources>\r\n");
        myWriter.write("      <info>\r\n");
        myWriter.write("         tilesize width=" + width + " height=" + height + " />\r\n");
        myWriter.write("         tileamount X=" + x + " Y=" + y + " />\r\n");
        myWriter.write("      </info>\r\n");
    }
    
    public void writeTiles(EditableMap myMap , FileWriter myWriter) throws IOException {
        int x = myMap.getMyXSize();
        int y = myMap.getMyYSize();
        myWriter.write("      <tiles>\r\n");
    
        for(int i = 0 ; i < x ; i++) {
            for(int j = 0 ; j < y ; j++) {
                int id = i*y + j ;
                String name = myMap.getMapNode(i, j).getTileType();
                myWriter.write("         <tiles ID=" + id + " name=\"" + name + "\" />\r\n" );
            }
        }
        myWriter.write("      </tiles>\r\n");
    }
}
