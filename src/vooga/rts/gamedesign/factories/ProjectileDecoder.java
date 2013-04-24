package vooga.rts.gamedesign.factories;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import vooga.rts.gamedesign.sprite.gamesprites.Projectile;
import vooga.rts.gamedesign.weapon.Weapon;
import vooga.rts.util.Pixmap;
import vooga.rts.util.ReflectionHelper;

public class ProjectileDecoder extends Decoder {

	private Factory myFactory;
	
	public ProjectileDecoder(Factory factory){
		myFactory = factory;
	}

	@Override
	public void create(Document doc, String type) {
		String path = doc.getElementsByTagName(type).item(0).getAttributes().getNamedItem(SOURCE_TAG).getTextContent();	
		String subtype = type.substring(0, type.length()-1);
		NodeList nodeLst = doc.getElementsByTagName(subtype);
		for(int i = 0 ; i < nodeLst.getLength() ; i++){
			Element pElement = (Element) nodeLst.item(i);
			String name = getElement(pElement, NAME_TAG);
			String image = getElement(pElement, IMAGE_TAG);
			int damage = Integer.parseInt(getElement(pElement, DAMAGE_TAG));
			int lifespan = Integer.parseInt(getElement(pElement, LIFESPAN_TAG));
			int speed = Integer.parseInt(getElement(pElement, SPEED_TAG));
			Projectile weapon = (Projectile) ReflectionHelper.makeInstance(path, new Pixmap(image),
																		damage,
																		lifespan,
																		speed);
			
			myFactory.put(name, weapon);
		}
	}
}
