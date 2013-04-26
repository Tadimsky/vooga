package vooga.fighter.model.loaders;

import java.awt.Dimension;
import java.awt.Rectangle;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import util.Pixmap;
import vooga.fighter.model.ModelConstants;
import vooga.fighter.model.objects.MouseObject;
import vooga.fighter.model.utils.State;

public class MouseLoader extends ObjectLoader {

	MouseObject myMouse;
	
	public MouseLoader(MouseObject Mouse, String pathHierarchy) {
		super(ModelConstants.MOUSELOADER_PATH_TAG, pathHierarchy);
		myMouse = Mouse;
		load(ModelConstants.MOUSELOADER_PATH_TAG, pathHierarchy);
	}

	protected void load(String mouse, String pathHierarchy) {
		Document doc = getDocument();
		NodeList menuNodes = doc.getElementsByTagName(getResourceBundle().getString("MouseObject"));
		Element node = (Element) menuNodes.item(0);
		NodeList states = node.getElementsByTagName(getResourceBundle().getString("State"));
		Element state = (Element) states.item(0);
		String Statename = getAttributeValue(state, getResourceBundle().getString("Name"));
		NodeList frames = node.getElementsByTagName(getResourceBundle().getString("Frame"));
		State newState = new State(myMouse, frames.getLength());
		Element node1 = (Element) frames.item(0);
		String imagepathway = getAttributeValue(node1, myMouse.getImageTag()+getResourceBundle().getString("Image"));
		newState.populateImage(new Pixmap(imagepathway), 0);
		int width = Integer.parseInt((getAttributeValue(node1, getResourceBundle().getString("Width"))));
		int height = Integer.parseInt((getAttributeValue(node1, getResourceBundle().getString("Height"))));
		Dimension dim = new Dimension(width,height);
		Rectangle rect = new Rectangle(width,height);
		newState.populateAllSizes(dim);
		newState.populateRectangle(rect, 0);
		myMouse.addState(Statename, newState);
		myMouse.setCurrentState(Statename);
		myMouse.defineDefaultState(Statename);
	}

}
