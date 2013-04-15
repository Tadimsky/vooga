package vooga.rts.gamedesign.upgrades;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import vooga.rts.gamedesign.Weapon;
import vooga.rts.gamedesign.sprite.InteractiveEntity;

public class DamageUpgradeNode extends UpgradeNode {
	//public static final String DAMAGE_UPGRADE_METHOD_NAME = "addDamage";
	//public static final Class[] DAMAGE_UPGRADE_METHOD_PARAM = new Class[] {int.class};

	public DamageUpgradeNode(UpgradeTree upgradeTree, int id, String upgradeType, String upgradeObject, int upgradeValue){
		super(upgradeTree, id, upgradeType, upgradeObject, upgradeValue);
	}
	
	/**
	 * Applies the damage upgrade by the method that updates damage.
	 */
	@Override
	public void apply(List<InteractiveEntity> requester)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException,
			SecurityException, NoSuchMethodException {
		for (InteractiveEntity i: requester){
			apply(i);
			//Class thisClass = Weapon.class;
			//Class[] params = DAMAGE_UPGRADE_METHOD_PARAM;
			//Method thisMethod = thisClass.getDeclaredMethod(DAMAGE_UPGRADE_METHOD_NAME, params);
			//thisMethod.invoke(i.getAttackStrategy().getCurrentWeapon(), getUpgradeValue());
		}
	}
	
	@Override
	public void apply(InteractiveEntity requester) { //TODO: figure out which one should actually be called under Action
		if (requester.getAttackStrategy().getCanAttack() && !requester.getAttackStrategy().getWeapons().isEmpty()){
			requester.getAttackStrategy().getCurrentWeapon().addDamage(getUpgradeValue());
		}
	}
}
