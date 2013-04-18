package vooga.rts.leveleditor.gui;

import java.awt.Insets;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
import vooga.rts.input.Input;
import vooga.rts.input.InputClassTarget;
import vooga.rts.input.InputMethodTarget;
import vooga.rts.input.PositionObject;
import vooga.rts.leveleditor.components.Terrain;
import vooga.rts.leveleditor.components.Tile;

@InputClassTarget
public class TileButton extends JToggleButton {
    
    public static final String INPUT_DIR = "vooga.rts.resources.properties.Input";
    
    private Tile myTile;
    private TilePanel myOwner;
    private BufferedImage myIcon;
    private Input myInput;

    public TileButton(Tile t, TilePanel owner) {
        myTile = t;
        myIcon = t.getMyImage();
        myOwner = owner;
        myInput = new Input(INPUT_DIR, this);
        myInput.addListenerTo(this);

        myIcon.getGraphics().drawImage(myIcon, 0, 0, 32, 32, null);
        setToolTipText(t.getMyName());
        setIcon(new ImageIcon(myIcon));
        setMargin(new Insets(2,2,2,2));
        
    }
    
    @InputMethodTarget(name="onLeftMouseDown")
    public void getResource(PositionObject p) {
        myOwner.getCanvas().remove(false);
        myOwner.setCurrentSelectTile(myTile);
        myOwner.getCanvas().setMode(MapPanel.TILEMODE);
    }

}
