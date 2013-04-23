package vooga.rts.gamedesign.upgrades;

import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;

/**
*
* This class represents the upgrade taken place on the InteractiveEntity's
* max health.
* 
* @author Wenshun Liu
*
*/
public class HealthUpgradeNode extends UpgradeNode {

    public HealthUpgradeNode (UpgradeTree upgradeTree,
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
    	getReflectionHelper().changeValue("myMaxHealth", requester,
    			getUpgradeValue());
    }

}
