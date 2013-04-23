package vooga.rts.leveleditor.gui;

import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
import util.input.Input;
import util.input.InputClassTarget;
import util.input.InputMethodTarget;
import util.input.PositionObject;
import vooga.rts.leveleditor.components.Tile;


@InputClassTarget
public class TileButton extends JToggleButton {

    public static final String INPUT_DIR = "vooga.rts.resources.properties.Input";

    private Tile myTile;
    private TilePanel myOwner;
    private Image myIcon;
    private Input myInput;

    public TileButton (Tile t, TilePanel owner) {
        myTile = t;
        myIcon = t.getMyImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        myOwner = owner;
        myInput = new Input(INPUT_DIR, this);
        myInput.addListenerTo(this);

        setToolTipText(t.getMyName());
        setIcon(new ImageIcon(myIcon));
        setMargin(new Insets(2, 2, 2, 2));

    }

    @InputMethodTarget(name = "onLeftMouseDown")
    public void getResource (PositionObject p) {
        myOwner.getCanvas().remove(false);
        myOwner.setCurrentSelectTile(myTile);
        myOwner.getCanvas().setMode(MapPanel.TILEMODE);
    }

}
