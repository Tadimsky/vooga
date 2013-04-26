package vooga.towerdefense.factories.attributefactories;

import java.util.ArrayList;
import java.util.List;
import vooga.towerdefense.attributes.Attribute;
import vooga.towerdefense.attributes.AttributeManager;


/**
 * Basic attribute factory that creates and populates new attribute for specific elements
 * 
 * @author Matthew Roy
 * 
 */
public class AttributeManagerFactory {

    private List<AttributeFactory> myInputAttributes;

    /**
     * Dangerous because it uses instantiated attributes
     * @param gameElementAttributes
     */
    public AttributeManagerFactory (List<AttributeFactory> gameElementAttributes) {
        myInputAttributes = new ArrayList<AttributeFactory>();
        myInputAttributes.addAll(gameElementAttributes);
    }

    /**
     * Another constructor just in case this is easier
     * @param attributeName
     * @param attributeValue
     */
    public AttributeManagerFactory (List<String> attributeName, List<Double> attributeValue) {
        myInputAttributes = new ArrayList<AttributeFactory>();
        for (int i = 0; i<attributeName.size(); i++) {
            myInputAttributes.add(new AttributeFactory(attributeName.get(i), attributeValue.get(i)));
        }
    }

    public AttributeManagerFactory () {
        myInputAttributes = new ArrayList<AttributeFactory>();
    }

    /**
     * Creates attributes at default values
     * @return
     */
    public AttributeManager makeAttributeManager () {
        return makeAttributeManager(1);
    }
    
    /**
     * Creates attributes at a multiple of the default values
     * @param modifier
     * @return
     */
    public AttributeManager makeAttributeManager (double modifier) {
        AttributeManager manager = new AttributeManager();
        for (AttributeFactory fact : myInputAttributes) {
            manager.addAttribute(fact.create());
        }
        return manager;
    }

}
