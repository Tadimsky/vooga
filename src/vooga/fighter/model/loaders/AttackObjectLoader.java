package vooga.fighter.model.loaders;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import vooga.fighter.model.ModelConstants;
import vooga.fighter.model.objects.AttackObject;

public class AttackObjectLoader extends ObjectLoader {

	private AttackObject myAttack;

	/**
	 * Constructs the attack loader with the name to be loaded and the attack which the
	 * loader will modify.
	 * @param attackName
	 * @param attack
	 */
	public AttackObjectLoader (String attackName, AttackObject attack, String pathHierarchy) {
		super(ModelConstants.ATTACKLOADER_PATH_TAG, pathHierarchy);
		myAttack = attack;
		load(attackName, pathHierarchy);
	}

	/**
	 * loads attack object
	 */
	protected void load(String attackName, String pathHierarchy) {
		Document doc = getDocument();
		NodeList attackNodes = doc.getElementsByTagName(getResourceBundle().getString("Attack"));

		for (int i = 0; i < attackNodes.getLength(); i++) {
			Element attackNode = (Element) attackNodes.item(i);
			String name = getAttributeValue(attackNode, getResourceBundle().getString("AttackName"));
			if (attackName.equals(name)) {
				addProperties(attackNode, myAttack);
				NodeList stateNodes = attackNode.getElementsByTagName(getResourceBundle().getString("State"));
				addStates(stateNodes, myAttack);
				myAttack.defineDefaultState(getAttributeValue(attackNode, getResourceBundle().getString("Default")));
			}
		}
	}
}
