package vooga.towerdefense.factories.definitions;

import java.awt.Dimension;

import vooga.towerdefense.attributes.DefaultAttributeManager;
import vooga.towerdefense.factories.attributefactories.AttributeManagerFactory;
import vooga.towerdefense.util.Pixmap;
import vooga.towerdefense.util.Location;

/**
 * Default TowerDefinition used for testing tower factories; 
 * only GameElementDefinition and GameElement will be made and used at the end.
 * 
 * @author XuRui
 *
 */

public class TowerDefinition extends GameElementDefinition {
	
	private Pixmap myImage;
	private Location myCenter;
	private Dimension mySize;

	public TowerDefinition() {
		myImage = DefinitionConstants.DEFAULT_TOWER_IMAGE;
		myCenter = DefinitionConstants.DEFAULT_TOWER_LOCATION;
		mySize = DefinitionConstants.DEFAULT_TOWER_SIZE;
		
	}
	
	public void setImage(Pixmap image){
		myImage = image;
	}
	
    public Pixmap getImage () {
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
