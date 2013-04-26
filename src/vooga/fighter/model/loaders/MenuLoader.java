package vooga.fighter.model.loaders;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import util.Pixmap;
import vooga.fighter.model.ModelConstants;
import vooga.fighter.model.objects.MenuObject;
import vooga.fighter.model.utils.State;


public class MenuLoader extends ObjectLoader {
	
	MenuObject myMenuObject;
	
	public MenuLoader(String menuobjectname, MenuObject menuobject, String pathHierarchy) {
		super(ModelConstants.MENULOADER_PATH_TAG, pathHierarchy);
		myMenuObject = menuobject;
		load(menuobjectname, pathHierarchy);
	}

    protected void load (String menuobjectname, String pathHiearchy) {
        Document doc = getDocument();
        NodeList menuNodes = doc.getElementsByTagName(getResourceBundle().getString("MenuObject"));
        for (int i = 0; i < menuNodes.getLength(); i++) {
            Element node = (Element) menuNodes.item(i);
            String name = getAttributeValue(node, getResourceBundle().getString("MenuObjectName"));
            if (name.equals(menuobjectname)) {
                myMenuObject.setValue(name);
                NodeList states = node.getElementsByTagName(getResourceBundle().getString("State"));
                for (int j = 0; j < states.getLength(); j++) {
                    Element state = (Element) states.item(j);
                    String stateName = getAttributeValue(state, getResourceBundle().getString("Name"));
                    NodeList frames = state.getElementsByTagName(getResourceBundle().getString("Frame"));
                    State newState = new State(myMenuObject, frames.getLength());
                    for (int k = 0; k < frames.getLength(); k++) {
                        Element node1 = (Element) frames.item(k);
                        String imagepathway = getAttributeValue(node1, getResourceBundle().getString("Image"));
                        Pixmap image = new Pixmap(imagepathway);
                        newState.populateImage(image, k);
                    }
                    myMenuObject.addState(stateName, newState);
                    newState.setLooping(true);
                    if (j == 0) {
                        myMenuObject.setCurrentState(stateName);
                        myMenuObject.defineDefaultState(stateName);
                    }
                }

            }
        }
    }
}
