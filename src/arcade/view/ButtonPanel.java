package arcade.view;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import util.ImageHelper;
import arcade.controller.Controller;
import arcade.view.modes.GameCenterPanel;
import arcade.view.modes.SocialCenterPanel;
import arcade.view.modes.StorePanel;


/**
 * 
 * The panel where the buttons are placed to switch between Arcade Modes.
 * 
 * @author David Liu, Ellango Jothimurugesan, Henrique Moraes
 * 
 */
@SuppressWarnings("serial")
public class ButtonPanel extends JPanel {
    private static final String GAME_CENTER_PICTURE_NAME = "GameCenterIcon.jpg";
    private static final String SOCIAL_CENTER_PICTURE_NAME = "SocialCenterIcon.jpg";
    private static final String GAME_STORE_PICTURE_NAME = "StoreIcon.jpg";
    private static final int BUTTON_SIZE = 140;

    private MainView myMainView;
    private Controller myController;
    private ResourceBundle myResources;
    private JToolBar myToolbar;

    /**
     * Creates a ButtonPanel with the mainView that it can manipulate by changing
     * which mode is showing, a Controller, and a ResourceBundle
     * 
     * @param mainView
     * @param controller
     * @param resources
     */
    public ButtonPanel (MainView mainView, Controller controller, ResourceBundle resources) {
        myMainView = mainView;
        myController = controller;
        myResources = resources;
        setBackground(Color.lightGray);

        myToolbar = new JToolBar(SwingConstants.VERTICAL);
        myToolbar.setOpaque(false);
        myToolbar.setFloatable(false);
        fillToolbar();
        add(myToolbar);
    }

    /**
     * Creates the buttons in the toolbar.
     */
    private void fillToolbar () {
        makeButton(GAME_CENTER_PICTURE_NAME,
                   TextKeywords.GAME_CENTER,
                   new ActionListener() {
                       @Override
                       public void actionPerformed (ActionEvent arg0) {
                           myMainView
                                   .changeViewPanel(new GameCenterPanel(myController, myResources));
                       }
                   });
        makeButton(SOCIAL_CENTER_PICTURE_NAME,
                   TextKeywords.SOCIAL_CENTER,
                   new ActionListener() {
                       @Override
                       public void actionPerformed (ActionEvent arg0) {
                           myMainView.changeViewPanel(new SocialCenterPanel());
                       }
                   });
        makeButton(GAME_STORE_PICTURE_NAME,
                   TextKeywords.GAME_STORE,
                   new ActionListener() {
                       @Override
                       public void actionPerformed (ActionEvent arg0) {
                           myMainView.changeViewPanel(new StorePanel(myController, myResources));
                       }
                   });
    }

    /**
     * Makes a single button with the filename of the image to place on the button,
     * and the ArcadeMode that it changes the MainView to. The button is labeled
     * with the description.
     * 
     * @param imageFilename
     * @param mode
     * @return
     */
    private void makeButton (String imageFilename, String descriptionKey, ActionListener action) {
        ImageIcon icon = new ImageIcon(MainView.IMAGES_DIRECTORY + imageFilename);
        Image scaledImage = ImageHelper.getScaledImage(icon, BUTTON_SIZE);
        JButton button = new JButton(new ImageIcon(scaledImage));
        button.addActionListener(action);
        button.setOpaque(false);
        myToolbar.add(button);
        myToolbar.add(new JLabel("<html><b><font size = 5><font face = champion>"
                                 + myResources.getString(descriptionKey)));
    }

}
