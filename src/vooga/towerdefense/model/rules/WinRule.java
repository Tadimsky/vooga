package vooga.towerdefense.model.rules;

import vooga.towerdefense.model.GameModel;

public class WinRule extends Rule {

	public WinRule(GameModel model) {
		super(model);
	}

	@Override
	protected boolean condition() {
		return (myModel.getPlayer().getScore() > 1000);
	}

	@Override
	protected void execute() {
		myModel.win();
	}

}
