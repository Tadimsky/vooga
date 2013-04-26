package vooga.fighter.model;

import java.util.List;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import util.XMLTool;
import vooga.fighter.model.loaders.MapLoader;
import vooga.fighter.model.objects.EnvironmentObject;
import vooga.fighter.model.objects.MapObject;
import vooga.fighter.model.utils.UpdatableLocation;


/**
 * Class used to write out all essential map information to an xml file
 * @author matthewparides
 *
 */
public class MapWriter {
	private MapObject myWriteSource;
	private XMLTool myXMLWriter;
	private Element myRoot;
	//sound in maps has not yet been implemented
	private String mySoundFilePath;
	private List<String> myBackgroundFilePaths;
	
	/**
	 * constructor
	 * @param map
	 * @param soundFilePath
	 * @param backgroundFilePaths
	 */
	public MapWriter(MapObject map, String soundFilePath, List<String> backgroundFilePaths) {
		myWriteSource = map;
		myXMLWriter = new XMLTool();
		myBackgroundFilePaths = backgroundFilePaths;
	}
	
	/**
	 * writes the data in the myWriteSource map to an xml file
	 */
	public void writeMap() {
		myXMLWriter.makeDoc();
		List<UpdatableLocation> startingPos = myWriteSource.getStartPositions();
		List<EnvironmentObject> enviroObjects = myWriteSource.getEnviroObjects();
		writeMapHeader();
		for(UpdatableLocation loc: startingPos) {
			writeStartPos(loc);
		}
		writeStates();
		writeSound();
		for(EnvironmentObject enviro: enviroObjects) {
			writeEnvironmentObject(enviro);
		}
		writeToFile();
	}
	
	/**
	 * writes the starting positions of the map to xml nodes
	 * includes coordinates.
	 * @param loc
	 */
	private void writeStartPos(UpdatableLocation loc) {
		Element elem = myXMLWriter.makeElement("startingPos");
		elem.setAttribute("yCoord", "" + loc.getLocation().getY());
		elem.setAttribute("xCoord", "" + loc.getLocation().getX());
		myXMLWriter.addChild(myRoot, elem);
		
	}
	
	/**
	 * writes the environment objects to xml nodes
	 * environment nodes include coordinate data and the object name
	 * @param enviro - the environmentobject to write
	 */
	private void writeEnvironmentObject(EnvironmentObject enviro) {
		Element elem = myXMLWriter.makeElement("environtmentObject");
		elem.setAttribute("yCoord", "" + enviro.getLocation().getLocation().getY());
		elem.setAttribute("xCoord", "" + enviro.getLocation().getLocation().getX());
		elem.setAttribute("objectName", enviro.getName());
		myXMLWriter.addChild(myRoot, elem);
	}
	
	/**
	 * writes the sound information to xml nodes
	 * currently writes just the filepath
	 */
	private void writeSound() {
		Element soundHead = myXMLWriter.makeElement("sound");
		myXMLWriter.addChild(myRoot, soundHead);
		Element sound = myXMLWriter.makeElement("soundFile");
		sound.setAttribute("sound", mySoundFilePath);
		myXMLWriter.addChild(soundHead, sound);
	}
	
	/**
	 * writes the map header info to an xml node
	 * the map header includes the map name and the dimensions.
	 */
	private void writeMapHeader() {
		myRoot = myXMLWriter.makeRoot("map");
		myRoot.setAttribute("ySize", "871");
		myRoot.setAttribute("xSize", "1024");
		myRoot.setAttribute("mapName", myWriteSource.getName());
	}
	
	/**
	 * writes the image states data to xml nodes
	 */
	private void writeStates() {
		Element stateHead = myXMLWriter.makeElement("state");
		stateHead.setAttribute("stateName", "background");
		myXMLWriter.addChild(myRoot, stateHead);
		for(String str: myBackgroundFilePaths) {
			Element background = myXMLWriter.makeElement("frame");
			background.setAttribute("image", str);
			myXMLWriter.addChild(stateHead, background);
		}
	}
	
	/**
	 * writes the map's generated xml nodes to the map.xml file.
	 * If the map already exists, overwrites that data, if not, adds a new map.
	 */
	private void writeToFile() {
		MapLoader loader = new MapLoader();
		Document doc = loader.getDocument();
		Element root = doc.getDocumentElement();
		NodeList mapNodes = doc.getElementsByTagName("map");
		for (int i = 0; i < mapNodes.getLength(); i++) {
			Element node = (Element) mapNodes.item(i);
			String name = loader.getAttributeValue(node, "mapName");
			if(name.equals(myWriteSource.getName())) {
				node.getParentNode().removeChild(node);
			}
		}
		root.appendChild(myRoot);
	}
}