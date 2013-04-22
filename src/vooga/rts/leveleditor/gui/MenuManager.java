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
                        //myCanvas.getMapPanel().getMyMap().printMatrix();
                        myCanvas.getMapPanel().repaint();    
                    }
                }
                catch (Exception exception) {
                    //TODO
                }
            }
        });

    }


    protected void showCustomizeMapDialog() {
        JTextField MapName = new JTextField();
        JTextField MapDesc = new JTextField();
        JTextField TileWidth = new JTextField();
        JTextField TileHeight = new JTextField();
        JTextField MapWidth = new JTextField();
        JTextField MapHeight = new JTextField();

        Object[] message = {"MapName", MapName, "MapDescription", MapDesc, 
                            "MapWidth", MapWidth, "MapHeight", MapHeight,
                            "TileWidth", TileWidth, "TileHeight", TileHeight};

        int option = JOptionPane.showConfirmDialog(null, message,"Customize your map",JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                String name = MapName.getText();
                String desc = MapDesc.getText();
                int width = Integer.parseInt(MapWidth.getText());
                int height = Integer.parseInt(MapHeight.getText());
                int tileWidth = Integer.parseInt(TileWidth.getText());
                int tileHeight = Integer.parseInt(TileHeight.getText());
                myCanvas.getMapPanel().setWidth(width);
                myCanvas.getMapPanel().setHeight(height);
                myCanvas.getMapPanel().setTileWidth(tileWidth);
                myCanvas.getMapPanel().setTileHeight(tileHeight);
                myCanvas.getMapPanel().initializeMap(name,desc,width,height,tileWidth,tileHeight);
            }
            catch (Exception e1) {
                //TODO
            }
        }
    }

}
