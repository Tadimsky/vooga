package vooga.rts.gamedesign.strategy.upgradestrategy;

import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.gamedesign.strategy.Strategy;
import vooga.rts.gamedesign.upgrades.UpgradeTree;

/**
 * This class implements UpgradeStrategy and is used as an instance in 
 * InteractiveEntity for objects that are not able to upgrade.  
 * 
 * @author Wenshun Liu 
 *
 */
public class CannotUpgrade implements UpgradeStrategy {
	public UpgradeTree getUpgradeTree() {
		return null;
	}
	
	public void setUpgradeTree(UpgradeTree upgradeTree, InteractiveEntity owner) {
		return;
	}

	public void affect(InteractiveEntity entity) {
		entity.setUpgradeStrategy(this);
	}

}
