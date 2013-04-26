package vooga.towerdefense.action.tobetested;

import java.util.List;

public abstract class UpgradeAction {
	List<UpgradeAction> myUpgradeHierarchy;
	
	public UpgradeAction(List<UpgradeAction> upgradeHierarchy){
		myUpgradeHierarchy = upgradeHierarchy;
	}

	public abstract void checkUpgradeCondition();
}
