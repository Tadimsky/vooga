package vooga.rts.action;

import vooga.rts.gamedesign.sprite.gamesprites.interactive.units.Unit;
import vooga.rts.util.Pixmap;


public class UpgradeAction extends Action {
    // private Unit myUnit;

    public UpgradeAction (String name, Pixmap image, String description, Unit unit) {
        super(name, image, description);
        // myUnit = unit;
    }

}
