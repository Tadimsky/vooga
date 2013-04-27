package vooga.rts.gamedesign.factories;

import java.lang.reflect.InvocationTargetException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import vooga.rts.gamedesign.sprite.gamesprites.Resource;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.util.ReflectionHelper;

/**
* If the game designer implements a custom class that extends Resource or Interactive entity then we offer
* them the ability to instantiate this class through the factory under the "custom" tag (see XML for example). 
* By convention the class has to have an empty constructor. 
*
*
*/

public class CustomHandler extends Decoder{
	
	private static final String CUSTOM_TAG = "custom";
	private static final String NAME_TAG = "name";
	private static final String SOURCE_TAG = "src";
	private static final String BUILDING_TYPE = "building";
	private static final String RESOURCE_TYPE = "resource";
	private static final String UNIT_TYPE = "unit";
	
	private Factory myFactory;
	public CustomHandler(Factory factory){
		myFactory = factory;
	}


	@Override
	public void create(Document doc, String type) {
		NodeList custom = doc.getElementsByTagName(CUSTOM_TAG + type);
		for(int i = 0; i < custom.getLength() ; i++){
			Element customElement = (Element) custom.item(i);
			String path = customElement.getAttributes().getNamedItem(SOURCE_TAG).getTextContent();
			String name = getElement(customElement, NAME_TAG);
			Object toPut = ReflectionHelper.makeInstance(path);
			
			if(type.equals(BUILDING_TYPE)){
				myFactory.put(name, (InteractiveEntity) toPut);
			}
			if(type.equals(RESOURCE_TYPE)){
				myFactory.put(name, (Resource) toPut);

			}
			if(type.equals(UNIT_TYPE)){
				myFactory.put(name, (InteractiveEntity) toPut);

			}
			
		}
		
	}
	
	
	
	
	

}
