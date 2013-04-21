package vooga.rts.gamedesign.sprite.gamesprites.interactive.buildings;

import java.awt.Dimension;

import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.gamedesign.upgrades.UpgradeTree;
import vooga.rts.manager.GameBuildingManager;
import vooga.rts.util.Location3D;
import vooga.rts.util.Pixmap;
import vooga.rts.util.Sound;


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
public class Building extends InteractiveEntity {
    public static final int MAXHEALTH = 100;
    private static UpgradeTree myUpgradeTree;
    
    public Building (Pixmap image,
            Location3D center,
            Dimension size,
            Sound sound,
            int playerID,
            int health,
            double buildTime) {
    	this(image, center, size, sound, playerID, health, null, buildTime);
    	}
    
    public Building (Pixmap image,
                     Location3D center,
                     Dimension size,
                     Sound sound,
                     int playerID,
                     int health, UpgradeTree upgradeTree, double buildTime) {
        super(image, center, size, sound, playerID, MAXHEALTH, buildTime);
        if (upgradeTree != null) {
        	myUpgradeTree = upgradeTree;
        }
    }
    
	@Override
	public InteractiveEntity copy() {
		return new Building(getImage(), getWorldLocation(), getSize(), getSound(),
				getPlayerID(), getHealth(), getBuildTime());
	}

	@Override
	public void addActions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
    public UpgradeTree getUpgradeTree() {
    	return myUpgradeTree;
    }
}
