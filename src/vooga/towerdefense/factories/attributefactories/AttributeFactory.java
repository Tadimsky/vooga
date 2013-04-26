package vooga.towerdefense.factories.attributefactories;

import vooga.towerdefense.attributes.Attribute;

/**
 * @author Matthew Roy
 *
 */
public class AttributeFactory {
    
    private String myName;
    private double myValue;
    
    public AttributeFactory(String name, double value) {
        myName = name;
        myValue = value;
    }
    
    /**
     * Creates an attribute with a value 
     * default value defined in the xml file
     * or potentially a 
     * @return
     */
    public Attribute create() {
        return new Attribute(myName, myValue);
    }
    
    /**
     * Creates an attribute with a value of some modifier from the 
     * default value defined in the xml file
     * @param modifier
     * @return
     */
    public Attribute create(double modifier) {
        return new Attribute(myName, myValue*modifier);
    }
    

}
