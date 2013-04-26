
package vooga.scroller.level_editor;

import java.awt.Image;
import java.util.Map;
import javax.swing.Icon;
import vooga.scroller.sprites.Sprite;
import vooga.scroller.util.Editable;
import vooga.scroller.util.IBackgroundView;

/**
 * Interface between a LevelEditor model and a Level Editor view.
 * This interface defines the behavior that any external component should
 * expect from a Level Editor entity (domain-specific).
 * 
 *  
 * Initial implementation: View can only pass a command to the LevelEditor.
 * 
 * @author Danny Goodman, Dagbedji Fagnisse
 *
 */
public interface ILevelEditor {
    

    public static final int START_ID = 0;

    /**
     * This is the generalized method call for a client to send a LevelEditor
     * command to a LevelEditor model for processing.
     * @param m - An Editable (level) on which the LevelEditor is expected to implement the command.
     * @param cmd - The command the LevelEditor model is expected to process.
     */
    public void processCommand (Editable m, String cmd);

    /**
     * Set the SpriteMap to be used when processing commands
     * @param spriteMap - a map of sprites to be used when decoding instructions to be processed.
     */
    public void setSpriteMap (Map<Integer, Sprite> spriteMap);

    void setBackgroundMap (Map<Integer, IBackgroundView> map);
    
}
