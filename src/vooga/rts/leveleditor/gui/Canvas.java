package vooga.rts.leveleditor.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import vooga.rts.leveleditor.components.Resource;

/**
 * 
 * This class holds all the visible components. 
 * It will be roughly divided into two parts - the left being the EditableMap, 
 * which visualizes the map the designer is creating, 
 * the right being all the resources (e.g. Trees) available to be put on the map. 
 * It will also contains a menu bar that allows user to save and load map
 * 
 * @author Ziqiang Huang
 *
 */
public class Canvas extends JFrame {

    public static final Dimension DEFAULT_CANVAS_SIZE  = new Dimension (800,600);
    public static final String USER_DIR = "user.dir";

    private MapPanel myMapPanel;
    private ResourcePanel myResourcePanel;
    private ButtonPanel myButtonPanel;
    private MenuManager myMenuManager;
    private Resource myCurrentSelectResource;
    private JScrollPane  myMapScroll;

    public Canvas() {
        setTitle("Level Editor");
        myMapPanel = new MapPanel(this);
        myResourcePanel = new ResourcePanel(this);
        myButtonPanel = new ButtonPanel(this);
        myMenuManager = new MenuManager(this);

        JPanel ChooserPanel = new JPanel(new BorderLayout());        
        JScrollPane resourceScroll = new JScrollPane(myResourcePanel);
        JTabbedPane ResourceTabPane = new JTabbedPane();
        ResourceTabPane.add("Resources", resourceScroll);
        ChooserPanel.add(ResourceTabPane, BorderLayout.CENTER);
        ChooserPanel.add(myButtonPanel, BorderLayout.SOUTH);

        myMapScroll = new JScrollPane(myMapPanel);
        getContentPane().add(myMapScroll, BorderLayout.CENTER);
        getContentPane().add(ChooserPanel, BorderLayout.EAST);

        setJMenuBar(myMenuManager);

        setPreferredSize(DEFAULT_CANVAS_SIZE);
        pack();
        setVisible(true);

    }


    public void setCurrentSelectResource(Resource r) {
        myCurrentSelectResource = r;
    }

    public Resource getCurrentSelectResource() {
        return myCurrentSelectResource;
    }

    public void ZoomIn() {
        myMapPanel.ZoomIn();      
    }

    public void ZoomOut() {
        myMapPanel.ZoomOut();      
    }

    public void clear() {
        myMapPanel.clear();

    }

    public void remove(boolean b) {
        myMapPanel.setRemoveFlag(b);       
    }

    public MapPanel getMapPanel() {
        return myMapPanel;
    }
    
    public void setMode(int mode) {
       myMapPanel.setMode(mode);       
    }
    
    public static void main(String[] argv) {
        new Canvas();
    }



}
