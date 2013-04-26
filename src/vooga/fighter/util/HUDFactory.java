package vooga.fighter.util;

import java.util.List;
import java.util.ArrayList;
import java.util.Observable;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import vooga.fighter.view.HUDElement;


public class HUDFactory {
    /**
     * Generate a list of HUDElements based off of annotations of member variables in the
     *  given Observable object. Assumes vooga.fighter.view package.
     * 
     * @param gameObject The Observable object with potentially annotated member variables.
     * @return A List of HUDElements
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     */
    public static List<HUDElement> getHUDElements (Observable gameObject)
                                                                         throws InstantiationException,
                                                                         IllegalAccessException,
                                                                         ClassNotFoundException {
        return getHUDElements(gameObject, "vooga.fighter.view");
    }
    
    /**
     * Generate a list of HUDElements based off of annotations of member variables in the
     *  given Observable object.
     * 
     * @param gameObject The Observable object with potentially annotated member variables.
     * @param packageName The name of the package with the HUDElements in use.
     * @return A List of HUDElements
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     */
    public static List<HUDElement> getHUDElements (Observable gameObject, String packageName)
                                                                         throws InstantiationException,
                                                                         IllegalAccessException,
                                                                         ClassNotFoundException {
        @SuppressWarnings("rawtypes")
        Class objectClass = gameObject.getClass();

        ArrayList<HUDElement> elements = new ArrayList<HUDElement>();

        for (Field member : objectClass.getDeclaredFields()) {
            for (Annotation a : member.getAnnotations()) {
                if (a instanceof HUDVariable) {
                    HUDVariable varAnnotation = (HUDVariable) a;
                    member.setAccessible(true);
                    if (member.get(gameObject) == null) {
                        continue;
                    }
                    String subclass = packageName + ".HUD" + varAnnotation.HUDElementClass();
                    HUDElement newElement = (HUDElement) Class.forName(subclass).newInstance();
                    newElement.setName(varAnnotation.name());
                    newElement.setObservedValue(member.getName());
                    gameObject.addObserver(newElement);
                    elements.add(newElement);
                    newElement.update(gameObject, null);
                }
            }
        }

        return elements;
    }
}
