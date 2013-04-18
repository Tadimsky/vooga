package vooga.rts.gamedesign.sprite.gamesprites.interactive.buildings;

import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import vooga.rts.gamedesign.action.Action;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.units.Unit;
import vooga.rts.gamedesign.upgrades.UpgradeNode;
import vooga.rts.gamedesign.upgrades.UpgradeTree;
import vooga.rts.util.Location;
import vooga.rts.util.Location3D;
import vooga.rts.util.Pixmap;
import vooga.rts.util.Sound;

/**
 * This class is the extension of Building class and represents a building that
 * is in charge of upgrades. 
 * 
 * @author Wenshun Liu
 *
 */
public class UpgradeBuilding extends Building{
	public int PRODUCE_TIME = 90;
	
	public UpgradeBuilding(Pixmap image, Location3D center, Dimension size,
			Sound sound, int playerID, int health) {
		super(image, center, size, sound, playerID, health, null);
	}
	
	/**
     * Adds the list of available upgrades into the list of available actions.
     */
    public void addUpgradeActions(UpgradeTree upgradeTree){
    	List<UpgradeNode> initialUpgrades = upgradeTree.getCurrentUpgrades();
    	addUpgradeActions(initialUpgrades);
    }
    
    public void addUpgradeActions(List<UpgradeNode> nodeList) {
    	for (final UpgradeNode u: nodeList) {
   		 getActions().add(new Action(u.getUpgradeName(), null, "An upgrade action"){
   	            @Override
   	            public void apply(int playerID) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException{
   	                u.apply(playerID);
   	                getActions().remove(this);
   	                if (!u.getChildren().isEmpty()) {
   	                	addUpgradeActions(u.getChildren());
   	                }
   	            }
   	        });
   	}
    }

	@Override
	public void getOccupied(Unit u) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * TESTING PURPOSE.
	 * Mimics player's click on action. Now invoke action after certain time. 
	 */
	@Override
	public void update(double elapsedTime) {
		super.update(elapsedTime);
		PRODUCE_TIME -= 1/elapsedTime;
		if(PRODUCE_TIME <= 0) { 
			try {
				findAction("Boost1").apply(1);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			PRODUCE_TIME = 90;
		}
	}
}
