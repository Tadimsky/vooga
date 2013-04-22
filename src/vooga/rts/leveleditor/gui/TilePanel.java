package vooga.rts.leveleditor.gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;
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
//        for(String str : myResource.keySet()) {
//            myPanel.add(new TileButton(new Tile(Integer.parseInt(str)),this));
//        }
        
        for(int i=0; i<myFiles.length; ++i) {
            try {
                BufferedImage image = ImageIO.read(myFiles[i]);
                myPanel.add(new TileButton(new Tile(i+1,myFiles[i].getName(),image),this));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setCurrentSelectTile(Tile t) {
        myCanvas.setCurrentSelectTile(t);        
    }        
}
