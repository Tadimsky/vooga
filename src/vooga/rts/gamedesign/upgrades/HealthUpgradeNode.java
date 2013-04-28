package vooga.rts.gamedesign.upgrades;

import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.util.ReflectionHelper;

/**
*
* This class represents the upgrade taken place on the InteractiveEntity's
* max health.
* 
* @author Wenshun Liu
*
*/
public class HealthUpgradeNode extends UpgradeNode {
	private static final String DEFAULT_TYPE = "health";
	
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
    	ReflectionHelper.changeValue("myMaxHealth", requester,
    			getUpgradeValue());
    }

    @Override
    public String getType() {
    	return DEFAULT_TYPE;
    }
}
