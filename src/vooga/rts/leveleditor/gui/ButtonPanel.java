package vooga.rts.leveleditor.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * This Class holds all the functional Buttons for this level editor
 * Right now it has fill, remove and clearall button
 * 
 * @author Ziqiang Huang
 *
 */

@SuppressWarnings("serial")
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
        JButton fillButton = new JButton("Fill");
        fillButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                myCanvas.getMapPanel().fill();
            }
        });
        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                myCanvas.getMapPanel().setRemoveMode();
            }
        });

        JButton clearButton = new JButton("ClearAll");
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                myCanvas.clear();
            }
        });

        this.add(fillButton);
        this.add(removeButton);
        this.add(clearButton);
    }



}
