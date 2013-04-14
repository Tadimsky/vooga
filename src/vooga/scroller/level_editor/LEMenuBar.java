
package vooga.scroller.level_editor;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JMenu;
import vooga.scroller.viewUtil.MenuBarView;
import vooga.scroller.viewUtil.Window;

/**
 * A specialized menubar for a Level Editor.
 * Note that default File and Edit Menu are inherited from the superclass.
 * @author Dagbedji Fagnisse
 *
 */
public class LEMenuBar extends MenuBarView {

    /**
     * 
     */
    private static final long serialVersionUID = -2714084580594858599L;

    private List<JMenu> myMenus;
    
    /**
     * Attach the menu to the specified window
     * @param window - window which is accessible via this menu
     */
    public LEMenuBar (Window window) {
        super(window);
    }

    @Override
    protected JMenu makePreferencesMenu () {
        // TODO - Add LEPreferences
        JMenu result = new JMenu(Window.getResources().getString("PreferencesMenu"));
        result.setMnemonic(KeyEvent.VK_P);
        result.setEnabled(false);
        return result;
    }

    @Override
    protected JMenu makeHelpMenu () {
        // TODO - Add LEHelp
        JMenu result = new JMenu(Window.getResources().getString("HelpMenu"));
        result.setMnemonic(KeyEvent.VK_H);
        result.setEnabled(true);
        return result;
    }

    @Override
    protected void addCustomMenus () {
        myMenus = new ArrayList<JMenu>();
        addCustomMenu(makePreferencesMenu());
        addCustomMenu(makeHelpMenu());
    }

    private void addCustomMenu (JMenu cm) {
        myMenus.add(cm);
        this.add(cm);
        
    }

    /**
     * Make the Workspace specific menus available to members of the class hierarchy
     */
    @Override
    protected List<JMenu> getWorkspaceMenus () { 
        return myMenus;
    }

}
