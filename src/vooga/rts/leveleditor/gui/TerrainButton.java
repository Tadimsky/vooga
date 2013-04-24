package vooga.rts.leveleditor.gui;

import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import util.input.Input;
import util.input.InputClassTarget;
import util.input.InputMethodTarget;
import util.input.PositionObject;
import vooga.rts.leveleditor.components.EditableTerrain;

/**This class represents the terrain button on terrain panel
 * 
 * @author Ziqiang Huang
 *
 */
@SuppressWarnings("serial")
@InputClassTarget
public class TerrainButton extends JToggleButton {

    public static final String INPUT_DIR = "vooga.rts.resources.properties.Input";

    private EditableTerrain myTerrain;
    private TerrainPanel myOwner;
    private Input myInput;
    private boolean isInitialized;
    
    /**
     * Constructor for the map
     * 
     * @param t the terrain the button represents
     * @param image the buffered image for the button icon
     * @param owner the panel that holds the button
     */
    public TerrainButton (EditableTerrain t, BufferedImage image, TerrainPanel owner) {
        myTerrain = t;
        t.getImage();
        Image image2 = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        myOwner = owner;
        myInput = new Input(INPUT_DIR, this);
        myInput.addListenerTo(this);

        setToolTipText(t.getMyName());
        setIcon(new ImageIcon(image2));
        setMargin(new Insets(2, 2, 2, 2));

    }
    
    /**
     * get the terrain from the button when the button is clicked
     * @param p
     */
    @InputMethodTarget(name = "onLeftMouseDown")
    public void getResource (PositionObject p) {
        if(!isInitialized) {
            showCustmizationDailog();
        }
        myOwner.setCurrentSelectTerrain(myTerrain);
        myOwner.getCanvas().setMode(MapPanel.TERRAINMODE);
    }

    /**
     * show the custmization dailog for the terrain
     * users should enter in the type and walkability of the map
     */
    private void showCustmizationDailog() {
        JTextField terrainType = new JTextField();
        JTextField walkability = new JTextField();
        Object[] message = {"Type", terrainType, "Walkability", walkability};
        int option = JOptionPane.showConfirmDialog(null, message,"Set Terrain",JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String type = terrainType.getText();
                myTerrain.setType(type);
                int w = Integer.parseInt(walkability.getText());
                myTerrain.setWalkability(w);
                isInitialized = true;
            }
            catch (Exception e1) {              
            }
        }
        
    }

}
