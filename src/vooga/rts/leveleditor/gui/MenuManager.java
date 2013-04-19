package vooga.rts.leveleditor.gui;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * This class holds all the menus for the level editor
 * 
 * @author Ziqiang Huang
 *
 */
public class MenuManager extends JMenuBar {

    public static final String USER_DIR = "user.dir";
    private Canvas myCanvas;
    private JFileChooser myChooser;

    public MenuManager(Canvas canvas) {
        myCanvas = canvas;
        myChooser = new JFileChooser(System.getProperties().getProperty(USER_DIR));
        this.add(createFileMenu());
        this.add(createPlayerMenu());
        this.add(createLayerMenu());

    }

    private JMenu createLayerMenu() {
        JMenu menu = new JMenu("Layer");
        createLayerMenu(menu);
        return menu;
    }


    private JMenu createPlayerMenu() {
        JMenu menu = new JMenu("Player");
        createPlayerMenu(menu);
        return menu;
    }

    private JMenu createFileMenu() {
        JMenu menu = new JMenu("File");
        createFileMenu(menu);
        return menu;
    }

    private void createLayerMenu(JMenu menu) {
        menu.add(new AbstractAction("AddLayer") {
            @Override
            public void actionPerformed (ActionEvent e) {
                myCanvas.addLayer();
            }
        });
        
        menu.add(new AbstractAction("Remove") {
            @Override
            public void actionPerformed (ActionEvent e) {
                myCanvas.removeLayer();
            }
        });

    }
    
    private void createPlayerMenu(JMenu menu) {
        menu.add(new AbstractAction("AddPlayer") {
            @Override
            public void actionPerformed (ActionEvent e) {
               myCanvas.setMode(MapPanel.PLAYERMODE);
               myCanvas.getMapPanel().setRemoveFlag(false);
            }
        });

        menu.add(new AbstractAction("RemovePlayer") {
            @Override
            public void actionPerformed (ActionEvent e) {
                myCanvas.setMode(MapPanel.PLAYERMODE);
                myCanvas.getMapPanel().setRemoveFlag(true);
            }
        });
    }

    private void createFileMenu(JMenu menu) {

        menu.add(new AbstractAction("New") {
            @Override
            public void actionPerformed (ActionEvent e) {
                showCustomizeMapDialog();
            }
        });

        menu.add(new AbstractAction("Save") {
            @Override
            public void actionPerformed (ActionEvent e) {
                try {
                    int response = myChooser.showSaveDialog(null);
                    if (response == JFileChooser.APPROVE_OPTION) {

                        myCanvas.getMapPanel().getMyMap().save(myChooser.getSelectedFile());
                    }
                }
                catch (Exception exception) {
                    //TODO
                }
            }
        });

        menu.add(new AbstractAction("Load") {

            @Override
            public void actionPerformed (ActionEvent e) {
                try {
                    int response = myChooser.showOpenDialog(null);
                    if (response == JFileChooser.APPROVE_OPTION) {
                        myCanvas.getMapPanel().getMyMap().load(myChooser.getSelectedFile());
                        myCanvas.getMapPanel().getMyMap().printMatrix();
                        
                        myCanvas.getMapPanel().setWidth(myCanvas.getMapPanel().getMyMap().getWidth());
                        myCanvas.getMapPanel().setHeight(myCanvas.getMapPanel().getMyMap().getHeight());
                        myCanvas.getMapPanel().setTileWidth(MapPanel.DEFAULT_TILE_WIDTH);
                        myCanvas.getMapPanel().setTileHeight(MapPanel.DEFAULT_TILE_HEIGHT);
                    }
                }
                catch (Exception exception) {
                    //TODO
                }
            }
        });

    }


    protected void showCustomizeMapDialog() {
        JTextField MapWidth = new JTextField();
        JTextField MapHeight = new JTextField();

        Object[] message = {"MapWidth", MapWidth, "MapHeight", MapHeight};

        int option = JOptionPane.showConfirmDialog(null, message,"Set Map Size",JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                int w = Integer.parseInt(MapWidth.getText());
                int h = Integer.parseInt(MapHeight.getText());
                myCanvas.getMapPanel().setWidth(w);
                myCanvas.getMapPanel().setHeight(h);
                myCanvas.getMapPanel().setTileWidth(MapPanel.DEFAULT_TILE_WIDTH);
                myCanvas.getMapPanel().setTileHeight(MapPanel.DEFAULT_TILE_HEIGHT);
                myCanvas.getMapPanel().initializeMap(w,h);
            }
            catch (Exception e1) {
                //TODO
            }
        }
    }

}
