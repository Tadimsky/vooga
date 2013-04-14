package vooga.rts.leveleditor.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import vooga.rts.leveleditor.components.Resource;

/**
 * This Panel holds all the map resources designer can use
 * to create the map
 * 
 * @author Ziqiang Huang
 *
 */

public class ResourcePanel extends JPanel {

    public static final String IMAGE_PATH_1 = "/vooga/rts/leveleditor/resource/tree1.jpg";
    public static final String IMAGE_PATH_2 = "/vooga/rts/leveleditor/resource/rock.gif";
    public static final String IMAGE_PATH_3 = "/vooga/rts/leveleditor/resource/river.png";
    public static final String IMAGE_PATH_4 = "/vooga/rts/leveleditor/resource/grass.gif";

    private Canvas myCanvas;
    private JPanel myPanel;
    private BufferedImage myImage;

    /**
     * Constructor for this class
     * @param canvas: the canvas which holds this panel;
     */
    public ResourcePanel(Canvas canvas) {
        myCanvas = canvas;
        myPanel = new JPanel();
        myPanel.setLayout(new GridLayout(0,4));

        add(myPanel, BorderLayout.NORTH);
        addResouceButton();
    }

    /**
     * Initialize the ResourceButton on this panel
     */
    public void addResouceButton() {
        setImageRelative(IMAGE_PATH_1);
        myPanel.add(new ResourceButton(new Resource(1,"Tree",myImage),this));
        setImageRelative(IMAGE_PATH_2);
        myPanel.add(new ResourceButton(new Resource(2,"Rock",myImage),this));
        setImageRelative(IMAGE_PATH_3);
        myPanel.add(new ResourceButton(new Resource(3,"River",myImage),this));
        setImageRelative(IMAGE_PATH_4);
        myPanel.add(new ResourceButton(new Resource(3,"Grass",myImage),this));

    }

    /**
     * Set the image of resource according to the path
     * @param path
     */
    public void setImageRelative (String path) {
        try {
            myImage = ImageIO.read(this.getClass().getResource(path));
        }
        catch (Exception e) {

        }
    }

    public Canvas getCanvas() {
        return myCanvas;
    }

    /**
     * Set the current resource selected by user
     * @param r
     */
    public void setCurrentSelectResource(Resource r) {
        myCanvas.setCurrentSelectResource(r);
    }



}
