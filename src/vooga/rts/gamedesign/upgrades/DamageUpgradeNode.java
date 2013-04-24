package vooga.rts.gamedesign.upgrades;

import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;

/**
 *
 * This class represents the upgrade taken place on the InteractiveEntity's
 * projectile damage. This upgrade will only be available given the
 * InteractiveEntity has CanAttack as its AttackStrategy.
 * 
 * @author Wenshun Liu
 *
 */
public class DamageUpgradeNode extends UpgradeNode {

    public DamageUpgradeNode (UpgradeTree upgradeTree,
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
    	if(requester.getAttackStrategy().hasWeapon()){
        	getReflectionHelper().changeValue("myDamage",
        			requester.getAttackStrategy().getCurrentWeapon().getProjectile(),
        			getUpgradeValue());
    	}

    }
}
