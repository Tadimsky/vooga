package vooga.rts.gamedesign.sprite.gamesprites.interactive.buildings;

import java.awt.Dimension;

import vooga.rts.gamedesign.sprite.gamesprites.interactive.IOccupiable;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.gamedesign.upgrades.UpgradeTree;
import vooga.rts.util.Location;
import vooga.rts.util.Location3D;
import vooga.rts.util.Pixmap;
import vooga.rts.util.Sound;
import vooga.towerdefense.gameElements.Unit;


/**
 * This is an abstract class that represents a building. It will be extended
 * by specific types of buildings such as AttackTower.
 * 
 * @author Ryan Fishel
 * @author Kevin Oh
 * @author Francesco Agosti
 * @author Wenshun Liu
 * 
 */
public abstract class Building extends InteractiveEntity implements IOccupiable {
    public static final int MAXHEALTH = 100;
    private static UpgradeTree myUpgradeTree;
    
    public Building (Pixmap image,
            Location3D center,
            Dimension size,
            Sound sound,
            int playerID,
            int health) {
    	super(image, center, size, sound, playerID, health);
    }
    
    public Building (Pixmap image,
                     Location3D center,
                     Dimension size,
                     Sound sound,
                     int playerID,
                     int health, UpgradeTree upgradeTree) {
        super(image, center, size, sound, playerID, MAXHEALTH);
        if (upgradeTree != null) {
        	myUpgradeTree = upgradeTree;
        }
    }
    
    @Override
    public UpgradeTree getUpgradeTree() {
    	return myUpgradeTree;
    }

    public void getOccupied (Unit unit) {
        //u.occupy(this);
    }
}
