package vooga.rts.gamedesign.strategy.upgradestrategy;

import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.gamedesign.upgrades.UpgradeTree;

public class CannotUpgrade implements UpgradeStrategy {

	public void createUpgradeActions(InteractiveEntity entity) {
		return;
	}

	public UpgradeTree getUpgradeTree() {
		return null;
	}

	public void setUpgradeTree(UpgradeTree upgradeTree, InteractiveEntity owner) {
		return;
	}

}
