package vooga.rts.gamedesign.factories;

import java.lang.reflect.InvocationTargetException;

import org.w3c.dom.Document;

/**
 * 
 * @author Ryan Fishel
 * @author Kevin Oh
 * @author Francesco Agosti
 * @author Wenshun Liu
 * 
 * This is the super class for all decoders.
 *
 */
public abstract class Decoder {

	
	
	/**
	 * Depending on the decoder, this method takes in the document, parses it and instantiates
	 * objects based on values given in the XML file. If objects also have dependencies, such as Buildings
	 * that produce units and have strategies, it records these dependencies so that they can be set-up later
	 * (since dependencies exist between two objects that both need to be instantiated). This allows us to define
	 * game elements in the XML file in any order (as it should be). 
	 * @param doc
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 */
	public abstract void create(Document doc) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException;
}
