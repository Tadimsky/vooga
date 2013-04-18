package vooga.rts.gamedesign.upgrades;

import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;

public class HealthUpgradeNode extends UpgradeNode {
	
	public HealthUpgradeNode(UpgradeTree upgradeTree, String upgradeType, int upgradeValue, int costedResourceAmount){
		super(upgradeTree, upgradeType, upgradeValue, costedResourceAmount);
	}
	
	/**
	 * Applies the upgrade to an individual InteractiveEntity by calling
	 * related method.
	 */
	@Override
	public void apply(InteractiveEntity requester) {
		requester.addMaxHealth(getUpgradeValue());
	}

}
