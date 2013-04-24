package vooga.rts.gamedesign.strategy.upgradestrategy;

import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.gamedesign.strategy.Strategy;
import vooga.rts.gamedesign.upgrades.UpgradeTree;

public interface UpgradeStrategy extends Strategy{
	public void createUpgradeActions(final InteractiveEntity entity);
	
	public UpgradeTree getUpgradeTree();
	
	public void setUpgradeTree(UpgradeTree upgradeTree, InteractiveEntity owner);
}
