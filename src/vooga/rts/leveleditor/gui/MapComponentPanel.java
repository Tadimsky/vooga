package vooga.rts.leveleditor.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

/**
 * This class is the superclass for Tile, Terrain and Resouce Panels
 * 
 * @author Patrick
 *
 */

public abstract class MapComponentPanel extends JPanel {
    
    public static final String USER_DIR = "user.dir";
    public static final Dimension DEFAULT_DIMENSION = new Dimension(250, 250);
    public static final String RELATIVE_PATH = "vooga.rts.leveleditor.resource.";

    protected Canvas myCanvas;
    protected JPanel myPanel;
    protected JFileChooser myChooser;
    protected File[] myFiles;
    protected ResourceBundle myResource;
    
    public MapComponentPanel(Canvas canvas) {      
        myCanvas = canvas;
        myPanel = new JPanel();
        myChooser = new JFileChooser(System.getProperties().getProperty(USER_DIR));
        myChooser.setMultiSelectionEnabled(true);
        myPanel.setLayout(new GridLayout(0,4));
        add(myPanel, BorderLayout.NORTH);
        //test import
        add(importButtonPanel(),BorderLayout.SOUTH);
        
        this.setPreferredSize(DEFAULT_DIMENSION);
        setResourceBundle();
        //addButton();
    }
    
    public JPanel importButtonPanel() {
        JPanel p = new JPanel();
        JButton b = new JButton("Import");
        b.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                try {
                    int response = myChooser.showDialog(myPanel, "Import");
                    if (response == JFileChooser.APPROVE_OPTION) {
                        myFiles = myChooser.getSelectedFiles();
                        addButton();
                    }
                }
                catch (Exception exception) {
                    //TODO
                }
                myCanvas.repaint();
            }
        });
        p.add(b);
        return p;
    }

    public Canvas getCanvas() {
        return myCanvas;
     }


    public abstract void setResourceBundle();

    public abstract void addButton(); 
}
