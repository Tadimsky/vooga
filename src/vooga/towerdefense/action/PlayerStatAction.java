package vooga.towerdefense.action;

import vooga.towerdefense.attributes.Attribute;
import vooga.towerdefense.attributes.AttributeConstants;
import vooga.towerdefense.attributes.AttributeConstantsEnum;
import vooga.towerdefense.model.Player;

/**
 * action for changing player's score/life/money or any other stat that Player has in a game
 * If the Player class was changed, this needs to be changed.
 * @author Zhen Gou
 *
 */
public class PlayerStatAction extends Action{
	private Player myPlayer;
	private Attribute myScoreChangeReference;
	private Attribute myLifeChangeReference;
	private Attribute myMoneyChangeReference;
	
/**
 * 
 * @param player
 * @param scoreChange
 * @param lifeChange
 * @param moneyChange
 */
	public PlayerStatAction(Player player,Attribute scoreChange, Attribute lifeChange, Attribute moneyChange){
		myPlayer=player;
		myScoreChangeReference=scoreChange;
		myLifeChangeReference=lifeChange;
		myMoneyChangeReference=moneyChange;
	
	}

	@Override
	public void executeAction(double elapsedTime) {
		myPlayer.getAttributeManager().getAttribute(AttributeConstants.SCORE).modifyValue(myScoreChangeReference.getValue());
		myPlayer.getAttributeManager().getAttribute(AttributeConstants.HEALTH).modifyValue(myLifeChangeReference.getValue());
		myPlayer.getAttributeManager().getAttribute(AttributeConstants.MONEY).modifyValue(myMoneyChangeReference.getValue());
	
		
	}
	

}
