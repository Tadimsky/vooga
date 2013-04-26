package vooga.scroller.sprites.interfaces;


/**
 * This interface should be implemented by any Sprites that are platforms. Some 
 * platforms will be static, some will be nonstatic. Right now the interface is empty, 
 * as all the methods a platform needs are available in ISprite. However, there 
 * still is value in having this interface because it groups various platforms 
 * together and consolidates their collisions into one visit() method. 
 * 
 * @author Jay Wang
 */
public interface IPlatform extends Locatable {

   
}
