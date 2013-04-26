package vooga.towerdefense.gameeditor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Element;
import util.XMLTool;
import vooga.towerdefense.factories.actionfactories.ActionFactory;

public class ActionXMLLoader {
    private static final String ACTIONS_TAG = "Actions";
    private static final String PARAMETER_TAG = "parameter";
    
    private XMLTool myXMLTool;
    
    public ActionXMLLoader(XMLTool xmlTool) {
        myXMLTool = xmlTool;
    }
    
    public List<ActionFactory> loadActions() {
        Element actionsElement = myXMLTool.getElement(ACTIONS_TAG);                
        Map<String, Element> subElements = myXMLTool.getChildrenElementMap(actionsElement);
        List<ActionFactory> actions = new ArrayList<ActionFactory>();
        for (Element e : subElements.values()) {
            actions.add(loadAction(e));
        }
        return actions;
    }
    
    private ActionFactory loadAction(Element actionElement) {
        String actionName = myXMLTool.getContent(actionElement);
        List<String> parameterStrings = new ArrayList<String>();
        Object[] parameterStringsArray = parameterStrings.toArray();
        
        List<ActionFactory> subActions = new ArrayList<ActionFactory>();
        
        Map<String, Element> subElements = myXMLTool.getChildrenElementMap(actionElement); 
        for (String subElementName : subElements.keySet()) {
            if (subElementName.equals(PARAMETER_TAG)) {
                parameterStrings.add(loadParameterString(subElements.get(subElementName)));
            } else {
                subActions.add(loadAction(subElements.get(subElementName)));
            }
        }
        Class actionFactoryClass = null;
        ActionFactory af = null;
        try {
            actionFactoryClass = Class.forName(actionName + "Factory");
            Constructor[] constructors = actionFactoryClass.getDeclaredConstructors();
            Constructor constructor = constructors[0];
            try {
                af = (ActionFactory) constructor.newInstance(parameterStringsArray);
            }
            catch (InstantiationException e) {
                return null;
            }
            catch (IllegalAccessException e) {
                return null;
            }
            catch (IllegalArgumentException e) {
                return null;
            }
            catch (InvocationTargetException e) {
                return null;
            }
        }
        catch (ClassNotFoundException e) {
            return null;
        }        
        af.addFollowUpActionsFactories(subActions);
        return af;
    }
    
    private String loadParameterString(Element parameterElement) {
        return myXMLTool.getContent(parameterElement);
    }
}
