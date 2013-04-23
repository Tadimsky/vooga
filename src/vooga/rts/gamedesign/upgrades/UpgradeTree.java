package vooga.rts.gamedesign.upgrades;

import java.util.ArrayList;
import java.util.List;

import vooga.rts.action.InteractiveAction;
import vooga.rts.commands.Command;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;


/**
 * This class is the tree of upgrades that any object that can be upgraded will
 * hold. For example, every unit will have an upgrade tree. Upgrades are being
 * stored in a tree like structure because in some cases upgrades are specified
 * in certain orders, so that buying a certain tier 1 upgrade does not 
 * necessarily mean that all tier 2 upgrades are now available. The root of the
 * tree is just a place holder. It would not contain an upgrade. The children
 * of the root represent the different "genres" of upgrades. The actually
 * upgrades start below the "genres".
 * 
 * @author Ryan Fishel
 * @author Kevin Oh
 * @author Francesco Agosti
 * @author Wenshun Liu
 * 
 */
public class UpgradeTree {
    private UpgradeNode myHead;
    private List<UpgradeNode> myCurrentUpgrades;

    public UpgradeTree () {
        myHead = new UpgradeNode();
        myCurrentUpgrades = new ArrayList<UpgradeNode>();
    }

    /**
     * Updates the list of current and next upgrades based on the given
     * UpgradeNode, which is newly activated. Also updates the Actions of
     * the entity that owns this upgrade tree.
     * 
     * @param activateNode the UpgradeNode that is newly activated
     * @param entity the entity that the activation will be applied on
     */
    public void activateNode (UpgradeNode activateNode, final InteractiveEntity entity) {
        myCurrentUpgrades.remove(activateNode);
        entity.removeAction(activateNode.getUpgradeName());
        if (!activateNode.getChildren().isEmpty()) {
            for (final UpgradeNode newUpgrade : activateNode.getChildren()) {
                myCurrentUpgrades.add(newUpgrade);
                entity.addAction(newUpgrade.getUpgradeName(), new InteractiveAction(entity) {
    				@Override
    	            public void update (Command command) {
    	            }

    	            @Override
    	            public void apply () {
    	                newUpgrade.apply(entity);
    	            }
    			});
            }
        }
    }

    /**
     * Updates the list of current and next upgrades. Only to be called when
     * upgradeTree is first created.
     */
    public void updateTreeStatus () {
        for (UpgradeNode u : myHead.getChildren()) {
            UpgradeNode current = u;
            while (!u.getChildren().isEmpty() && u.getChildren().get(0).getHasBeenUpgraded()) {
                current = current.getChildren().get(0);
            }
            if (!current.getChildren().isEmpty()) {
                current = current.getChildren().get(0);
                myCurrentUpgrades.add(current);
            }
        }
    }
    
    /**
     * Adds a new Branch to the head of the upgrade tree. Indicating that a
     * new type of upgrade has been added.
     * 
     * @param branchName the name of the new branch/upgrade type
     */
    public void addBranch (String branchName) {
        UpgradeNode branch = new UpgradeNode(this, branchName, 0, 0);
        myHead.addChild(branch);
    }

    /**
     * Finds and returns the upgrade node with the given name.
     * 
     * @param upgradeName the name of the upgrade node to be found
     * @return the UpgradeNode with the given name
     */
    public UpgradeNode findNode (String upgradeName) {
        UpgradeNode current = myHead;
        while (!current.getChildren().isEmpty()) {
            for (UpgradeNode u : current.getChildren()) {
                if (u.getUpgradeName().equals(upgradeName)) {
                    return u;
                }
                else {
                    current = u;
                }
            }
        }
        return null;
    }

    /**
     * Returns the list of upgrade node that is current available to be
     * activated.
     * 
     * @return List<UpgradeNode> The list of upgrade node that is current
     * available for activation
     */
    public List<UpgradeNode> getCurrentUpgrades () {
        return myCurrentUpgrades;
    }

    /**
     * Returns the head of the upgrade tree.
     * 
     * @return the head of the upgrade tree
     */
    public UpgradeNode getHead () {
        return myHead;
    }
}
