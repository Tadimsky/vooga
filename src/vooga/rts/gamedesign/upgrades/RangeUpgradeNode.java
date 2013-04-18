package vooga.rts.gamedesign.upgrades;

import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;

public class RangeUpgradeNode extends UpgradeNode {
	public RangeUpgradeNode(UpgradeTree upgradeTree, String upgradeType, int upgradeValue, int costedResourceAmount){
		super(upgradeTree, upgradeType, upgradeValue, costedResourceAmount);
	}
	
	@Override
	public void apply(InteractiveEntity requester) {
		requester.getAttackStrategy().getCurrentWeapon().addRange(getUpgradeValue());
	}
}
