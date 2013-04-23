package vooga.rts.gamedesign.upgrades;

import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.gamedesign.strategy.attackstrategy.CanAttack;

/**
 * This class represents the type of upgrade taken place on Attack Strategy. In
 * the case of Attack Strategy the only upgrade will be from Cannot Attack to
 * Can Attack.
 * 
 * @author Wenshun Liu
 *
 */
public class AttackUpgradeNode extends UpgradeNode {

    public AttackUpgradeNode (UpgradeTree upgradeTree,
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
    	getReflectionHelper().setValue("myAttackStrategy", requester,
    			new CanAttack(requester.getWorldLocation(),
    					requester.getPlayerID()));
    }

}
