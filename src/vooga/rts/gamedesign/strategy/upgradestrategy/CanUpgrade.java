package vooga.rts.gamedesign.strategy.upgradestrategy;

import vooga.rts.action.InteractiveAction;
import vooga.rts.commands.Command;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.gamedesign.strategy.Strategy;
import vooga.rts.gamedesign.upgrades.UpgradeNode;
import vooga.rts.gamedesign.upgrades.UpgradeTree;
import vooga.rts.util.Information;


public class CanUpgrade implements UpgradeStrategy {
    private UpgradeTree myUpgradeTree;

    public void setUpgradeTree (UpgradeTree upgradeTree, InteractiveEntity owner) {
        myUpgradeTree = upgradeTree;
        createUpgradeActions(owner);
    }

    public void createUpgradeActions (final InteractiveEntity entity) {
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
            entity.addInfo(commandName, new Information(commandName, "This upgrades " + upgrade.getUpgradeName(), "buttons/unload.gif",null));
            
        }
    }

    public UpgradeTree getUpgradeTree () {
        return myUpgradeTree;
    }

    public void affect (InteractiveEntity other) {
        UpgradeStrategy newUpgrade = new CanUpgrade();
        newUpgrade.setUpgradeTree(getUpgradeTree(), other);
        other.setUpgradeStrategy(newUpgrade);
    }

}
