package vooga.towerdefense.action.actionlist;

import java.util.ArrayList;
import java.util.List;

import vooga.towerdefense.action.Action;
import vooga.towerdefense.attributes.Attribute;
import vooga.towerdefense.attributes.AttributeConstantsEnum;
import vooga.towerdefense.gameElements.GameElement;

/**
 * 
 * Defines how something acts on death
 * 
 * @author Matthew Roy
 * @author Zhen Gou
 */
public class OnDeath extends Action {
	private GameElement myResponsibleElement;
	private List<Action> myActions;

	/**
	 * @param initiator
	 */
	public OnDeath(GameElement responsibleElement) {
		super();
		myResponsibleElement = responsibleElement;
		setEnabled(false);
	}

	/**
	 * Overrides from superclasses
	 * 
	 * @param elapseTime
	 */
	@Override
	public void executeAction(double elapseTime) {
		if (myResponsibleElement.getAttributeManager()
				.getAttribute(AttributeConstantsEnum.HEALTH.toString()).getValue() <= 0) {
			setEnabled(true);
		}

	}

	@Override
	public void update(double elapsedTime) {
		executeAction(elapsedTime);
		if (isEnabled())
			updateFollowUpActions(elapsedTime);
	}

}
