package vooga.rts.leveleditor.gui;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
import javax.swing.text.Position;
import vooga.rts.input.AlertObject;
import vooga.rts.input.Input;
import vooga.rts.input.InputClassTarget;
import vooga.rts.input.InputMethodTarget;
import vooga.rts.input.PositionObject;
import vooga.rts.leveleditor.components.Resource;

/**
 * This class represents the resource button on Resource Panel
 * User can click and add the resource onto the map
 *
 * @author Ziqiang Huang
 *
 */

@InputClassTarget
public class ResourceButton extends JToggleButton {

    public static final String INPUT_DIR = "vooga.rts.resources.properties.Input";

    private Resource myResource;
    private ResourcePanel myOwner;
    private BufferedImage myIcon;
    private Input myInput;

    /**
     * Constructor for this class
     * 
     * @param r : the Resource which the button represents
     * @param owner : the ResourcePanel which holds this button;
     */
    public ResourceButton (Resource r, ResourcePanel owner) {
        myResource = r;
        myIcon = r.getMyImage();
        myOwner = owner;
        myInput = new Input(INPUT_DIR, this);
        myInput.addListenerTo(this);

        myIcon.getGraphics().drawImage(myIcon, 0, 0, 32, 32, null);
        setToolTipText(r.getMyName());
        setIcon(new ImageIcon(myIcon));
        setMargin(new Insets(2,2,2,2));
    }

    @InputMethodTarget(name="onLeftMouseDown")
    public void getResource(PositionObject p) {
        myOwner.getCanvas().remove(false);
        myOwner.setCurrentSelectResource(myResource);
        myOwner.getCanvas().setMode(MapPanel.RESOURCEMODE);
    }

}
