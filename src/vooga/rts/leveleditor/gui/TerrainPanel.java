package vooga.rts.leveleditor.gui;

import java.util.ResourceBundle;
import vooga.rts.leveleditor.components.Terrain;

public class TerrainPanel extends MapComponentPanel {

    public TerrainPanel(Canvas canvas) {
        super(canvas);
    }

    @Override
    public void setResourceBundle() {
        myResource = ResourceBundle.getBundle(RELATIVE_PATH+"TerrainIndex");       
    }

    @Override
    public void addButton() {
        for(String str : myResource.keySet()) {
            myPanel.add(new TerrainButton(new Terrain(Integer.parseInt(str)),this));
        } 
    }
    
    public void setCurrentSelectTerrain(Terrain t) {
        myCanvas.setCurrentSelectTerrain(t);
        
    }

}
