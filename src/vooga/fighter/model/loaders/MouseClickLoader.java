package vooga.fighter.model.loaders;

import java.awt.Rectangle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import util.Pixmap;
import vooga.fighter.model.ModelConstants;
import vooga.fighter.model.objects.MouseClickObject;
import vooga.fighter.model.utils.State;


public class MouseClickLoader extends ObjectLoader {

    MouseClickObject myMouseClick;

    public MouseClickLoader (MouseClickObject mouseclick, String pathHierarchy) {
        super(ModelConstants.MOUSECLICKLOADERPATH_TAG, pathHierarchy);
        myMouseClick = mouseclick;
        load(ModelConstants.MOUSECLICKLOADERPATH_TAG, pathHierarchy);
    }

    protected void load (String mouseClick, String pathHierarchy) {
        Document doc = getDocument();
        NodeList menuNodes = doc.getElementsByTagName(getResourceBundle().getString("MosueClickObject"));
        for (int i = 0; i < menuNodes.getLength(); i++) {
            Element node = (Element) menuNodes.item(i);
            NodeList states = node.getElementsByTagName(getResourceBundle().getString("State"));
            for (int j = 0; j < states.getLength(); j++) {
                Element state = (Element) states.item(j);
                String Statename = getAttributeValue(state, getResourceBundle().getString("Name"));
                NodeList frames = state.getElementsByTagName(getResourceBundle().getString("Frame"));
                State newState = new State(myMouseClick, frames.getLength());
                for (int k = 0; k < frames.getLength(); k++) {
                    Element node1 = (Element) frames.item(k);
                    String imagepathway = getAttributeValue(node1, getResourceBundle().getString("Image"));
                    newState.populateImage(new Pixmap(imagepathway), k);
                    int width = Integer.parseInt((getAttributeValue(node1, getResourceBundle().getString("Width"))));
                    int height = Integer.parseInt((getAttributeValue(node1, getResourceBundle().getString("Height"))));
                    Rectangle rect = new Rectangle(width, height);
                    newState.populateRectangle(rect, k);
                    myMouseClick.addState(Statename, newState);
                }
                if (j == 0) {
                    myMouseClick.defineDefaultState(Statename);
                    myMouseClick.setCurrentState(Statename);
                }
            }
        }
    }
}
