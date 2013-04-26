package vooga.towerdefense.model.rules;

import java.util.ArrayList;

import vooga.towerdefense.action.Action;
import vooga.towerdefense.model.GameModel;

/**
 * Makes the game go to the next level if the current one is completed.
 * @author JLongley
 *
 */
public class NextLevelRule extends Rule {

	public NextLevelRule(GameModel model) {
		super(model);
	}

	/**
	 * If the levels actions are all done, e.g., the wave is finished.
	 */
	@Override
	protected boolean condition() {
		ArrayList<Action> actions = (ArrayList<Action>) myModel.getActiveLevel().getActions();
		for(Action action : actions)
			if (action.isEnabled())
				return false;
		return true;
		
	}

	@Override
	protected void execute() {
		myModel.startNextLevel();
	}

}
