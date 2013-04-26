package vooga.towerdefense.view.gamescreens;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import vooga.towerdefense.controller.Controller;


/**
 * This class makes a screen that can display any information in it.
 * Examples include information about game stats, units, towers, etc.
 * 
 * @author Angelica Schwartz
 */
public class InformationScreen extends JPanel {

    /**
     * location for the text area.
     */
    public static final String INFO_SCREEN_LOCATION = BorderLayout.CENTER;
    /**
     * default serialized id.
     */
    private static final long serialVersionUID = 1L;
    /**
     * width of the text area.
     */
    private int myWidth;
    /**
     * height of the text area.
     */
    private int myHeight;
    /**
     * text area for the info screen.
     */
    private JTextArea myTextArea;
    /**
     * title of the screen.
     */
    private String myTitle;

    /**
     * Constructor.
     * 
     * @param title
     * @param size
     */
    public InformationScreen (Dimension size, Controller controller) {
        setPreferredSize(size);
        setLayout(new BorderLayout());
        myWidth = size.width;
        myHeight = size.height;
        add(makeInfoTextArea(), INFO_SCREEN_LOCATION);
        setVisible(true);
    }

    /**
     * displays the information contained in the string parameter.
     * 
     * @param information is the string to display
     */
    public void displayInformation (String information) {
        myTextArea.setText(myTitle + information);
        repaint();
    }

    /**
     * clears the text from the information screen.
     */
    public void clearScreen () {
        displayInformation("");
    }

    /**
     * gets the text area for this screen.
     * 
     * @return the text area
     */
    public JTextArea getTextArea () {
        return myTextArea;
    }

    /**
     * paints the InfoScreen.
     * 
     * @param pen
     */
    @Override
    public void paintComponent (Graphics pen) {
        super.paintComponent(pen);
        myTextArea.paint(pen);
    }

    /**
     * creates the text area.
     * 
     * @return the blank JTextArea
     */
    private JComponent makeInfoTextArea () {
        myTextArea = new JTextArea();
        myTextArea.setSize(myWidth, myHeight);
        myTextArea.setEditable(false);
        myTextArea.setText(myTitle);
        return myTextArea;
    }
}
