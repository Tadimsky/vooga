package vooga.rts.gamedesign.factories;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import vooga.rts.gamedesign.upgrades.UpgradeNode;
import vooga.rts.gamedesign.upgrades.UpgradeTree;
import vooga.rts.util.ReflectionHelper;

/**
 * This class is an extension of Decoder class that is in charge of the creation
 * of UpgradeTree for upgrade package.
 * 
 * @author Ryan Fishel
 * @author Kevin Oh
 * @author Francesco Agosti
 * @author Wenshun Liu
 *
 */
public class UpgradeDecoder extends Decoder {
	public static final String UPGRADE_CATEGORY_TAG = "type";
	public static final String CATEGORY_NAME_TAG = "name";
	public static final String INDIVIDUAL_UPGRADE_TAG = "upgradeNode";
	public static final String PARENT_UPGRADE_TAG = "parent";
	public static final String TITLE_TAG = "nodeName";
	public static final String AFFECTING_OBJECT_TAG = "object";
	public static final String AFFECTING_VALUE_TAG = "value";
	public static final String COSTING_RESOURCE_TYPE_TAG = "resourceCostType";
	public static final String COSTING_RESOURCE_AMOUNT_TAG = "resourceAmount";
	
	private Factory myFactory;
	private Map<String, String> myUpgradeNodeType;
	
	public UpgradeDecoder(Factory factory){
		myFactory = factory;
		myUpgradeNodeType = new HashMap<String, String>();
		//TODO: close this.
		myUpgradeNodeType.put("Health", "vooga.rts.gamedesign.upgrades.HealthUpgradeNode");
		myUpgradeNodeType.put("Damage", "vooga.rts.gamedesign.upgrades.DamageUpgradeNode");
		myUpgradeNodeType.put("AttackStrategy", "vooga.rts.gamedesign.upgrades.AttackUpgradeNode");
		myUpgradeNodeType.put("Range", "vooga.rts.gamedesign.upgrades.RangeUpgradeNode");
	}
	
	/**
	 * Creates the UpgradeTree by receiving Document passed in from
	 * the Factory, containing necessary information related to the
	 * UpgradeTree
	 * 
	 * @param doc the Document passed in from Factory
	 * @throws NumberFormatException 
	 * 
	 */

	public void create(Document doc, String type) throws NumberFormatException {
		//TODO: get all upgradeTrees into a same file. Return all results into a map
		UpgradeTree upgradeTree = new UpgradeTree();
		
		NodeList nodeLst = doc.getElementsByTagName(UPGRADE_CATEGORY_TAG);

		for (int i = 0; i < nodeLst.getLength(); i++) {

			Element typeElmnt = (Element) nodeLst.item(i);
			Element nameElmnt = (Element) typeElmnt.getElementsByTagName(CATEGORY_NAME_TAG).item(0);
			NodeList name = nameElmnt.getChildNodes();
			upgradeTree.addBranch(((Node) name.item(0)).getNodeValue());
			
			NodeList upgradeNodeList = typeElmnt.getElementsByTagName(INDIVIDUAL_UPGRADE_TAG);
			for (int j=0; j<upgradeNodeList.getLength(); ++j) {
				Element upgradeNodeElement = (Element) upgradeNodeList.item(j);
				
				String parent = loadSingleLine(upgradeNodeElement, PARENT_UPGRADE_TAG);
				String nodeName = loadSingleLine(upgradeNodeElement, TITLE_TAG);
				String object = loadSingleLine(upgradeNodeElement, AFFECTING_OBJECT_TAG);
				String value = loadSingleLine(upgradeNodeElement, AFFECTING_VALUE_TAG);
				String costedResource = loadSingleLine(upgradeNodeElement, COSTING_RESOURCE_TYPE_TAG);
				String costedResourceAmount = loadSingleLine(upgradeNodeElement, COSTING_RESOURCE_AMOUNT_TAG);
				
				UpgradeNode newUpgrade = (UpgradeNode) ReflectionHelper.makeInstance(myUpgradeNodeType.get(object), upgradeTree, nodeName, Integer.parseInt(value), Integer.parseInt(costedResourceAmount));
				UpgradeNode current = upgradeTree.findNode(parent);
				current.addChild(newUpgrade);
			}
		}
		upgradeTree.updateTreeStatus();
		//printTree(upgradeTree);
		myFactory.put("", upgradeTree);
	}
	
	private String loadSingleLine(Element element, String tag) {
		NodeList nodeElmntLst = element.getElementsByTagName(tag);
		Element nodeElmnt = (Element) nodeElmntLst.item(0);
		String result = ((Node)nodeElmnt.getChildNodes().item(0)).getNodeValue();
		return result;
	}
	
	/**
	 * TESTING PURPOSE. PRINTS TREE.
	 * @param upgradeTree
	 */
	private void printTree(UpgradeTree upgradeTree) {
		//System.out.println("Also Goes Here");
		for (UpgradeNode u: upgradeTree.getHead().getChildren()) {
			UpgradeNode current = u;
			while (!current.getChildren().isEmpty()) {
				for (UpgradeNode node: current.getChildren()) {
					System.out.println("Name: " + node.getUpgradeName() +
							" Parent Name " + current.getUpgradeName());
				}
				current = current.getChildren().get(0);
						//should recurse if really want to print the whole tree
			}
		}
		for (UpgradeNode u: upgradeTree.getCurrentUpgrades()) {
			System.out.println("Current Upgradss: " + u.getUpgradeName());
		}
	}
}
