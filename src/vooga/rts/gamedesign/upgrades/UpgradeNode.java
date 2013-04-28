package vooga.rts.gamedesign.upgrades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.player.Player;
import vooga.rts.util.ReflectionHelper;


/**
 * This class is the superclass of a single upgrade. It is used to build the
 * upgrade tree and apply the upgrade to the corresponding object.
 * 
 * @author Wenshun Liu
 * 
 */
public class UpgradeNode {

    private UpgradeTree myUpgradeTree;
    private Map<String, Integer> myCost;
    private String myUpgradeName;
    private int myUpgradeValue;
    private boolean myHasBeenUpgraded;
    private List<UpgradeNode> myChildren;

    public UpgradeNode () {
        this(null, null, 0, 0);
    }

    public UpgradeNode (UpgradeTree upgradeTree,
                        String upgradeName,
                        int upgradeValue,
                        int costedResourceAmount) {
        myCost = new HashMap<String, Integer>();
        myUpgradeTree = upgradeTree;
        myUpgradeName = upgradeName;
        myChildren = new ArrayList<UpgradeNode>();
        myHasBeenUpgraded = false;
        myUpgradeValue = upgradeValue;
        myCost = new HashMap<String, Integer>();
        myCost.put("resource", costedResourceAmount);
    }
    
    /**
     * Applies the upgrade to the requesting object and updates the upgrade
     * Actions of the requesting object
     * 
     * @param requester the InteractiveEntity that will be receiving the
     * upgrade.
     */
    public void apply (InteractiveEntity requester) {
    	myHasBeenUpgraded = true;
    	upgrade(requester);
    	myUpgradeTree.activateNode(this, requester);
    	return;
    }

    /**
     * Applies the upgrade to the requesting object.
     * 
     * @param requester the InteractiveEntity that will be receiving the
     * upgrade
     */
    public void upgrade(InteractiveEntity requester) {
    	return;
    }
    
    /**
     * Checks if the object requesting the upgrade has enough resource to
     * "buy" it.
     * 
     * @param requester the InteractiveEntity that wish to be upgraded
     * @return if the requester has enough resource to receive the upgrade
     */
    public boolean validUpdate (InteractiveEntity requester) {
        /*
         * for (String resourceType : myCost.keySet()) {
         * if (player.getIndividualResourceManager().getAmount(resourceType) < myCost
         * .get(resourceType)) {
         * return false;
         * }
         * }
         */
        return true;
    }

    /**
     * Returns the children of the upgrade node.
     * 
     * @return the children of the upgrade node.
     */
    public List<UpgradeNode> getChildren () {
        return myChildren;
    }

    /**
     * Adds a child to the upgrade node
     * 
     * @param childNode the child to be added to the upgrade node
     */
    public void addChild (UpgradeNode childNode) {
        myChildren.add(childNode);
    }

    /**
     * Returns the upgrade tree this node belongs to
     * 
     * @return the upgrade tree this upgrade node belongs to
     */
    public UpgradeTree getUpgradeTree () {
        return myUpgradeTree;
    }

    /**
     * Returns the name of this upgrade
     * 
     * @return the name of this upgrade
     */
    public String getUpgradeName () {
        return myUpgradeName;
    }

    /**
     * Returns whether this upgrade has been activated.
     * 
     * @return whether this upgrade has been activated
     */
    public boolean getHasBeenUpgraded () {
        return myHasBeenUpgraded;
    }

    /**
     * Returns the integer value that will be applied when upgrade is activated
     * 
     * @return the integer value that will be applied
     */
    public int getUpgradeValue () {
        return myUpgradeValue;
    }

    /**
     * Returns the cost it takes to activate this upgrade.
     * 
     * @return the cost it takes to activate this upgrade.
     */
    public Map<String, Integer> getCost () {
        return myCost;
    }
    
    /**
     * Returns the type of upgrade.
     */
    public String getType() {
    	return "";
    }
}
