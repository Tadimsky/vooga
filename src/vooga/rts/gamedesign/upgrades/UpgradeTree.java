package vooga.rts.gamedesign.upgrades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vooga.rts.gamedesign.sprite.InteractiveEntity;


/**
 * This class is the tree of upgrades that any object that can be upgraded will
 * hold. For example, every unit will have an upgrade tree (and each unit of
 * the same type will have the same upgrade tree). Upgrades are being stored
 * in a tree like structure because in some cases upgrades are specified in
 * certain branches so that buying a certain tier 1 upgrade does not
 * necessarily mean that all tier 2 upgrades are now available. For example
 * buying the tier 1 armor upgrade does not mean that the tier 2 weapon upgrade
 * is available (only the tier 2 armor upgrade would be available). If there
 * are multiple types of tier 1 upgrades, the root of the tree would not
 * contain an upgrade.
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
    private List<UpgradeNode> myNextUpgrades;
    private Map<Integer, List<InteractiveEntity>> myUsers;
    //private List<InteractiveEntity> myUsers;

    public UpgradeTree () {
        myHead = new UpgradeNode();
        myCurrentUpgrades = new ArrayList<UpgradeNode>();
        myNextUpgrades = new ArrayList<UpgradeNode>();
        myUsers = new HashMap<Integer, List<InteractiveEntity>>();
        //myUsers = new ArrayList<InteractiveEntity>();
    }

    /**
     * Updates the list of current and next upgrades based on the given
     * UpgradeNode, which is newly activated
     * 
     * @param u
     */
    public void activateNode (UpgradeNode u) {
        myCurrentUpgrades.add(u);
        if (!u.getChildren().isEmpty()) {
        	 UpgradeNode nextUpgrade = u.getChildren().get(0);
             myNextUpgrades.add(nextUpgrade);
        }
    }

    /**
     * Updates the list of current and next upgrades. Only to be called when
     * upgradeTree is first created.
     */
    public void updateTreeStatus () {
        for (UpgradeNode u: myHead.getChildren()) {
        	UpgradeNode current = u;
        	while(!u.getChildren().isEmpty() && u.getChildren().get(0).getHasBeenUpgraded()) {
        		current = current.getChildren().get(0);
        	}
        	if (!current.getChildren().isEmpty()) {
        		current = current.getChildren().get(0);
        		myCurrentUpgrades.add(current);
        		if (!current.getChildren().isEmpty()) {
        			myNextUpgrades.add(current.getChildren().get(0));
        		}
        	}
        }
    }

    public UpgradeNode addBranch (int ID, String branchName) {
        UpgradeNode branch = new UpgradeNode(this, ID, branchName, null, 0);
        myHead.addChild(branch);
        return branch;
    }

    /**
     * Finds the most advanced upgrade has been made in the giving upgrade type.
     * 
     * @param upgradeType
     * @return
     */
    public UpgradeNode findCurrent (String upgradeType) {
        UpgradeNode current = new UpgradeNode();
        for (UpgradeNode n : myHead.getChildren()) {
            if (n.getUpgradeType().equals(upgradeType)) {
                current = n;
            }
        }

        while (current != null) {
            if (current.getHasBeenUpgraded()) {
                current = current.getChildren().get(0);
            }
            else {
                break;
            }
        }
        return current;
    }

    public Map<Integer, List<InteractiveEntity>> getUsers() {
    	return myUsers;
    }
    
    public List<InteractiveEntity> getPlayerUsers(int playerID) {
        return myUsers.get(playerID);
    }
    
    public void addUser(InteractiveEntity i, int playerID) {
    	if (myUsers.get(playerID) == null) {
    		List<InteractiveEntity> entityGroup = new ArrayList<InteractiveEntity>();
    		entityGroup.add(i);
    		myUsers.put(playerID, entityGroup);
    	} else {
    		List<InteractiveEntity> entityGroup = myUsers.get(playerID);
    		entityGroup.add(i);
    		myUsers.put(playerID, entityGroup);
    	}
    	
    	myUsers.get(playerID).add(i);
    }

    public List<UpgradeNode> getCurrentUpgrades() {
        return myCurrentUpgrades;
    }
    
    public List<UpgradeNode> getNextUpgrades() {
    	return myNextUpgrades;
    }
    
    public UpgradeNode getHead() {
    	return myHead;
    }
}
