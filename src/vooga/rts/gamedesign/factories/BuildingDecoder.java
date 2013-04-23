package vooga.rts.gamedesign.factories;

import java.awt.Dimension;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import vooga.rts.gamedesign.sprite.gamesprites.interactive.buildings.Building;
import vooga.rts.gamedesign.upgrades.UpgradeTree;
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
	
	private static final String HEAD_TAG = "buildings";
	private static final String TYPE_TAG = "building";
	
	private int DEFAULTTEAM = 0;
	
	private Factory myFactory;

	public BuildingDecoder(Factory factory){
		myFactory = factory;
	}

	@Override
	public void create(Document doc, String tag) {
		//there is some duplicate code between decoders that should be factored out. 
		String path = doc.getElementsByTagName(HEAD_TAG).item(0).getAttributes().getNamedItem(SOURCE_TAG).getTextContent();	
		
	
		
		NodeList nodeLst = doc.getElementsByTagName(TYPE_TAG);
		for(int i = 0 ; i < nodeLst.getLength() ; i++){
			Element nElement = (Element) nodeLst.item(i);
			String name = getElement(nElement, NAME_TAG);
			String img = getElement(nElement, IMAGE_TAG);
			String sound = getElement(nElement, SOUND_TAG);
			int health = Integer.parseInt(getElement(nElement, HEALTH_TAG));
			double buildTime = Double.parseDouble(getElement(nElement, TIME_TAG));
			Building building = (Building) ReflectionHelper.makeInstance(path, 
																			new Pixmap(img),
																  			new Location3D(0,0,0),
																			  new Dimension(50,50),
																			  new Sound(sound),
																			  DEFAULTTEAM,
																			  health,
																			  buildTime);
			
			myFactory.put(name, building);
			//Load Production Dependencies now
			String [] nameCanProduce = nElement.getElementsByTagName(PRODUCE_TAG).item(0).getTextContent().split("\\s+");
			if(nameCanProduce[0] != ""){
				myFactory.putProductionDependency(name, nameCanProduce);
			}
			//Load Strategy Dependencies now
			String[] strategies = new String[3];
			strategies[1] = nElement.getElementsByTagName(OCCUPY_TAG).item(0).getTextContent();
			myFactory.putStrategyDependency(name, strategies);
			
			
		}
		
		
	}
}
