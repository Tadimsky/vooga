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
	Factory myFactory;
	
	public abstract <T extends Object> T create(Document doc) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;
}
