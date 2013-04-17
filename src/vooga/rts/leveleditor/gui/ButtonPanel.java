package vooga.rts.leveleditor.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * This Class holds all the functional Buttons for this level editor
 * ZoomIn, ZoomOut, remove, clear
 * 
 * @author Ziqiang Huang
 *
 */

public class ButtonPanel extends JPanel {

    private Canvas myCanvas;

    /**
     * Constructor for the class
     * 
     * @param canvas
     */
    public ButtonPanel(Canvas canvas) {
        myCanvas = canvas;
        this.setPreferredSize(new Dimension(200,70));
        initializeButton();
    }
    /**
     * Initialize the Buttons on this panel
     */

    public void initializeButton() {
        JButton ZoomInButton = new JButton("ZoomIn");
        ZoomInButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                myCanvas.ZoomIn();
            }
        });
        JButton ZoomOutButton = new JButton("ZoomOut");
        ZoomOutButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                myCanvas.ZoomOut();
            }
        });
        JButton RemoveButton = new JButton("Remove");
        RemoveButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                myCanvas.remove(true);
                myCanvas.setMode(MapPanel.RESOURCEMODE);
            }
        });

        JButton ClearButton = new JButton("ClearAll");
        ClearButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                myCanvas.clear();
            }
        });

        this.add(ZoomInButton);
        this.add(ZoomOutButton);
        this.add(RemoveButton);
        this.add(ClearButton);
    }



}
