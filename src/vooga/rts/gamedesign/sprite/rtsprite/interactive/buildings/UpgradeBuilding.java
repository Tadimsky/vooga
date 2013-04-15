package vooga.rts.gamedesign.sprite.rtsprite.interactive.buildings;

import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import vooga.rts.gamedesign.action.Action;
import vooga.rts.gamedesign.sprite.InteractiveEntity;
import vooga.rts.gamedesign.sprite.rtsprite.interactive.units.Unit;
import vooga.rts.gamedesign.upgrades.UpgradeNode;
import vooga.rts.gamedesign.upgrades.UpgradeTree;
import vooga.rts.util.Location;
import vooga.rts.util.Location3D;
import vooga.rts.util.Pixmap;
import vooga.rts.util.Sound;

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
        List<UpgradeNode> currentUpgrades = upgradeTree.getCurrentUpgrades();
    	for (final UpgradeNode u: currentUpgrades) {
    		 getActions().add(new Action(u.getUpgradeType(), null, "An upgrade action for soldier"){
    	            @Override
    	            public void apply(int playerID) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException{
    	                u.apply(playerID);
    	                for (InteractiveEntity i: u.getUpgradeTree().getUsers().get(1)) {
    	                	System.out.println("Health: " + i.getMaxHealth());
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
