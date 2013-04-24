package vooga.rts.gamedesign.factories;

import java.awt.Dimension;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.buildings.Building;
import vooga.rts.util.Location3D;
import vooga.rts.util.Pixmap;
import vooga.rts.util.ReflectionHelper;
import vooga.rts.util.Sound;


/**
* This Class acts as the decoder for all interactive entities since they all have the same constructor. 
* If you would like to extend interactive entity to implement a class with a different constructor then you 
* should use default values for the extra parameters (or you could write your own decoder).
*
*/
public class InteractiveEntityDecoder extends Decoder {
	
	private int DEFAULTTEAM = 0;
	
	private Factory myFactory;
	private CustomHandler myCustomHandler;

	public InteractiveEntityDecoder(Factory factory){
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
			InteractiveEntity entity = (InteractiveEntity) ReflectionHelper.makeInstance(path, 
																			new Pixmap(img),
																  			new Location3D(0,0,0),
																			  new Dimension(50,50),
																			  new Sound(sound),
																			  DEFAULTTEAM,
																			  health,
																			  buildTime);
			
			myFactory.put(name, entity);
			//Load Production Dependencies now
			String [] nameCanProduce = nElement.getElementsByTagName(PRODUCE_TAG).item(0).getTextContent().split("\\s+");
			if(nameCanProduce[0] != ""){
				myFactory.putProductionDependency(name, nameCanProduce);
			}
			//Load Strategy Dependencies now
			String[] strategies = new String[3];
			strategies[0] = getElement(nElement, ATTACK_TAG);
			strategies[1] = getElement(nElement, OCCUPY_TAG);
			
			if(subtype.equals(BUILDING_TYPE)) {
				strategies[2] = "cannotgather";
			}else{
				strategies[2] = getElement(nElement, GATHER_TAG);
			}

			
			myFactory.putStrategyDependency(name, strategies);
			
			
		}
	}
}
	

