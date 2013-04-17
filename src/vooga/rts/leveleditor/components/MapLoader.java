package vooga.rts.leveleditor.components;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * this class is responsible for generating a XML format map file
 * 
 * @author Richard Yang
 *
 */

public class MapLoader {
public void loadMapFile(EditableMap myMap , File resourceFile) throws FileNotFoundException {
        
        Scanner myScanner = new Scanner(resourceFile);
        String line = myScanner.nextLine();
        
        String[] sizeBuffer = line.split("\\*");
        int x = Integer.parseInt(sizeBuffer[0]);
        int y = Integer.parseInt(sizeBuffer[1]);
        int layerCount = Integer.parseInt(sizeBuffer[2]);
        
        EditableMap buffer = new EditableMap(x,y);
       
        
        line = myScanner.nextLine();
        
        while(line.contains("Player")) {
            String[] playerBuffer = line.split(" ");
            buffer.addPlayer(Integer.parseInt(playerBuffer[2]), Integer.parseInt(playerBuffer[3]));
            System.out.println("player added");
            line = myScanner.nextLine();
            
        }
        
        for(int l = 0 ; l<layerCount ; l++) {
            if(line.contains("*")) {
                line = myScanner.nextLine();
            }
            for(int i =0 ; i<x ; i++) {
                for(int j =0 ; j<y ; j++) {
                    if(Integer.parseInt(line.charAt(j)+"") != 0){
                        //buffer.getMapNode(i, j).addFeature(Integer.parseInt(line.charAt(j)+""));    
                    }
                }
                if(myScanner.hasNextLine()) {
                    line = myScanner.nextLine();
                } else {
                    break;
                }
            }
        }
        
        
        //myMap.myTileIndex = buffer.getMap();
        for(Integer i : buffer.getAllPlayers().keySet()) {
            myMap.addPlayer(buffer.getPlayer(i));
        }
        myMap.setMyXSize(x);
        myMap.setMyYSize(y);
       // myMap.refreshLayerNumber();
        
        
        myScanner.close();
    }
}
