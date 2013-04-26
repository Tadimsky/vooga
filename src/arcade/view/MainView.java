package arcade.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ResourceBundle;
import javax.swing.JFrame;
import javax.swing.JPanel;
import arcade.controller.Controller;
import arcade.view.forms.ScoresView;
import arcade.view.modes.GameCenterPanel;


/**
 * The main view of the arcade. Main view is triggered after the user 
 * successfully logged in. 
 * 
 * Users can access different modes from here by selecting different buttons.  
 * 
 * @author David Liu, Ellango Jothimurugesan
 * 
 */
@SuppressWarnings("serial")
public class MainView extends JFrame {
	

    public static final String IMAGES_DIRECTORY = System.getProperty("user.dir")
                                                  + "/src/arcade/resources/images/";
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    
    private JPanel myViewPanel;
    private Controller myController;
    private ResourceBundle myResources;

    
    /**
     * Creates the MainView with the provided Controller and ResourceBundle
     * 
     * @param controller
     * @param rb
     */
    public MainView (Controller controller, ResourceBundle rb) {
        myController = controller;
        myResources = rb;

        JPanel contentPane = (JPanel) getContentPane();
        contentPane.setBackground(Color.WHITE);
        contentPane.setLayout(new BorderLayout());
        contentPane.add(new ButtonPanel(this, myController, myResources), BorderLayout.WEST);
        contentPane.add(makeViewPanel(), BorderLayout.CENTER);

        setTitle(rb.getString(TextKeywords.TITLE));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Makes the panel where the mode will be showing, and initializes it
     * to the GameCenter.
     */
    private JPanel makeViewPanel () {
        myViewPanel = new JPanel();
        myViewPanel.add(new GameCenterPanel(myController, myResources));
        return myViewPanel;
    }

    /**
     * Changes the mode to the provided panel.
     * 
     * @param panel
     */
    public void changeViewPanel (JPanel panel) {
        myViewPanel.removeAll();
        myViewPanel.add(panel);
        myViewPanel.revalidate();
        myViewPanel.repaint();
    }

    /**
     * When a game is done being played, pop up a dialog box with their score,
     * high scores, and an option to share on social networks.
     * 
     */
    public void showEndGameView (int userScore) {
        new ScoresView(myController, myResources, userScore);
    }

}
