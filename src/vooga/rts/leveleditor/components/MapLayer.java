package vooga.rts.leveleditor.components;

import java.util.ArrayList;
import java.util.List;

public class MapLayer {
    
    private static final int DEFAULT_HEIGHT = 50;
    
    private int myHight;
    private List<Terrain> myTerrains;
    
    
    public MapLayer(int hight) {
           myHight = hight;
           myTerrains = new ArrayList<Terrain>();
    }
    
    public MapLayer() {
        this(DEFAULT_HEIGHT);
    }
    
    public void addTerrain(Terrain ter) {
        myTerrains.add(ter);
    }
    
    public void removeTerrain(int index) {
        myTerrains.remove(index);
        
    }
    
    public void removeTerrain(Terrain ter) {
        myTerrains.remove(ter);
        
    }
    
    
    
    
}
