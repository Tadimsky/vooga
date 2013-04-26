package vooga.rts.leveleditor.gui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import vooga.rts.leveleditor.components.EditableResource;
import vooga.rts.util.Location3D;
import vooga.rts.util.Pixmap;

/**
 * This Panel holds all the map resources designer can use
 * to create the map
 * 
 * @author Ziqiang Huang
 *
 */

@SuppressWarnings("serial")
public class ResourcePanel extends MapComponentPanel {
    /**
     * Constructor for this class
     * @param canvas: the canvas which holds this panel;
     */
    public ResourcePanel(Canvas canvas) {
        super(canvas);
    }
    
    /**
     * add the resource buttons on the panel based on the import images
     */
    @Override
    public void addButton() {
        for(int i=0; i<myFiles.length; ++i) {
            try {
                BufferedImage image = ImageIO.read(myFiles[i]);
                Pixmap image1 = new Pixmap(image);              
                myPanel.add(new ResourceButton(new EditableResource(image1, new Location3D (0,0,0),i+1, "",myFiles[i].getName(),0), image, this));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }     
    }

    /**
     * Set the current resource selected by user
     * @param r
     */
    public void setCurrentSelectResource(EditableResource r) {
        myCanvas.setCurrentSelectResource(r);
    }

}
