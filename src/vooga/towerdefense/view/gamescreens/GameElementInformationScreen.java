package vooga.towerdefense.view.gamescreens;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import vooga.towerdefense.controller.Controller;


/**
 * This screen is specifically designed to display game element
 * information, including an option to upgrade if applicable.
 * 
 * @author Angelica Schwartz
 */
public class GameElementInformationScreen extends InformationScreen {

    // TODO: load from resources
    private static final String UPGRADE_BUTTON_NAME = "Upgrade";

    /**
     * default serialized id.
     */
    private static final long serialVersionUID = 1L;
    /**
     * button to click to upgrade a game element.
     */
    private JButton myUpgradeButton;
    /**
     * drop down list to show upgrade options.
     */
    private JComboBox myUpgradeOptionsBox;
    /**
     * holds the upgrade button & options box.
     */
    private JPanel myUpgradeSection;
    /**
     * mouselistener for click on a button.
     */
    private MouseAdapter myMouseAdapter;
    /**
     * controller needed to handle upgrade information.
     */
    private Controller myController;

    /**
     * constructor.
     * 
     * @param title
     * @param size
     * @param controller
     */
    public GameElementInformationScreen (Dimension size, Controller controller) {
        super(size, controller);
        myController = controller;
        add(getTextArea(), InformationScreen.INFO_SCREEN_LOCATION);
        makeMouseAdapter();
        setUpUpgradeSection();
    }

    /**
     * displays the upgrade options in a drop down menu
     * and the upgrade button.
     * 
     * @param upgrades is the list of upgrades as strings
     */
    public void displayUpgradesAndButton (List<String> upgrades) {
        myUpgradeOptionsBox.removeAllItems();
        for (String upgrade : upgrades) {
            myUpgradeOptionsBox.addItem(upgrade);
        }
        myUpgradeSection.setVisible(true);
    }

    /**
     * Overrides the clear screen to get rid of the
     * element information, and the upgrade information.
     */
    @Override
    public void clearScreen () {
        super.clearScreen();
        myUpgradeSection.setVisible(false);
    }

    /**
     * helper method to create the mouse listener.
     */
    private void makeMouseAdapter () {
        myMouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent e) {
                if (e.getSource().equals(myUpgradeButton) && myUpgradeButton.isVisible()) {
                    String upgrade = (String) myUpgradeOptionsBox.getSelectedItem();
                    myController.upgradeSelectedItemTo(upgrade);
                }
            }
        };
    }

    /**
     * helper method to set up the upgrade button
     * and drop down box with upgrade options.
     */
    private void setUpUpgradeSection () {
        myUpgradeSection = new JPanel();
        myUpgradeButton = new JButton(UPGRADE_BUTTON_NAME);
        myUpgradeButton.addMouseListener(myMouseAdapter);
        myUpgradeOptionsBox = new JComboBox();
        myUpgradeSection.setLayout(new BorderLayout());
        myUpgradeSection.add(myUpgradeOptionsBox, BorderLayout.CENTER);
        myUpgradeSection.add(myUpgradeButton, BorderLayout.SOUTH);
        myUpgradeSection.setVisible(false);
        this.add(myUpgradeSection, BorderLayout.SOUTH);
    }

}
