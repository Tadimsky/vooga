package vooga.towerdefense.action.actionlist;

import vooga.towerdefense.gameElements.GameElement;
import vooga.towerdefense.action.TargetedAction;
import vooga.towerdefense.attributes.Attribute;


/**
 * 
 * Finds the attribute in the target object and sets it to the attribute given
 * If the target doesn't have the attribute, it places the new attribute in the target
 * 
 * e.g. replace a tower's missiles with pebbles
 * 
 * @author Matthew Roy
 * 
 */
public class ModifyFactoryAttribute extends TargetedAction {

	Attribute myTargetAttribute;

	/**
	 * constructor
	 * @param toModify
	 */
    public ModifyFactoryAttribute (Attribute toModify) {
        myTargetAttribute = toModify;
    }

    @Override
    public void executeAction (double elapsedTime) {
        for (GameElement target : getTargets()) {
            Attribute targetsAttribute =
                    target.getAttributeManager().getAttribute(myTargetAttribute.getName());
            if (targetsAttribute != null) {
                targetsAttribute.setValue(myTargetAttribute.getValue());
            }
            else {
                target.getAttributeManager().addAttribute(myTargetAttribute);
            }
        }
    }

    
}
