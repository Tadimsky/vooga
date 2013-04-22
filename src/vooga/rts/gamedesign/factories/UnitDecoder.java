package vooga.rts.gamedesign.factories;

import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import vooga.rts.gamedesign.sprite.gamesprites.interactive.units.Unit;
import vooga.rts.util.Location3D;
import vooga.rts.util.Pixmap;
import vooga.rts.util.ReflectionHelper;
import vooga.rts.util.Sound;

public class UnitDecoder extends Decoder {

	private static String HEAD_TAG = "units";
	private static String TYPE_TAG = "unit";
	private static final String NAME_TAG = "name";
	private static final String IMAGE_TAG = "img";
	private static final String SOUND_TAG = "sound";
	private static final String HEALTH_TAG = "health";
	private static final String SOURCE_TAG = "src";
	private static final String TIME_TAG = "buildtime";
	
	
	private Factory myFactory;
	public UnitDecoder(Factory factory){
		myFactory = factory;
	}
	
	
	@Override
	public void create(Document doc) throws InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException,
			SecurityException, ClassNotFoundException {
		
		String path = doc.getElementsByTagName(HEAD_TAG).item(0).getAttributes().getNamedItem(SOURCE_TAG).getTextContent();
		Class<?> headClass = null;
		
		try {
			headClass = Class.forName(path);			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		NodeList nodeLst = doc.getElementsByTagName(TYPE_TAG);
		for(int i = 0 ; i < nodeLst.getLength() ; i++){
			Element nElement = (Element) nodeLst.item(i);
			String name = nElement.getElementsByTagName(NAME_TAG).item(0).getTextContent();
			String img = nElement.getElementsByTagName(IMAGE_TAG).item(0).getTextContent();
			String sound = nElement.getElementsByTagName(SOUND_TAG).item(0).getTextContent();
			int health = Integer.parseInt(nElement.getElementsByTagName(HEALTH_TAG).item(0).getTextContent());
			int buildTime = Integer.parseInt(nElement.getElementsByTagName(TIME_TAG).item(0).getTextContent());
			
			Unit unit = (Unit) ReflectionHelper.makeInstance(headClass, new Pixmap(img), 
																		new Location3D(0,0,0),
																		new Dimension(50,50),
																		new Sound(sound),
																		0,
																		health,
																		buildTime);
			
			myFactory.put(name, unit);
			
		}

	}

}
