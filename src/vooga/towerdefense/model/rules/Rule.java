package vooga.towerdefense.model.rules;

import vooga.towerdefense.model.GameModel;

/**
 * A simple class that executes a certain behavior based on a condition.  It is used to control game flow in the gameModel.
 * 
 * @author JLongley
 *
 */
public abstract class Rule {

	GameModel myModel;
	
	public Rule(GameModel model) {
		myModel = model;
	}
	
	/**
	 * Based on how the rule is defined, a rule checks a condition and then if it is true, performs an action.
	 */
	public void apply() {
		if (condition())
			execute();
	}
	
	/**
	 * Whether the rule's condition is met.
	 * @return
	 */
	protected abstract boolean condition();
	
	/**
	 * executes the rule's behavior.
	 */
	protected abstract void execute();
	
	
	
}
