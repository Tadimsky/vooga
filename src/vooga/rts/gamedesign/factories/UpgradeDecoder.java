package vooga.rts.gamedesign.factories;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import vooga.rts.gamedesign.sprite.InteractiveEntity;
import vooga.rts.gamedesign.upgrades.DamageUpgradeNode;
import vooga.rts.gamedesign.upgrades.HealthUpgradeNode;
import vooga.rts.gamedesign.upgrades.UpgradeNode;
import vooga.rts.gamedesign.upgrades.UpgradeTree;

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
	public static final String ID_TAG = "id";
	public static final String TITLE_TAG = "nodeName";
	public static final String AFFECTING_OBJECT_TAG = "object";
	public static final String AFFECTING_VALUE_TAG = "value";
	
	private Factory myFactory;
	private InteractiveEntity myInteractiveEntity;
	
	public UpgradeDecoder(Factory factory){
		myFactory = factory;
	}
	
	public void addInteractiveEntity(InteractiveEntity i) {
		myInteractiveEntity = i;
	}
	
	/**
	 * Creates the UpgradeTree by receiving Document passed in from
	 * the Factory, containing necessary information related to the
	 * UpgradeTree
	 * 
	 * @param doc the Document passed in from Factory
	 * 
	 */

	public UpgradeTree create(Document doc) {

		UpgradeTree upgradeTree = new UpgradeTree();
		
		NodeList nodeLst = doc.getElementsByTagName(UPGRADE_CATEGORY_TAG);

		for (int i = 0; i < nodeLst.getLength(); i++) {

			Element typeElmnt = (Element) nodeLst.item(i);
			NodeList nameElmntLst = typeElmnt.getElementsByTagName(CATEGORY_NAME_TAG);
			Element nameElmnt = (Element) nameElmntLst.item(0);
			NodeList name = nameElmnt.getChildNodes();
			UpgradeNode branchHead = upgradeTree.addBranch(i+1, ((Node) name.item(0)).getNodeValue());
			UpgradeNode current = branchHead;
			
			NodeList upgradeNodeList = typeElmnt.getElementsByTagName(INDIVIDUAL_UPGRADE_TAG);
			for (int j=0; j<upgradeNodeList.getLength(); ++j) {
				Element upgradeNodeElement = (Element) upgradeNodeList.item(j);
				
				NodeList idNodeElmntLst = upgradeNodeElement.getElementsByTagName(ID_TAG);
				Element idNodeElmnt = (Element) idNodeElmntLst.item(0);
				String id = ((Node)idNodeElmnt.getChildNodes().item(0)).getNodeValue();
				
				NodeList nodeNameElmntLst = upgradeNodeElement.getElementsByTagName(TITLE_TAG);
				Element nodeNameElmnt = (Element) nodeNameElmntLst.item(0);
				String nodeName = ((Node)nodeNameElmnt.getChildNodes().item(0)).getNodeValue();
				
				NodeList objectElmntLst = upgradeNodeElement.getElementsByTagName(AFFECTING_OBJECT_TAG);
				Element objectElmnt = (Element) objectElmntLst.item(0);
				String object = ((Node)objectElmnt.getChildNodes().item(0)).getNodeValue();
				
				NodeList valueElmntLst = upgradeNodeElement.getElementsByTagName(AFFECTING_VALUE_TAG);
				Element valueElmnt = (Element) valueElmntLst.item(0);
				String value = ((Node)valueElmnt.getChildNodes().item(0)).getNodeValue();
				
				if (object.equals("Health")) { //SO BAD!!
					UpgradeNode newLeaf = current.addChild(new HealthUpgradeNode(upgradeTree, Integer.parseInt(id), nodeName, object, Integer.parseInt(value)));
					current = newLeaf;
				} else if (object.equals("Damage")) {
					UpgradeNode newLeaf = current.addChild(new DamageUpgradeNode(upgradeTree, Integer.parseInt(id), nodeName, object, Integer.parseInt(value)));
					current = newLeaf;
				}
			}
		}
		upgradeTree.updateTreeStatus();
		printTree(upgradeTree);
		return upgradeTree;
	}
	
	/**
	 * TESTING PURPOSE. PRINTS TREE.
	 * @param upgradeTree
	 */
	private void printTree(UpgradeTree upgradeTree) {
		for (UpgradeNode u: upgradeTree.getHead().getChildren()) {
			UpgradeNode current = u;
			while (!current.getChildren().isEmpty()) {
				System.out.println("Type: " + current.getChildren().get(0).getUpgradeType() +
						" Parent ID " + current.getID() + " ID " + 
						current.getChildren().get(0).getID());
				current = current.getChildren().get(0);
			}
		}
		for (UpgradeNode u: upgradeTree.getCurrentUpgrades()) {
			System.out.println("Current Upgradss: " + u.getID());
		}
		for (UpgradeNode u: upgradeTree.getNextUpgrades()) {
			System.out.println("Next Upgrades: " + u.getID());
		}
	}
}
