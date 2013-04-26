package vooga.rts.leveleditor.gui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import vooga.rts.leveleditor.components.EditableTerrain;
import vooga.rts.util.Pixmap;

/**
 * This class represents the terrain panel for the level editor
 * 
 * @author Ziqiang Huang
 *
 */

@SuppressWarnings("serial")
public class TerrainPanel extends MapComponentPanel {

    /**
     * Constructor for the class
     * 
     * @param canvas the canvas that holds the panel
     */
    public TerrainPanel (Canvas canvas) {
        super(canvas);
    }
    
    /**
     * add terrain buttons based on the imported images
     */
    @Override
    public void addButton () {
        for(int i=0; i<myFiles.length; ++i) {
            try {
                BufferedImage image = ImageIO.read(myFiles[i]);
                Pixmap image1 = new Pixmap(image);
                myPanel.add(new TerrainButton(new EditableTerrain(image1,0,0,0,i+1,"",myFiles[i].getName(),0),image,this));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Set the current select terrain for the panel
     * @param t the terrain of this button
     */
    public void setCurrentSelectTerrain (EditableTerrain t) {
        myCanvas.setCurrentSelectTerrain(t);
        
    }

}
