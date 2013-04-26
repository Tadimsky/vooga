package vooga.towerdefense.model;

import java.util.HashSet;

import vooga.towerdefense.attributes.Attribute;
import vooga.towerdefense.attributes.AttributeConstants;
import vooga.towerdefense.attributes.AttributeManager;

public class Player {

	private AttributeManager myAttributeManager;
	
	public Player() {
		myAttributeManager = new AttributeManager();
		myAttributeManager.addAttribute(new Attribute(AttributeConstants.HEALTH, 100D));
		myAttributeManager.addAttribute(new Attribute(AttributeConstants.SCORE, 0D));
		myAttributeManager.addAttribute(new Attribute(AttributeConstants.MONEY, 1000D));
	}

	public Player(HashSet<Attribute> attributes) {
		myAttributeManager = new AttributeManager(attributes);
	}

	public AttributeManager getAttributeManager() {
		return myAttributeManager;
	}
	
	public void addAttribute(Attribute newAttribute){
		myAttributeManager.addAttribute(newAttribute);
	}
}
