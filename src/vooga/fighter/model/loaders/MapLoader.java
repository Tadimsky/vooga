package vooga.fighter.model.loaders;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import vooga.fighter.model.ModelConstants;
import vooga.fighter.model.objects.EnvironmentObject;
import vooga.fighter.model.objects.MapObject;
import vooga.fighter.model.utils.UpdatableLocation;

/**
 * 
 * @author alanni, David Le Loads the data to the map object
 */

public class MapLoader extends ObjectLoader {

	private MapObject myMap;

	/**
	 * Dummy Constructor only to be used when getting map count
	 */
	public MapLoader(String pathHierarchy) {
		super(ModelConstants.MAPLOADER_PATH_TAG, pathHierarchy);
	}

	/**
	 * Constructs MapLoader, sets file path using the super constructor, and
	 * loads map
	 * 
	 * @param mapName to be loaded
	 * @param map object which is loaded into
	 */
	public MapLoader(String mapName, MapObject map, String pathHierarchy) {
		super(ModelConstants.MAPLOADER_PATH_TAG, pathHierarchy);
		myMap = map;
		load(mapName, pathHierarchy);
		myMap.setCurrentState("background");
		myMap.defineDefaultState("background");
		myMap.getCurrentState().setLooping(true);
	}

	/**
	 * Loads map from xml data
	 * 
	 * @param mapName to be loaded
	 */
	protected void load(String mapName, String pathHierarchy) {
		Document doc = getDocument();
		NodeList mapNodes = doc.getElementsByTagName(getResourceBundle().getString("Map"));

		for (int i = 0; i < mapNodes.getLength(); i++) {
			Element node = (Element) mapNodes.item(i);
			String name = getAttributeValue(node, getResourceBundle().getString("MapName"));
			if (mapName.equals(name)) {
				NodeList stateNodes = ((Element) node).getElementsByTagName(getResourceBundle().getString("State"));
				addStates(stateNodes, myMap);
				myMap.setLocation(new UpdatableLocation(Integer.parseInt(getAttributeValue(node, getResourceBundle()
					.getString("XSize"))) / 2, Integer.parseInt(getAttributeValue(node, getResourceBundle()
					.getString("YSize"))) / 2));
				NodeList startingPosNodes = node.getElementsByTagName(getResourceBundle().getString("StartingPosition"));
				addStartingPositions(startingPosNodes);
				NodeList enviroObjectNodes = node.getElementsByTagName(getResourceBundle().getString("EnvironmentObject"));
				addEnviroObjects(enviroObjectNodes, pathHierarchy);
			}
		}
	}
	
	/**
	 * Checks if the map already exists inside the given xml file.
	 * 
	 * @param mapName to check
	 * @return
	 */
	public boolean contains(String mapName) {
		List<String> existingMaps = getMapNames();
		for(String existingMap : existingMaps) {
			if(existingMap.equals(mapName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns a list of map names inside the designated xml file.
	 * 
	 * @return
	 */
	public List<String> getMapNames() {
		List<String> maps = new ArrayList<String>();
		Document doc = getDocument();
		NodeList mapNodes = doc.getElementsByTagName(getResourceBundle().getString("Map"));
		for (int i = 0; i < mapNodes.getLength(); i++) {
			Element node = (Element) mapNodes.item(i);
			maps.add(getAttributeValue(node, getResourceBundle().getString("MapName")));
		}
		return maps;
	}

	/**
	 * Adds starting position for the characters
	 * 
	 * @param startingPosNodes NodeList of all starting positions available on a map
	 */
	private void addStartingPositions(NodeList startingPosNodes) {
		for (int i = 0; i < startingPosNodes.getLength(); i++) {
			Node startingPosition = startingPosNodes.item(i);
			int xCoord = Integer.parseInt(getAttributeValue(startingPosition,
					getResourceBundle().getString("XCoordinate")));
			int yCoord = Integer.parseInt(getAttributeValue(startingPosition,
					getResourceBundle().getString("YCoordinate")));
			myMap.addStartPosition(new UpdatableLocation(xCoord, yCoord));
		}
	}

	/**
	 * Creates environment objects based on XML data
	 * 
	 * @param enviroObjectNodes to be added to the map
	 */
	private void addEnviroObjects(NodeList enviroObjectNodes, String pathHierarchy) {
		for (int i = 0; i < enviroObjectNodes.getLength(); i++) {
			Node enviroObjectNode = enviroObjectNodes.item(i);
			int xCoord = Integer.parseInt(getAttributeValue(enviroObjectNode, getResourceBundle().getString("XCoordinate")));
			int yCoord = Integer.parseInt(getAttributeValue(enviroObjectNode, getResourceBundle().getString("YCoordinate")));
			EnvironmentObject newEnvironmentObject = new EnvironmentObject(getAttributeValue(enviroObjectNode, getResourceBundle()
				.getString("EnvironmentObjectName")), new UpdatableLocation(xCoord, yCoord), pathHierarchy);
			myMap.addEnviroObject(newEnvironmentObject);
		}
	}
}
