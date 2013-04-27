package vooga.rts.gamedesign.factories;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import vooga.rts.gamedesign.sprite.gamesprites.interactive.units.Unit;
import vooga.rts.resourcemanager.ResourceManager;
import vooga.rts.util.Location3D;
import vooga.rts.util.Pixmap;
import vooga.rts.util.ReflectionHelper;
import vooga.rts.util.Sound;


/**
 * This class takes care of parsing the XML file for custom Unit information and 
 * instantiating this custom Unit. 
 * 
 * @author Francesco Agosti
 * @modify Wenshun Liu
 */
public class UnitDecoder extends Decoder {

	private static String HEAD_TAG = "units";
	private static String TYPE_TAG = "unit";
	
	
	private Factory myFactory;
	private CustomHandler myCustomHandler;
	
	public UnitDecoder(Factory factory){
		myFactory = factory;
		myCustomHandler = new CustomHandler(factory);
	}
	
	/**
	 * Loads and creates the list of Units. Also loads information related to
	 * the Unit such as its Weapon (if CanAttack), strategies and
	 * InteractiveEntities that are able to produce. Stroe those information
	 * in maps in Factory.
	 */
	@Override
	public void create(Document doc, String type) {
		String path = doc.getElementsByTagName(type).item(0).getAttributes().getNamedItem(SOURCE_TAG).getTextContent();	
		String subtype = type.substring(0, type.length()-1);
		NodeList nodeLst = doc.getElementsByTagName(subtype);
		myCustomHandler.create(doc, subtype);
		for(int i = 0 ; i < nodeLst.getLength() ; i++){
			Element nElement = (Element) nodeLst.item(i);
			String name = getElement(nElement, NAME_TAG);
			String img = getElement(nElement, IMAGE_TAG);
			String sound = getElement(nElement, SOUND_TAG);
			int health = Integer.parseInt(getElement(nElement, HEALTH_TAG));
			double buildTime = Double.parseDouble(getElement(nElement, TIME_TAG));
			int speed = Integer.parseInt(getElement(nElement, SPEED_TAG));
			
			Unit unit = (Unit) ReflectionHelper.makeInstance(path, new Pixmap(ResourceManager.getInstance()
                    .<BufferedImage> getFile(img, BufferedImage.class)), 
					new Sound(sound), health, buildTime, speed);
			myFactory.put(name, unit);
			loadProductionDependencies(nElement, name);
			String[] strategies = loadStrategyDependencies(nElement);
			strategies = loadWeaponDependencies(nElement, name, strategies);
			
			myFactory.putStrategyDependency(name, strategies);
		}
	}
	
	private void loadProductionDependencies(Element element, String unitName) {
		String [] nameCanProduce = getElement(element, PRODUCE_TAG).split("\\s+");
		if(nameCanProduce[0] != ""){
			myFactory.putProductionDependency(unitName, nameCanProduce);
		}
	}
	
	private String[] loadStrategyDependencies(Element element) {
		String[] strategies = new String[5];
		strategies[0] = CANNOT_ATTACK;
		strategies[1] = getElement(element, OCCUPY_TAG);
		strategies[2] = getElement(element, GATHER_TAG);
		strategies[3] = getElement(element, UPGRADE_TAG);
		strategies[4] = getElement(element, UPGRADE_TREE_NAME_TAG);
		return strategies;
	}
	
	private String[] loadWeaponDependencies(Element element, String unitName, String[] strategies) {
		String[] weapons = getElement(element, MYWEAPONS_TAG).split("\\s+");
		if(weapons[0] != ""){
			myFactory.putWeaponDependency(unitName, weapons);
			strategies[0] = CAN_ATTACK;
		}
		return strategies;
	}
}
