package vooga.rts.leveleditor.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import vooga.rts.leveleditor.components.EditableTerrain;
import vooga.rts.leveleditor.components.EditableTile;
import vooga.rts.leveleditor.components.EditableResource;

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
@SuppressWarnings("serial")
public class Canvas extends JFrame {

    public static final Dimension DEFAULT_CANVAS_SIZE  = new Dimension (800,600);
    public static final String USER_DIR = "user.dir";

    private MapPanel myMapPanel;
    private ResourcePanel myResourcePanel;
    private TilePanel myTilePanel;
    private TerrainPanel myTerrainPanel;
    private LayerPanel myLayerPanel;
    private ButtonPanel myButtonPanel;
    private MenuManager myMenuManager;
    private EditableResource myCurrentSelectResource;
    private EditableTerrain myCurrentSelectTerrain;
    private EditableTile myCurrentSelectTile;
    private JScrollPane  myMapScroll;

    /**
     * Constructor for the class
     */
    public Canvas() {
        setTitle("Level Editor");
        myMapPanel = new MapPanel(this);
        myResourcePanel = new ResourcePanel(this);
        myTerrainPanel = new TerrainPanel(this);
        myTilePanel = new TilePanel(this);
        myLayerPanel = new LayerPanel(this);
        myButtonPanel = new ButtonPanel(this);
        myMenuManager = new MenuManager(this);

        JPanel ChooserPanel = new JPanel(new BorderLayout());        
        JScrollPane resourceScroll = new JScrollPane(myResourcePanel);
        JScrollPane terrainScroll = new JScrollPane(myTerrainPanel);
        JScrollPane tileScroll = new JScrollPane(myTilePanel);
        JTabbedPane MapComponentTabPane = new JTabbedPane();
        MapComponentTabPane.add("Tiles", tileScroll);
        MapComponentTabPane.add("Terrains", terrainScroll);
        MapComponentTabPane.add("Resources", resourceScroll);
        JTabbedPane LayerTabPane = new JTabbedPane();
        JScrollPane layerScroll = new JScrollPane(myLayerPanel);
        LayerTabPane.add("Layers",layerScroll);
        ChooserPanel.add(MapComponentTabPane, BorderLayout.NORTH);
        ChooserPanel.add(LayerTabPane, BorderLayout.CENTER);
        ChooserPanel.add(myButtonPanel, BorderLayout.SOUTH);

        myMapScroll = new JScrollPane(myMapPanel);
        getContentPane().add(myMapScroll, BorderLayout.CENTER);
        getContentPane().add(ChooserPanel, BorderLayout.EAST);

        setJMenuBar(myMenuManager);

        setPreferredSize(DEFAULT_CANVAS_SIZE);
        pack();
        setVisible(true);

    }

    /**
     * Set r as the current selected resource selected by user
     * @param r
     */
    public void setCurrentSelectResource(EditableResource r) {
        myCurrentSelectResource = r;
    }
    
    /**
     * 
     * @return the current selected resource
     */
    public EditableResource getCurrentSelectResource() {
        return myCurrentSelectResource;
    }
    
    /**
     * Set t as the current selected terrain selected by user
     * @param t
     */
    public void setCurrentSelectTerrain(EditableTerrain t) {
        myCurrentSelectTerrain = t;        
    }
    
    /**
     * 
     * @return the current selected terrain
     */
    public EditableTerrain getCurrentSelectTerrain() {
        return myCurrentSelectTerrain;
    }
    
    /**
     * Set t as the current selected tile
     * 
     * @param myTile
     */
    public void setCurrentSelectTile(EditableTile t) {
        myCurrentSelectTile = t;       
    }
    
    /**
     * 
     * @return the current selected tile
     */
    public EditableTile getCurrentSelectTile() {
        return myCurrentSelectTile;
    }

    /**
     * clear the canvas
     */
    public void clear() {
        myMapPanel.clear();
        myLayerPanel.clear();

    }

    /**
     * 
     * @return the map panel for the canvas
     */
    public MapPanel getMapPanel() {
        return myMapPanel;
    }
    
    /**
     * Set the edit mode according to the mode 
     * @param mode
     */
    public void setMode(int mode) {
       myMapPanel.setMode(mode);       
    }
    
    /**
     * add a layer to the canvas
     */
    public void addLayer() {
       myMapPanel.addLayer();
       myLayerPanel.addLayer(myMapPanel.getMaxLayer());
       
    }
    
    /**
     * remove the last layer from the canvas
     */
    public void removeLayer() {
       myLayerPanel.removeLayer(myMapPanel.getMaxLayer());
       myMapPanel.removeLayer(); 
    }

    /**
     * remove all the players from the map
     */
    public void clearPlayer() {
        myMapPanel.getMyMap().getPlayerSet().clearPlayers();
        myMapPanel.repaint();
    }


}
