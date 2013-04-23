package vooga.rts.leveleditor.gui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;
import vooga.rts.leveleditor.components.Terrain;
import vooga.rts.leveleditor.components.Tile;


public class TerrainPanel extends MapComponentPanel {

    public TerrainPanel (Canvas canvas) {
        super(canvas);
    }

    @Override
    public void setResourceBundle () {
        myResource = ResourceBundle.getBundle(RELATIVE_PATH + "TerrainIndex");
    }

    @Override
    public void addButton () {
        for(int i=0; i<myFiles.length; ++i) {
            try {
                BufferedImage image = ImageIO.read(myFiles[i]);
                myPanel.add(new TerrainButton(new Terrain(i+1,myFiles[i].getName(),image),this));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void setCurrentSelectTerrain (Terrain t) {
        myCanvas.setCurrentSelectTerrain(t);
        
    }

}
