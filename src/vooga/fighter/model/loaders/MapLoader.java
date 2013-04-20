package vooga.fighter.model.loaders;


import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import util.Pixmap;
import vooga.fighter.model.objects.EnvironmentObject;
import vooga.fighter.model.objects.MapObject;
import vooga.fighter.model.utils.State;
import vooga.fighter.model.utils.UpdatableLocation;


/**
 * 
 * @author alanni, David Le
 * Loads the data to the map object
 */

public class MapLoader extends ObjectLoader {


	private static final String MAP_PATH = "src/vooga/fighter/config/maps.xml";

	private MapObject myMap;
	/**
	 * Dummy Constructor only to be used when getting map count
	 */
	public MapLoader(){
		super(MAP_PATH);
	}

	public MapLoader (String mapName, MapObject map) {
		super(MAP_PATH);
		myMap = map;
		load(mapName);
	}

	/**
	 * Loads map from xml data
	 */
	public void load(String mapName) {
		Document doc = getDocument();
		NodeList mapNodes = doc.getElementsByTagName(getResourceBundle().getString("Map"));
		
		for (int i = 0; i < mapNodes.getLength(); i++) {
			Element node = (Element) mapNodes.item(i);
			String name = getAttributeValue(node, getResourceBundle().getString("MapName"));
			if (mapName.equals(name)) {
			    //TODO: fix 1 to number of frames later
				State mapState = new State(myMap, 1);
				mapState.populateImage(new Pixmap(getAttributeValue(node, getResourceBundle().getString("MapBackground"))), 0);
				mapState.populateSize(new Dimension(Integer.parseInt(getAttributeValue(node, getResourceBundle().getString("XSize"))),
						Integer.parseInt(getAttributeValue(node, getResourceBundle().getString("YSize")))), 0);
				myMap.addState(getResourceBundle().getString("BackgroundState"), mapState);
				myMap.setLocation(new UpdatableLocation(Integer.parseInt(getAttributeValue(node, getResourceBundle().getString("XSize")))/2,
						Integer.parseInt(getAttributeValue(node, getResourceBundle().getString("YSize")))/2));
				NodeList startingPosNodes= node.getElementsByTagName(getResourceBundle().getString("StartingPosition"));
				addStartingPositions(startingPosNodes);
				//NodeList enviroObjectNodes = node.getElementsByTagName("environmentObject");
				//addEnviroObjects(enviroObjectNodes);
			}
		}
	}
	
	
	public List<String> getMapNames(){
		List maps = new ArrayList<String>();
		Document doc = getDocument();
		NodeList mapNodes = doc.getElementsByTagName(getResourceBundle().getString("Map"));
		for (int i = 0; i < mapNodes.getLength(); i++) {
			Element node = (Element) mapNodes.item(i);
			maps.add(getAttributeValue(node, getResourceBundle().getString("MapName")));
		}
		return maps;		
	}

	/**
	 * 
	 * Adds starting position for the characters
	 */

	private void addStartingPositions(NodeList startingPosNodes) {
		for (int i=0; i<startingPosNodes.getLength(); i++){
			Node startingPosition= startingPosNodes.item(i);
			int xCoord= Integer.parseInt(getAttributeValue(startingPosition, getResourceBundle().getString("XCoordinate")));
			int yCoord= Integer.parseInt(getAttributeValue(startingPosition, getResourceBundle().getString("YCoordinate")));
			myMap.addStartPosition(new UpdatableLocation(xCoord,yCoord));
		}
	}

	/**
	 * Creates environment objects based on XML data
	 */
	private void addEnviroObjects(NodeList enviroObjectNodes) {
		for (int i = 0; i < enviroObjectNodes.getLength(); i++) {
			Node environmentObject = enviroObjectNodes.item(i);
			//String imagePath= getAttributeValue(environmentObject, "image");
			int xCoord= Integer.parseInt(getAttributeValue(environmentObject, "xCoord"));
			int yCoord= Integer.parseInt(getAttributeValue(environmentObject, "yCoord"));
			EnvironmentObject toAdd= new EnvironmentObject();
			toAdd.setLocation(new UpdatableLocation(xCoord, yCoord));
			NodeList stateNodes = ((Element) environmentObject).getElementsByTagName("state");
			addStates(stateNodes, toAdd);
			toAdd.setCurrentState("brick");
			toAdd.setImageData();
		}
	}




}