package vooga.fighter.model.loaders;

import java.awt.Rectangle;
import java.io.File;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import util.Pixmap;
import vooga.fighter.model.objects.GameObject;
import vooga.fighter.model.utils.State;

/**
 * Abstract class with shared methods that all ObjectLoader-derived classes may need.
 * 
 * @author Dayvid, alanni
 *
 */
public abstract class ObjectLoader {


    private String delimiter= ",";
	private String RESOURCE_PATH = "config.objects";
	private String RESOURCE_DEFAULT_VALUES_PATH="config.defaultvalues";
	private String RESOURCE_PROPERTIES_PATH="config.propertyfields";
	private File myObjectFile;
	private Document myDocument;
	private ResourceBundle myResources;
	private ResourceBundle myDefaults; 
    private ResourceBundle myProperties;  

	/**
	 * Points to the xml file that the loader will be parsing
	 * 
	 * @param objectPath
	 */
	public ObjectLoader (String pathName, String pathHierarchy) {
		setResourcePaths(pathHierarchy); 
		String objectPath = myResources.getString(pathName);
		myObjectFile = new File(objectPath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			myDocument = dBuilder.parse(myObjectFile);
			myDocument.getDocumentElement().normalize();
		} catch (Exception e) {
			myDocument = null;
			System.err.println("Parsing error/file not found for " + getClass().getSimpleName());
		}
	}
	
	/**
	 * Sets the resource paths for the object loader
	 */
	private void setResourcePaths(String pathHierarchy){
		myResources = ResourceBundle.getBundle(pathHierarchy+RESOURCE_PATH);
		myDefaults= ResourceBundle.getBundle(pathHierarchy+RESOURCE_DEFAULT_VALUES_PATH);
		myProperties= ResourceBundle.getBundle(pathHierarchy+RESOURCE_PROPERTIES_PATH);
	}

	/**
	 * Loads object based on the name given
	 * @param name
	 */
	protected abstract void load(String name, String pathHierarchy);
	

	/**
	 * Returns the xml document which the loader points to
	 * @return
	 */
	public Document getDocument() {
		return myDocument;
	}

	/**
	 * Returns resource bundle which contains what the names of nodes in the xml files should be.
	 */
	protected ResourceBundle getResourceBundle() {
		return myResources;
	}

	/**
	 * Returns the string attribute value of the specified tag for the specified mode.
	 * @param node
	 * @param tag
	 * @return
	 */
	public String getAttributeValue(Node node, String tag) {
		try{
			String value= node.getAttributes().getNamedItem(tag).getTextContent();
			return value; 
		}
		catch(NullPointerException e){
			String value= myDefaults.getString(getClass().getSimpleName()+tag);
			System.err.println("Property "+ tag+ " not set.  Default value is "+value);
			return value;
		}
	}

	/**
	 * Returns the value of the child node with the specified tag for the element.
	 * @param tag
	 * @param element
	 * @return
	 */
	protected String getChildValue(String tag, Element element) {
		NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodes.item(0);
		return node.getNodeValue();
	}

	/**
	 * Loads and adds states for the GameObject.
	 * @param stateNodes
	 * @param myObject
	 */
	protected void addStates(NodeList stateNodes, GameObject myObject) {
		for (int i = 0; i < stateNodes.getLength(); i++) {
			Element state = (Element) stateNodes.item(i);
			String stateName = getAttributeValue(stateNodes.item(i), getResourceBundle().getString("StateName"));
			NodeList frameNodes = state.getElementsByTagName(getResourceBundle().getString("Frame"));
			State newState = new State(myObject, frameNodes.getLength());
			getFrameProperties(frameNodes, newState);
			myObject.addState(stateName, newState);
		}
	}

	/**
	 * Loads frames and states for the objects 
	 */
	protected void getFrameProperties(NodeList frameNodes, State newState){
		for (int j = 0; j < frameNodes.getLength(); j++) {
			Element frame = (Element) frameNodes.item(j);
			if (frame.getAttributes().getNamedItem(getResourceBundle().getString("Image")) != null){
				newState.populateImage(new Pixmap(getAttributeValue(frame, getResourceBundle().getString("Image"))), j);
			} else {
				newState.populateImage(new Pixmap(getResourceBundle().getString("Blank")), j);
			}
			NodeList hitboxNodes = frame.getElementsByTagName(getResourceBundle().getString("Hitbox")); 
			for (int k=0; k<hitboxNodes.getLength(); k++){
				newState.populateRectangle(new Rectangle(Integer.parseInt(getAttributeValue(hitboxNodes.item(k), getResourceBundle().getString("CornerX"))),
						Integer.parseInt(getAttributeValue(hitboxNodes.item(k), getResourceBundle().getString("CornerY"))), Integer.parseInt(getAttributeValue(hitboxNodes.item(k), getResourceBundle().getString("Width"))),
						Integer.parseInt(getAttributeValue(hitboxNodes.item(k), getResourceBundle().getString("Height")))), j);
			}
		}
	}
	
	/**
	 *  return array of desired properties that will be loaded into the character
	 */
	protected String [] getProperties(){
		return myProperties.getString(getClass().getSimpleName()).split(delimiter);
	}
	
	/**
	 * Loops through list of properties and adds property values for gameobject
	 */
	protected void addProperties(Node node, GameObject object){
		for (String property: getProperties()){
			addPropertyValue(node, property, object);
		}
	}
	
	/**
	 * Matches appropriate value from xml with 
	 */
	protected void addPropertyValue(Node node, String property, GameObject object){
		int propertyValue= Integer.parseInt(getAttributeValue(node, property));
		object.addProperty(property, propertyValue);
	}
}

