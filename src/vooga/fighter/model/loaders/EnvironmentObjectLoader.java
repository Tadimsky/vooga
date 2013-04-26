package vooga.fighter.model.loaders;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import vooga.fighter.model.ModelConstants;
import vooga.fighter.model.objects.EnvironmentObject;

/**
 * Loads data associated with a environment object to be passed to EnvironmentObject.
 * 
 * @author alanni, David Le
 *
 *
 */
public class EnvironmentObjectLoader extends ObjectLoader {
	
	private EnvironmentObject myEnvironmentObject;

	/**
	 * Constructor used when trying to read the environment objects data directly such as wanting to retrieve
	 * all environment objects.
	 */
	public EnvironmentObjectLoader (String pathHierarchy) {
		super(ModelConstants.ENVIRONMENTLOADER_PATH_TAG, pathHierarchy);
	}
	
	/**
	 * Constructs the environment object loader with the id to be loaded and the environment object which the
	 * loader will modify.
	 */
	public EnvironmentObjectLoader (String enviroObjectName, EnvironmentObject enviroObject, String pathHierarchy) {
		super(ModelConstants.ENVIRONMENTLOADER_PATH_TAG, pathHierarchy);
		myEnvironmentObject = enviroObject;
		load(enviroObjectName, pathHierarchy); 
	}
	
	/**
	 * Retrieves information of all environment objects; used for level editor.
	 */
	public List<EnvironmentObject> getEnvironmentObjects(String pathHierarchy) {
		Document doc = getDocument();
		List<EnvironmentObject> allEnviroObjects = new ArrayList<EnvironmentObject>();
		NodeList enviroObjectNodes = doc.getElementsByTagName(getResourceBundle().getString("EnvironmentObject"));
		for (int i = 0; i < enviroObjectNodes.getLength(); i++) {
			Node enviroObjectNode = enviroObjectNodes.item(i);
			String name = getAttributeValue(enviroObjectNode, getResourceBundle().getString("EnvironmentObjectName"));
			EnvironmentObject newEnvironmentObject= new EnvironmentObject(name, pathHierarchy);
			allEnviroObjects.add(newEnvironmentObject);
		}
		return allEnviroObjects;
	}
	
	/**
	 * Loads the environment object associated with the id
	 */
	protected void load(String enviroObjectName, String pathHierarchy) {
		Document doc = getDocument();
		NodeList enviroObjectNodes = doc.getElementsByTagName(getResourceBundle().getString("EnvironmentObject"));
		for (int i = 0; i < enviroObjectNodes.getLength(); i++) {
			Node enviroObjectNode = enviroObjectNodes.item(i);
			String name = getAttributeValue(enviroObjectNode, getResourceBundle().getString("EnvironmentObjectName"));
			if (enviroObjectName.equals(name)) {
				NodeList stateNodes = ((Element) enviroObjectNode).getElementsByTagName(getResourceBundle().getString("State"));
				addStates(stateNodes, myEnvironmentObject);
				myEnvironmentObject.defineDefaultState(getAttributeValue(enviroObjectNode, getResourceBundle().getString("Default")));
			}
		}
	}
}
