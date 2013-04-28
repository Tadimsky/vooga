package vooga.rts.gamedesign.factories;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import util.Location;
import vooga.rts.gamedesign.sprite.gamesprites.*;
import vooga.rts.util.Location3D;
import vooga.rts.util.Pixmap;
import vooga.rts.util.ReflectionHelper;


/**
 * Decodes an XML file that contains resource information and instantiates those reasources. 
 * Once the resources and instantiated it puts them in the Sprite map that is found in the factory. 
 * The resource "name" is used as the key. 
 * @author Francesco Agosti
 *
 */
public class ResourceDecoder extends Decoder{
	
	private static final Dimension RESOURCE_SIZE = new Dimension(50,50);
	
	private Factory myFactory;
	private CustomHandler myCustomHandler;
	
	
	public ResourceDecoder(Factory factory){
		myFactory = factory;
		myCustomHandler = new CustomHandler(factory);
	}
		
	/**
	 * Adds the resources defined in the XML file to the map of Sprites found in the factory. 
	 * 
	 * @Override
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	public void create(Document doc, String type) {
		String path = doc.getElementsByTagName(type).item(0).getAttributes().getNamedItem(SOURCE_TAG).getTextContent();
		String subtype = type.substring(0, type.length()-1);
		myCustomHandler.create(doc,subtype);
		NodeList nodeLst = doc.getElementsByTagName(subtype);
		
		for(int i = 0 ; i < nodeLst.getLength() ; i++){
			Element nElement = (Element) nodeLst.item(i);
			String name = nElement.getElementsByTagName(NAME_TAG).item(0).getTextContent();
			String img = nElement.getElementsByTagName(IMAGE_TAG).item(0).getTextContent();
			int health = Integer.parseInt((nElement.getElementsByTagName(HEALTH_TAG).item(0).getTextContent()));
			
			
			Resource resource = (Resource) ReflectionHelper.makeInstance(path, new Pixmap(img),
																		new Location3D(0,0,0),
																		RESOURCE_SIZE,
																		0,
																		health,
																		name);
					
																
			myFactory.put(name, resource);
		}
	}
}
