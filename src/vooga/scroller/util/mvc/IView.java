
package vooga.scroller.util.mvc;

import java.awt.Dimension;
import javax.swing.JMenu;
import vooga.scroller.util.Renderer;
import vooga.scroller.util.mvc.vcFramework.IDomainDescriptor;


/**
 * This interface defines the minimum behavior a component need to exhibit to qualify as a view.
 * <li>First, it needs to occupy space, a.k.a have a size.</li>
 * <li>Second, it needs to be able to render valid objects </li>
 * (in most cases, the objects will be responsible for the ultimate rendering implementation).
 * <li>Third, it needs to be able to accept and <strong>process</strong> requests 
 * generated by a user.</li>
 * @param <D> - The domain descriptor
 * @author Dagbedji Fagnisse
 *
 */
public interface IView<D extends IDomainDescriptor> 
                         extends Renderer<D> {

    /**
     * Process a String representing a command.
     * 
     * @param command - unprocessed object, ideally a string.
     */
    public void process(Object command);
    
    
    /**
     * Provides the size of this view entity. All views are supposed to occupy
     * some space.
     */
    public Dimension getSize();


    /**
     * To enforce language hierarchy/translation
     * @param string - string identifier (to be looked up in resource file)
     * @return
     */
    public String getLiteral (String string);


    /**
     * Register a menu for a menu (ideally to the top-level container)
     * @param jMenu - menu to be registered
     */
    public void registerMenu (JMenu jMenu);


    /**
     * Display a dialog box on top of this view
     * @param message
     */
    public void showMessageDialog (String message);

}
