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

@InputClassTarget
public class TerrainButton extends JToggleButton {
    
    public static final String INPUT_DIR = "vooga.rts.resources.properties.Input";
    
    private Terrain myTerrain;
    private TerrainPanel myOwner;
    private BufferedImage myIcon;
    private Input myInput;

    public TerrainButton(Terrain t, TerrainPanel owner) {
        myTerrain = t;
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
        myOwner.setCurrentSelectTerrain(myTerrain);
        myOwner.getCanvas().setMode(MapPanel.TERRAINMODE);
    }

}
