package vooga.scroller.level_editor.library;

import java.awt.Image;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import vooga.scroller.sprites.Sprite;
import vooga.scroller.util.Pixmap;


public abstract class EncapsulatedSpriteLibrary implements ISpriteLibrary{


    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends Sprite>[] getSpritesClasses () {
        return (Class<? extends Sprite>[]) this.getClass().getClasses();
    }
    
    public static Pixmap makePixmap(String directory, String fileName) {
        return new Pixmap(directory, fileName);
    }

}
