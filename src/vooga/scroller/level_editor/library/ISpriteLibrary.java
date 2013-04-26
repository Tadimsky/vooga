package vooga.scroller.level_editor.library;


import vooga.scroller.sprites.Sprite;


/**
 * This interface ensures that the object passed is intended indeed to be a Sprite
 * Library.
 * @author Dagbedji Fagnisse
 *
 */
public interface ISpriteLibrary {

    /**
     * Get a list of classes that can be used in the level editor
     * @return
     */
    Class<? extends Sprite>[] getSpritesClasses();
    
    
}
