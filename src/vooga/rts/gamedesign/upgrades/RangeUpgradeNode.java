package vooga.rts.gamedesign.upgrades;

import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;

/**
*
* This class represents the upgrade taken place on the InteractiveEntity's
* weapon range. This upgrade will only be available given the
* InteractiveEntity has CanAttack as its AttackStrategy.
* 
* @author Wenshun Liu
*
*/
public class RangeUpgradeNode extends UpgradeNode {
    public RangeUpgradeNode (UpgradeTree upgradeTree,
                             String upgradeType,
                             int upgradeValue,
                             int costedResourceAmount) {
        super(upgradeTree, upgradeType, upgradeValue, costedResourceAmount);
    }

    /**
     * Applies the upgrade to an InteractiveEntity using reflection helper,
     * which locates the property to be upgraded and applies the upgrade.
     */
    @Override
    public void upgrade (InteractiveEntity requester) {
    	getReflectionHelper().changeValue("myRange",
    			requester.getAttackStrategy().getCurrentWeapon(),
    			getUpgradeValue());
    	
    }
}
