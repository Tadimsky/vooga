package vooga.rts.gamedesign.sprite.gamesprites.interactive.buildings;

import java.awt.Dimension;

import vooga.rts.gamedesign.Interval;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.IOccupiable;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.gamedesign.upgrades.UpgradeTree;
import vooga.rts.manager.GameBuildingManager;
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
public class Building extends InteractiveEntity implements IOccupiable {
    public static final int MAXHEALTH = 100;
    private static UpgradeTree myUpgradeTree;
    //TODO: probably shouldn't be stored in Building. Should try Observer pattern later?
    private static GameBuildingManager myGameBuildingManager;
    
    public Building (Pixmap image,
            Location3D center,
            Dimension size,
            Sound sound,
            int playerID,
            int health,
            double buildTime) {
    	super(image, center, size, sound, playerID, health, buildTime);
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
	
    public void setGameBuildingManager(GameBuildingManager gameBuildingManager) {
    	myGameBuildingManager = gameBuildingManager;
    }
    
    public GameBuildingManager getGameBuildingManager() {
    	return myGameBuildingManager;
    }
    
    @Override
	public void getOccupied(
			vooga.rts.gamedesign.sprite.gamesprites.interactive.units.Unit unit) {
		// TODO Auto-generated method stub
		
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

    public void getOccupied (Unit unit) {
        //u.occupy(this);
    }

}
