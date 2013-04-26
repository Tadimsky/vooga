package vooga.towerdefense.factories.actionfactories;

import vooga.towerdefense.action.Action;
import vooga.towerdefense.action.actionlist.Move;
import vooga.towerdefense.gameElements.GameElement;

/**
 * Factory for creating move action. User needs to specify which attribute to be taken
 * as a reference for move speed in the constructor. 
 * 
 * @author Zhen Gou
 * 
 */

public class MoveFactory extends ActionFactory {
	
	private String myMoveSpeedReference;
	private String myDirectionReference;

	/**
	 * constructor 
	 * 
	 * @param moveSpeedReference
	 * @param directionReference
	 */
	public MoveFactory(String moveSpeedReference, String directionReference) {
		myMoveSpeedReference = moveSpeedReference;
		myDirectionReference = directionReference;
	}
	
	/**
	 * create a move action for the game element given
	 * @param elementToMove
	 * @return
	 */
	@Override
	protected Action buildAction(GameElement elementToMove) {
		Action action = new Move(elementToMove.getCenter(), elementToMove
				.getAttributeManager().getAttribute(
						myMoveSpeedReference), elementToMove
				.getAttributeManager().getAttribute(
						myDirectionReference));
		return action;
	}
	
}
