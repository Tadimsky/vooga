package vooga.rts.gamedesign.strategy.upgradestrategy;

import vooga.rts.action.InteractiveAction;
import vooga.rts.commands.Command;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.gamedesign.upgrades.UpgradeNode;
import vooga.rts.gamedesign.upgrades.UpgradeTree;

public class CanUpgrade implements UpgradeStrategy{
	private UpgradeTree myUpgradeTree;

	public void setUpgradeTree(UpgradeTree upgradeTree, InteractiveEntity owner) {
		myUpgradeTree = upgradeTree;
		createUpgradeActions(owner);
	}
	
	public void createUpgradeActions(final InteractiveEntity entity) {
		for (final UpgradeNode upgrade: myUpgradeTree.getCurrentUpgrades()) {
			entity.addAction(upgrade.getUpgradeName(), new InteractiveAction(entity) {
				@Override
	            public void update (Command command) {
	            }

	            @Override
	            public void apply () {
	                upgrade.apply(entity);
	            }
			});
		}
	}
	
	public UpgradeTree getUpgradeTree(){
		return myUpgradeTree;
	}
}
