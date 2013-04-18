package vooga.rts.leveleditor.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import vooga.rts.leveleditor.components.Resource;
import vooga.rts.leveleditor.components.Tile;

/**
 * This Panel holds all the map resources designer can use
 * to create the map
 * 
 * @author Ziqiang Huang
 *
 */

public class ResourcePanel extends MapComponentPanel {
    /**
     * Constructor for this class
     * @param canvas: the canvas which holds this panel;
     */
    public ResourcePanel(Canvas canvas) {
        super(canvas);
    }

    @Override
    public void setResourceBundle() {
        myResource = ResourceBundle.getBundle(RELATIVE_PATH+"ResourceIndex");
       
    }

    @Override
    public void addButton() {
        for(String str : myResource.keySet()) {
            myPanel.add(new ResourceButton(new Resource(Integer.parseInt(str)),this));
        }       
    }

    /**
     * Set the current resource selected by user
     * @param r
     */
    public void setCurrentSelectResource(Resource r) {
        myCanvas.setCurrentSelectResource(r);
    }

}
