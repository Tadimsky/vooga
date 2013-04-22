package vooga.rts.gamedesign.factories;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import vooga.rts.gamedesign.sprite.gamesprites.interactive.buildings.Building;

public class EasyConstructor {
	private static String CUSTOM_TAG = "custom";
	private static String SOURCE_TAG = "src";
	private static String NAME_TAG = "name";
	
	public EasyConstructor(){
		
	}
	
	
	public <T>T make(Document doc){
		NodeList customList = doc.getElementsByTagName(CUSTOM_TAG);
		for(int j = 0 ; j < customList.getLength() ; j++){
			Element customElement = (Element) customList.item(j);
			String classPath = customElement.getAttribute(SOURCE_TAG);
			String name = customElement.getElementsByTagName(NAME_TAG).item(0).getTextContent();
			Constructor<?> building = makeSimpleCustomConstructor(classPath);
			
			return building.newInstance();

				
		}
		
		
	}
	
	private Constructor<?> makeSimpleCustomConstructor(String path) {
		Class<?> headClass = null;
		try {
			headClass = Class.forName(path);
			return headClass.getConstructor();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
