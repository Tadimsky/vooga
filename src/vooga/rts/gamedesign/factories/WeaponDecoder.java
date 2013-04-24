package vooga.rts.gamedesign.factories;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import vooga.rts.gamedesign.weapon.Weapon;
import vooga.rts.util.ReflectionHelper;

public class WeaponDecoder extends Decoder {
	
	private Factory myFactory;
	
	public WeaponDecoder(Factory factory){
		myFactory = factory;
	}

	@Override
	public void create(Document doc, String type) {
		String path = doc.getElementsByTagName(type).item(0).getAttributes().getNamedItem(SOURCE_TAG).getTextContent();	
		String subtype = type.substring(0, type.length()-1);
		NodeList nodeLst = doc.getElementsByTagName(subtype);
		for(int i = 0 ; i < nodeLst.getLength() ; i++){
			Element wElement = (Element) nodeLst.item(i);
			String name = getElement(wElement, NAME_TAG);
			int range = Integer.parseInt(getElement(wElement, RANGE_TAG));
			double cooldown = Double.parseDouble(getElement(wElement, COOLDOWN_TAG));
			Weapon weapon = (Weapon) ReflectionHelper.makeInstance(path, range, cooldown);
			myFactory.put(name, weapon);
			
			
			//Load Projectile Dependencies
			String projectile = getElement(wElement, MYPROJECTILE_TAG);
			if(projectile != ""){
				myFactory.putProjectileDependency(name, projectile);
			}
			
		}
	}

}
