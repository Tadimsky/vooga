package vooga.rts.gamedesign.strategy.upgradestrategy;

import vooga.rts.action.InteractiveAction;
import vooga.rts.commands.Command;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.gamedesign.strategy.Strategy;
import vooga.rts.gamedesign.upgrades.UpgradeNode;
import vooga.rts.gamedesign.upgrades.UpgradeTree;
import vooga.rts.util.Information;

/**
 * This class implements UpgradeStrategy and is used as an instance in
 * InteractiveEntity for objects that are able to upgrade. It also stores
 * the upgrade tree tied to the InteractiveEntity that details what the
 * upgrades are and their relationship to each other
 * 
 * @author Wenshun Liu
 * 
 */
public class CanUpgrade implements UpgradeStrategy {
    private UpgradeTree myUpgradeTree;

    /**
     * Sets the upgrade tree that is tied to the InteractiveEntity passed in,
     * and creates upgrade related actions for that InteractiveEntity.
     * 
     * @param upgradeTree the UpgradeTree that will be set to the
     * InteractiveEntity
     * @param owner the InteractiveEntity that the UpgradeTree will belong to
     */
    public void setUpgradeTree (UpgradeTree upgradeTree, InteractiveEntity owner) {
        myUpgradeTree = upgradeTree;
        createUpgradeActions(owner);
    }

    /**
     * Creates the upgrade related actions to the InteractiveEntity that is
     * passed in.
     * 
     * @param entity the InteractiveEntity that will receive that actions
     */
    private void createUpgradeActions (final InteractiveEntity entity) {
        for (final UpgradeNode upgrade : myUpgradeTree.getCurrentUpgrades()) {
        	String commandName = "upgrade " + upgrade.getUpgradeName();
            entity.addAction(commandName, new InteractiveAction(entity) {
                @Override
                public void update (Command command) {
                }

                @Override
                public void apply () {
                    upgrade.apply(entity);
                }
            });
        	entity.addInfo(commandName, new Information(commandName,
                    "This is an upgrade that will be working on " + upgrade.getType(),
                    "buttons/marine.PNG", null));
        }
    }

    /**
     * Returns the UpgradeTree tied to this CanUpgrade strategy.
     * 
     * @return the UpgradeTree of this CanUpgrade strategy.
     */
    public UpgradeTree getUpgradeTree () {
        return myUpgradeTree;
    }

    /**
     * Applies this CanUpgrade strategy to another InteractiveEntity that is
     * passed in, by setting it as the InteractiveEntity's strategy and
     * recreating the actions.
     * 
     * @param other the InteractiveEntity that will receive the strategy.
     */
    public void affect (InteractiveEntity other) {
        UpgradeStrategy newUpgrade = new CanUpgrade();
        newUpgrade.setUpgradeTree(getUpgradeTree(), other);
        other.setUpgradeStrategy(newUpgrade);
    }

}
