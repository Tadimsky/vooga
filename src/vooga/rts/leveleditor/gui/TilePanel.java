package vooga.rts.leveleditor.gui;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import vooga.rts.leveleditor.components.EditableTile;
import vooga.rts.util.Pixmap;

/**This class represents for the tile panel for the level editor
 * 
 * @author Ziqiang Huang
 *
 */
@SuppressWarnings("serial")
public class TilePanel extends MapComponentPanel {
    
    /**
     * Constructor for this class
     * 
     * @param canvas the canvas that holds the panel
     */
    public TilePanel(Canvas canvas) {
        super(canvas);
    }    
    
    /**
     * add the tile button on the panel
     */
    @Override
    public void addButton() {
        for(int i=0; i<myFiles.length; ++i) {
            try {
                BufferedImage image = ImageIO.read(myFiles[i]);
                Pixmap image1 = new Pixmap(image);
                myPanel.add(new TileButton(new EditableTile(image1, 0, 0, new Dimension(0,0), i+1, "", myFiles[i].getName(),false),image,this));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * set the current select tile for the canvas
     * @param myTile
     */

    public void setCurrentSelectTile(EditableTile myTile) {
        myCanvas.setCurrentSelectTile(myTile);        
    }        
}
