package vooga.rts.leveleditor.gui;

import java.util.ResourceBundle;
import vooga.rts.leveleditor.components.Tile;

public class TilePanel extends MapComponentPanel {
    
    public TilePanel(Canvas canvas) {
        super(canvas);
    }
    
    @Override
    public void setResourceBundle() {
        myResource = ResourceBundle.getBundle(RELATIVE_PATH+"TileIndex");
    }
    
    @Override
    public void addButton() {
        for(String str : myResource.keySet()) {
            myPanel.add(new TileButton(new Tile(Integer.parseInt(str)),this));
        }
    }

    public void setCurrentSelectTile(Tile t) {
        myCanvas.setCurrentSelectTile(t);        
    }        
}
