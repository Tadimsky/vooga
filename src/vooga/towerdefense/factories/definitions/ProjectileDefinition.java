package vooga.towerdefense.factories.definitions;

import java.awt.Dimension;

import vooga.towerdefense.factories.attributefactories.AttributeManagerFactory;
import vooga.towerdefense.util.Location;
import vooga.towerdefense.util.Pixmap;


/**
 * Default ProjectileDefinition used for testing projectiles; 
 * only GameElementDefinition and GameElement will be made and used at the end.
 * 
 * @author XuRui
 *
 */
public class ProjectileDefinition extends GameElementDefinition {
	
	private Pixmap myImage;
	private Location myCenter;
	private Dimension mySize;

	public ProjectileDefinition() {
		myImage = DefinitionConstants.DEFAULT_PROJECTILE_IMAGE;
		myCenter = DefinitionConstants.DEFAULT_PROJECTILE_LOCATION;
		mySize = DefinitionConstants.DEFAULT_PROJECTILE_SIZE;
	}
	
	public Pixmap getImage(){
		return myImage;
	}
	
    public Location getCenter () {
        return myCenter;
    }

    public Dimension getSize () {
        return mySize;
    }

	@Override
	public AttributeManagerFactory getAttributeManagerFactory() {
		return new AttributeManagerFactory();
	}

	
	
	
	
}
