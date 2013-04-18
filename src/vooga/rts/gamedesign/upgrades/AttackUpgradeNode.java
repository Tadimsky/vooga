package vooga.rts.gamedesign.upgrades;

import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.gamedesign.strategy.attackstrategy.CanAttack;

public class AttackUpgradeNode extends UpgradeNode {
	
	/**
	 * 
	 * @param upgradeTree
	 * @param id
	 * @param upgradeType
	 * @param upgradeValue always set to 0.
	 * 
	 * @author Wenshun Liu
	 */
	//TODO: now upgradeValue is only Integer. See if need to be generic based on the need.
	public AttackUpgradeNode(UpgradeTree upgradeTree, String upgradeType, int upgradeValue,int costedResourceAmount){
		super(upgradeTree, upgradeType, upgradeValue, costedResourceAmount);
	}
	
	/**
	 * Applies the upgrade to an individual InteractiveEntity by calling
	 * related method.
	 */
	@Override
	public void apply(InteractiveEntity requester) {
		requester.setAttackStrategy(new CanAttack(requester.getWorldLocation(), requester.getPlayerID()));
	}

}
