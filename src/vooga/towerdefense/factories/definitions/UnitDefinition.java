package vooga.towerdefense.factories.definitions;

import java.awt.Dimension;

import vooga.towerdefense.factories.attributefactories.AttributeManagerFactory;
import vooga.towerdefense.util.Location;
import vooga.towerdefense.util.Pixmap;


/**
 * Default UnitDefinition used for testing unit factories; 
 * only GameElementDefinition will be made and used at the end.
 * 
 * @author Matthew Roy
 *
 */
public class UnitDefinition extends GameElementDefinition {
    
	private Pixmap myImage;
	private Location myCenter;
	private Dimension mySize;

    public UnitDefinition () {
        myImage = DefinitionConstants.DEFAULT_UNIT_IMAGE;
        myCenter = DefinitionConstants.DEFAULT_UNIT_LOCATION;
        mySize = DefinitionConstants.DEFAULT_UNIT_SIZE;
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
