package vooga.rts.gamedesign.factories;

import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import vooga.rts.util.Information;


/**
 * 
 * @author Francesco Agosti
 * @author Wenshun Liu
 * 
 *         This is the super class for all decoders.
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
    protected static final String UPGRADE_TAG = "upgrade";
    protected static final String UPGRADE_TREE_NAME_TAG = "upgradeTreeName";
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
    protected static final String CAN_UPGRADE = "canupgrade";
    protected static final String MYWEAPONS_TAG = "myweapons";
    protected static final String CANNOT_GATHER = "cannotgather";
    protected static final String INFORMATION_TAG = "information";
    protected static final String DESCRIPTION_TAG = "description";
    protected static final String BUTTON_TAG = "button";

    /**
     * Gets the text content of the specified tag from the Element given as a parameter
     * 
     * @param element
     * @param tag
     * @return String that is the correct content
     */
    protected String getElement (Element element, String tag) {
        return element.getElementsByTagName(tag).item(0).getTextContent();
    }

    /**
     * Looks through a nodeList and uses the information convention we have established to find
     * the description and button img necessary to instantiate an information object, then returns
     * this object.
     * 
     * @param name
     * @param listOfInfo
     * @return new information from the content of the NodeList
     */
    protected Information getInformation (String name, Element info) {
        Element infoElement =
                (Element) info.getElementsByTagName(INFORMATION_TAG).item(0).getChildNodes();
        String description = getElement(infoElement, DESCRIPTION_TAG);
        String button = getElement(infoElement, BUTTON_TAG);
        Element costElement =
                (Element) infoElement.getElementsByTagName(COST_TAG).item(0).getChildNodes();
        NodeList mapList = costElement.getChildNodes();
        Map<String, Integer> costMap = null;
        for (int i = 0; i < mapList.getLength(); i++) {
            costMap = new HashMap<String, Integer>();
            Node costElem = mapList.item(i);
            if (costElem.getNodeType() == Node.ELEMENT_NODE) {
                String rName = costElem.getNodeName();
                int cost = Integer.parseInt(costElem.getTextContent());
                costMap.put(rName, cost);
            }

        }
        return new Information(name, description, button, costMap);
    }

    /**
     * Depending on the decoder, this method takes in the document, parses it and instantiates
     * objects based on values given in the XML file. If objects also have dependencies, such as
     * Buildings
     * that produce units and have strategies, it records these dependencies so that they can be
     * set-up later
     * (since dependencies exist between two objects that both need to be instantiated). This allows
     * us to define
     * game elements in the XML file in any order (as it should be).
     * 
     * @param doc
     */
    public abstract void create (Document doc, String tag);
}
