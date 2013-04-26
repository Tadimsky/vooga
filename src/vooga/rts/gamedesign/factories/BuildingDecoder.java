package vooga.rts.gamedesign.factories;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import vooga.rts.gamedesign.sprite.gamesprites.interactive.buildings.Building;
import vooga.rts.gamedesign.strategy.production.CanProduce;
import vooga.rts.gamedesign.strategy.production.CannotProduce;
import vooga.rts.gamedesign.strategy.production.ProductionStrategy;
import vooga.rts.gamedesign.upgrades.UpgradeTree;
import vooga.rts.resourcemanager.ResourceManager;
import vooga.rts.util.Location3D;
import vooga.rts.util.Pixmap;
import vooga.rts.util.ReflectionHelper;
import vooga.rts.util.Sound;


/**
 * This class takes care of parsing the XML file for custom Building information and 
 * instantiating this custom building. 
 * 
 * @author Francesco Agosti
 *
 */
public class BuildingDecoder extends Decoder{
	
	
	private Factory myFactory;
	private CustomHandler myCustomHandler;
	
	public BuildingDecoder(Factory factory){
		myFactory = factory;
		myCustomHandler = new CustomHandler(factory);
		
	}

	@Override
	public void create(Document doc, String type) {
		//there is some duplicate code between decoders that should be factored out. 
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
			Building building = (Building) ReflectionHelper.makeInstance(path, 
					new Pixmap(ResourceManager.getInstance()
		                    .<BufferedImage> getFile(img, BufferedImage.class)),
																			 new Sound(sound),
																			 health,
																			 buildTime);
			
			//Load Production Dependencies now
			String [] nameCanProduce = getElement(nElement, PRODUCE_TAG).split("\\s+");
			System.out.println("name: " + name);
			for (String s: nameCanProduce) {
				System.out.println("baby: " + s);
			}
			if(nameCanProduce[0] != ""){
				//building.setProductionStrategy(new CanProduce(building));
				myFactory.putProductionDependency(name, nameCanProduce);
			} else {
				//building.setProductionStrategy(new CannotProduce());
			}
			myFactory.put(name, building);
			//Load Strategy Dependencies now
			String[] strategies = new String[5];
			strategies[0] = CANNOT_ATTACK;
			strategies[1] = getElement(nElement, OCCUPY_TAG);
			strategies[2] = CANNOT_GATHER;
			strategies[3] = getElement(nElement, UPGRADE_TAG);
			strategies[4] = getElement(nElement, UPGRADE_TREE_NAME_TAG);
			//Load Weapon Dependency
			String[] weapons = getElement(nElement, MYWEAPONS_TAG).split("\\s+");
			if(weapons[0] != ""){
				myFactory.putWeaponDependency(name, weapons);
				strategies[0] = CAN_ATTACK;
			}
			
			myFactory.putStrategyDependency(name, strategies);
				
			
			
			
		}
		
		
	}
}
