package vooga.towerdefense.view.gamescreens;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import vooga.towerdefense.controller.Controller;


/**
 * Next Wave Screen holds the button to start the next wave.
 * 
 * @author Angelica Schwartz
 */
public class NextWaveScreen extends JPanel {

    /**
     * default serialized id.
     */
    private static final long serialVersionUID = 1L;
    // TODO: get this name from resources
    /**
     * string to display on the button.
     */
    public static final String WAVE_SCREEN_BUTTON_KEYWORD = "NextWaveButtonName";
    /**
     * controller associated with this screen.
     */
    private Controller myController;
    /**
     * button to start the next wave.
     */
    private JButton nextWaveButton;
    /**
     * mouse listener for the button.
     */
    private MouseAdapter myMouseAdapter;

    /**
     * Constructor.
     * 
     * @param size
     * @param controller
     */
    public NextWaveScreen (Dimension size, Controller controller) {
        setPreferredSize(size);
        myController = controller;
        nextWaveButton =
                new JButton(myController.getStringFromResources(WAVE_SCREEN_BUTTON_KEYWORD));
        add(nextWaveButton);
        makeMouseAdapter();
        nextWaveButton.addMouseListener(myMouseAdapter);
        setVisible(true);
    }

    /**
     * helper method to create the mouse adapter.
     */
    private void makeMouseAdapter () {
        myMouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent e) {
                if (e.getSource().equals(nextWaveButton)) {
                    myController.startNextLevel();
                }
            }
        };
    }
}
