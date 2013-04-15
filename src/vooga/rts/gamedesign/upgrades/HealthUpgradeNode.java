package vooga.rts.gamedesign.upgrades;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import vooga.rts.gamedesign.sprite.GameEntity;
import vooga.rts.gamedesign.sprite.InteractiveEntity;

public class HealthUpgradeNode extends UpgradeNode {
	//public static final String HEALTH_UPGRADE_METHOD_NAME = "addMaxHealth";
	//public static final Class[] HEALTH_UPGRADE_METHOD_PARAM = new Class[] {int.class};
	
	public HealthUpgradeNode(UpgradeTree upgradeTree, int id, String upgradeType, String upgradeObject, int upgradeValue){
		super(upgradeTree, id, upgradeType, upgradeObject, upgradeValue);
	}
	
	
	/**
	 * Applies the health upgrade by the method that updates health.
	 */
	@Override
	public void apply(List<InteractiveEntity> requester)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException,
			SecurityException, NoSuchMethodException {
        for (InteractiveEntity i: requester){
        	apply(i);
        	//Class thisClass = GameEntity.class;
        	//System.out.println(thisClass);
            //Class[] params = HEALTH_UPGRADE_METHOD_PARAM;
            //Method thisMethod = thisClass.getDeclaredMethod(HEALTH_UPGRADE_METHOD_NAME, params);
            //thisMethod.invoke(i, getUpgradeValue());
        }
	}
	
	@Override
	public void apply(InteractiveEntity requester) { //TODO: figure out which one should actually be called under Action
		requester.addMaxHealth(getUpgradeValue());
	}
}
