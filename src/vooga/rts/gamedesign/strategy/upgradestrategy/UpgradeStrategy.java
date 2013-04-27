package vooga.rts.gamedesign.strategy.upgradestrategy;

import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.gamedesign.strategy.Strategy;
import vooga.rts.gamedesign.upgrades.UpgradeTree;
/**
 * This interface is implemented by the classes CanUpgrade and
 * CannotUpgrade that are then used as instance variables in the classes
 * that could possibly receive upgrades.
 * 
 * @author Wenshun Liu
 * 
 */
public interface UpgradeStrategy extends Strategy {
	public UpgradeTree getUpgradeTree();
	
	public void setUpgradeTree(UpgradeTree upgradeTree, InteractiveEntity owner);
}
