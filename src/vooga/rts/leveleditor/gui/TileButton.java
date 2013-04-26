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
import vooga.rts.leveleditor.components.EditableTile;
/**
 * This class represents for the Tile button for the level editor
 * 
 * @author Ziqiang Huang
 *
 */

@SuppressWarnings("serial")
@InputClassTarget
public class TileButton extends JToggleButton {

    public static final String INPUT_DIR = "vooga.rts.resources.properties.Input";

    private EditableTile myTile;
    private TilePanel myOwner;
    private Input myInput;
    private boolean isInitialized; 
    
    /**
     * Constructor for the class
     * 
     * @param t the editabletile for the button
     * @param image the buffered image for the tile icon
     * @param owner the panel that holds the button
     */
    public TileButton (EditableTile t, BufferedImage image, TilePanel owner) {
        myTile = t;
        t.getImage();
        Image image2 = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        myOwner = owner;
        myInput = new Input(INPUT_DIR, this);
        myInput.addListenerTo(this);

        setToolTipText(t.getMyName());
        setIcon(new ImageIcon(image2));
        setMargin(new Insets(2, 2, 2, 2));

    }


    @InputMethodTarget(name = "onLeftMouseDown")
    public void getResource (PositionObject p) {
        if(!isInitialized) {
            showCustmizationDailog();
            }
        myOwner.setCurrentSelectTile(myTile);
        myOwner.getCanvas().setMode(MapPanel.TILEMODE);
    }


    private void showCustmizationDailog() {
        JTextField tileType = new JTextField();
        Object[] message = {"Type", tileType};
        int option = JOptionPane.showConfirmDialog(null, message,"Set resource",JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String type = tileType.getText();
                myTile.setType(type);
                isInitialized = true;
            }
            catch (Exception e1) {
               
            }
        }
        
    }

}
