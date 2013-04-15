package vooga.rts.gamedesign.upgrades;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import vooga.rts.gamedesign.sprite.InteractiveEntity;
import vooga.rts.gamedesign.sprite.rtsprite.Resource;
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
    private String myUpgradeType;
    private String myUpgradeProperty;
    private int myUpgradeValue;
    private boolean myHasBeenUpgraded;
    private List<UpgradeNode> myChildren; //set to list for the Head.
    private int myID;

    public UpgradeNode(){
        this(null, 0, null, null, 0);
    }

    public UpgradeNode(UpgradeTree upgradeTree, int id, String upgradeType, String upgradeObject, int upgradeValue){
        myUpgradeTree = upgradeTree;
    	myID = id;
    	myUpgradeType = upgradeType;
        myChildren = new ArrayList<UpgradeNode>();
        myHasBeenUpgraded = false;
        myUpgradeProperty = upgradeObject;
        myUpgradeValue = upgradeValue;
    }

    //TODO: can refactor some subclass methods to here!
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
    	return;
	}
    
    public void apply(InteractiveEntity requester) //TODO: figure out which one should actually be called under Action
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
    
    public UpgradeNode addChild(UpgradeNode upgrade) {
		myChildren.add(upgrade);
		return upgrade;
	}

    public UpgradeTree getUpgradeTree() {
    	return myUpgradeTree;
    }
    
    public String getUpgradeType(){
        return myUpgradeType;
    }

    public boolean getHasBeenUpgraded(){
        return myHasBeenUpgraded;
    }

    public String getUpgradeObject(){
        return myUpgradeProperty;
    }

    public int getUpgradeValue(){
        return myUpgradeValue;
    }
    
    public int getID(){
    	return myID;
    }
}