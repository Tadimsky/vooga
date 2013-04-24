package vooga.rts.gamedesign.factories;


import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 
 * @author Ryan Fishel
 * @author Kevin Oh
 * @author Francesco Agosti
 * @author Wenshun Liu
 * 
 * This is the super class for all decoders.
 *
 */
public abstract class Decoder {

	
	protected static final String TIME_TAG = "buildtime";
	protected static final String CUSTOM_TAG = "custom";
	protected static final String BUILDING_TYPE = "building";
	protected static final String COST_TAG = "cost";
	protected static final String NAME_TAG = "name";
	protected static final String IMAGE_TAG = "img";
	protected static final String SOUND_TAG = "sound";
	protected static final String HEALTH_TAG = "health";
	protected static final String ATTACK_TAG = "attack";
	protected static final String OCCUPY_TAG = "occupy";
	protected static final String PRODUCE_TAG = "produce";
	protected static final String GATHER_TAG = "gather";
	protected static final String SOURCE_TAG = "src";
	protected static final String SPEED_TAG = "speed";
	protected static final String RANGE_TAG = "range";
	protected static final String COOLDOWN_TAG = "cooldown";
	protected static final String MYPROJECTILE_TAG = "myprojectile";
	protected static final String DAMAGE_TAG = "damage";
	protected static final String LIFESPAN_TAG = "lifespan";
	protected static final String CAN_ATTACK = "canattack";
	protected static final String CANNOT_ATTACK = "cannotattack";
	protected static final String MYWEAPONS_TAG = "myweapons";
	protected static final String CANNOT_GATHER = "cannotgather";
	
	
	
	/**
	 * Gets the text content of the specified tag from the Element given as a parameter
	 * @param element
	 * @param tag
	 * @return String that is the correct content
	 */
	protected String getElement(Element element, String tag){
		return element.getElementsByTagName(tag).item(0).getTextContent();

	}
	
	/**
	 * Depending on the decoder, this method takes in the document, parses it and instantiates
	 * objects based on values given in the XML file. If objects also have dependencies, such as Buildings
	 * that produce units and have strategies, it records these dependencies so that they can be set-up later
	 * (since dependencies exist between two objects that both need to be instantiated). This allows us to define
	 * game elements in the XML file in any order (as it should be). 
	 * @param doc
	 */
	public abstract void create(Document doc, String tag);
}
