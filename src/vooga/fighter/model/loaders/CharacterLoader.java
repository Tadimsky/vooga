package vooga.fighter.model.loaders;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import vooga.fighter.model.objects.AttackObject;
import vooga.fighter.model.objects.CharacterObject;

/**
 * Loads data associated with a character object to be passed to CharacterObject.
 * 
 * @author David Le, alanni
 *
 */
public class CharacterLoader extends ObjectLoader {
	
	private static final String PATH_TAG = "CharacterPath";
	
	private CharacterObject myChar;

	/**
	 * Constructs the character loader with the name to be loaded and the character which the
	 * loader will modify.
	 * @param charName
	 * @param character
	 */
	public CharacterLoader (String charName, CharacterObject character) {
		super(PATH_TAG);
		myChar = character;
		load(charName);
	}

	/**
	 * Loads the character associated with the id
	 * @param charId is the id of the character to be loaded
	 */
	protected void load(String charName) {
		Document doc = getDocument();
		NodeList charNodes = doc.getElementsByTagName(getResourceBundle().getString("Character"));

		for (int i = 0; i < charNodes.getLength(); i++) {
			Element node = (Element) charNodes.item(i);
			String name = getAttributeValue(node, getResourceBundle().getString("CharacterName"));
			if (charName.equals(name)) {
				addProperties(node, myChar);
				NodeList stateNodes = node.getElementsByTagName(getResourceBundle().getString("State"));
				addStates(stateNodes, myChar);
				myChar.defineDefaultState(getAttributeValue(node, getResourceBundle().getString("Default")));
				NodeList attackNodes = node.getElementsByTagName(getResourceBundle().getString("Attack"));
				addAttacks(attackNodes);
			}
		}
	}
	
	
	/**
	 * Loads and adds attacks to the list of attacks that a character can perform
	 *
	 * @param attackNodes
	 */
	private void addAttacks(NodeList attackNodes) {
		for (int i = 0; i < attackNodes.getLength(); i++) {
			String attackName = getAttributeValue(attackNodes.item(i), getResourceBundle().getString("AttackName"));
			AttackObject newAttack = new AttackObject(attackName);
			myChar.addAttack(attackName, newAttack);
		}
	}
}