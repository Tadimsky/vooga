package vooga.rts.gamedesign.upgrades;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import vooga.rts.gamedesign.sprite.gamesprites.Resource;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.player.Player;

/**
 * This class specifies an upgrade and is used to apply the upgrade to the 
 * object that is being upgraded.
 * 
 * @author Ryan Fishel
 * @author Kevin Oh
 * @author Francesco Agosti
 * @author Wenshun Liu 
 *
 */
public class UpgradeNode {

	private UpgradeTree myUpgradeTree;
	private Map<Resource, Integer> myCost;
    private String myUpgradeName;
    private int myUpgradeValue;
    private boolean myHasBeenUpgraded;
    private List<UpgradeNode> myChildren; //set to list for the Head.

    public UpgradeNode(){
        this(null, null, 0);
    }

    public UpgradeNode(UpgradeTree upgradeTree, String upgradeName, int upgradeValue){
        myUpgradeTree = upgradeTree;
    	myUpgradeName = upgradeName;
        myChildren = new ArrayList<UpgradeNode>();
        myHasBeenUpgraded = false;
        myUpgradeValue = upgradeValue;
    }
    
    /**
     * Applies the effect of this Upgrade type to the given interactive.
     * @param interactive
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     * @throws InstantiationException 
     * @throws NoSuchMethodException 
     * @throws SecurityException 
     */
    public void apply(int playerID)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException,
			SecurityException, NoSuchMethodException {
    	for (InteractiveEntity i: getUpgradeTree().getUsers().get(playerID)){
	    	apply(i);
	    	myUpgradeTree.activateNode(this);
	    }
	}
    
    public void apply(InteractiveEntity requester) 
    		throws IllegalArgumentException, IllegalAccessException,
    		InvocationTargetException, InstantiationException,
    		SecurityException, NoSuchMethodException {
    	return;
    }

    public boolean validUpdate(Player player){
    	//TODO check if play has enough resource to "buy the update"
    	return true;
    }

    public List<UpgradeNode> getChildren(){
        return myChildren;
    }
    
    public void addChild(UpgradeNode upgrade) {
		myChildren.add(upgrade);
	}

    public UpgradeTree getUpgradeTree() {
    	return myUpgradeTree;
    }
    
    public String getUpgradeName(){
        return myUpgradeName;
    }

    public boolean getHasBeenUpgraded(){
        return myHasBeenUpgraded;
    }

    public int getUpgradeValue(){
        return myUpgradeValue;
    }
}