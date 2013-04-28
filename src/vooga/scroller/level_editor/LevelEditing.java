package vooga.scroller.level_editor;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JMenu;
import vooga.scroller.util.mvc.vcFramework.IDomainDescriptor;
import vooga.scroller.util.mvc.vcFramework.Tools;
import vooga.scroller.util.mvc.vcFramework.Window;

public class LevelEditing implements IDomainDescriptor {

    public static final String UPDATE_GRID_SIZE = "Update Grid Size";
    public static final String SIMULATION_ERROR_MESSAGE = "All levels need a start point and a portal";
    public static final String WEB_CONNECTION_PROBLEMS = "Problems connecting with the Web";

    @Override
    public String getDomainName () {
        return VIEW_CONSTANTS.DOMAIN_NAME;
    }

    @Override
    public Tools<LevelEditing> getDomainTools () {
        // TODO Auto-generated method stub
        return null;
    }
    
    public static class VIEW_CONSTANTS {
        public static final double DEFAULT_GRIDVIEW_HEIGHT_RATIO = .95;
        public static final double DEFAULT_GRIDVIEW_WIDTH_RATIO = .7;
        public static final double DEFAULT_TOOLSVIEW_HEIGHT_RATIO = .9;
        public static final double DEFAULT_TOOLSVIEW_WIDTH_RATIO = .25;
        public static final String DOMAIN_NAME = "Level Editor";
    }

    @Override
    public List<JMenu> getDomainSpecificMenus () {
        List<JMenu> menus = new ArrayList<JMenu>();
        menus.add(makeHelpMenu ());
        return menus;
    }
    
    /**
     * This menu handles actions that provide help resources to the user. 
     * @return
     */
    protected JMenu makeHelpMenu () {
        // TODO - Add LEHelp
        JMenu result = new JMenu(Window.getResources().getString("HelpMenu"));
        result.setMnemonic(KeyEvent.VK_H);
        result.setEnabled(true);
        return result;
    }

}
