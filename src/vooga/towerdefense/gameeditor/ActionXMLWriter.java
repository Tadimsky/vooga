package vooga.towerdefense.gameeditor;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Element;
import util.XMLTool;


/**
 * This class is responsible for reading in a data string containing information 
 * about actions, their parameters, and their sub-actions. The class reads this
 * string, and then can return a root Element for an xml file.
 * 
 * An example of the data string can be shown below:
 * 
 * Action1 param1 param2
 *      Action3 param1 param2 param3
 *              Action4 param1
 *      Action5 param1
 * Action2 param1 param2 param3 param4
 * 
 * This data string is then converted to the following XML.
 * 
 * <actions>
 *      <action1>
 *              <parameter>param1</parameter>
 *              <parameter>param2</parameter>
 *              <action3>
 *                      <parameter>param1</parameter>
 *                      <parameter>param2</parameter>
 *                      <parameter>param3</parameter>
 *                      <action4>
 *                              <parameter>param1</parameter>
 *                      </action4>
 *              </action3>
 *              <action5>
 *                      <parameter>param1</parameter>
 *              </action5>      
 *      </action1>
 *      <action2>
 *              <parameter>param1</parameter>
 *              <parameter>param2</parameter>
 *              <parameter>param3</parameter>
 *              <parameter>param4</parameter>
 *      </action2>
 * </actions>
 * 
 * @author Erick Gonzalez
 */
public class ActionXMLWriter {
    private static final String PARAM_TAG = "parameter";
    private static final char TAB = '\t';
    private static final String WHITESPACE_REGEX = "\\s+";

    private int myIndex;
    private XMLTool myXMLTool;

    /**
     * 
     * @param xmlTool an xml tool containing a document being written to
     */
    public ActionXMLWriter (XMLTool xmlTool) {
        myIndex = 0;
        myXMLTool = xmlTool;
    }

    /**
     * 
     * @param rootElement the root XML element
     * @param dataString the string of actions
     * @return the rootElement, now containing all its appropriate subElements.
     */
    public Element parse (Element rootElement, String dataString) {
        String[] dataArray = dataString.split("\n");

        while (myIndex < dataArray.length) {
            myXMLTool.addChild(rootElement, parse(dataArray, 0));
        }
        return rootElement;
    }

    private Element parse (String[] dataArray, int numTabs) {
        String actionString = dataArray[myIndex++];
        String actionName = getActionName(actionString);
        List<String> params = getParams(actionString);
        
        Element currentElement = myXMLTool.makeElement(actionName);

        for (String param : params) {
            Element parameterElement = myXMLTool.makeElement(PARAM_TAG, param);
            myXMLTool.addChild(currentElement, parameterElement);
        }
        while (myIndex < dataArray.length) {
            String next = dataArray[myIndex];
            if (countTabs(next) > numTabs) {
                Element child = parse(dataArray, numTabs + 1);
                myXMLTool.addChild(currentElement, child);
            }
            else {
                break;
            }
        }
        return currentElement;
    }

    private String getActionName (String actionString) {
        String[] actionSplitString = actionString.trim().split(WHITESPACE_REGEX);
        return actionSplitString[0];
    }

    private List<String> getParams (String actionString) {
        String[] actionSplitString = actionString.trim().split(WHITESPACE_REGEX);
        List<String> params = new ArrayList<String>();
        for (int i = 1; i < actionSplitString.length; ++i) {
            params.add(actionSplitString[i]);
        }
        return params;
    }

    private int countTabs (String s) {
        int count = 0;
        for (int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) == TAB) {
                count++;
            }
        }
        return count;
    }
}
