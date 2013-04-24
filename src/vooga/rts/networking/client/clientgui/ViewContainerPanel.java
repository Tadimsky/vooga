package vooga.rts.networking.client.clientgui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


/**
 * A panel that contains the other views and provides a common framework for changing views.
 * 
 * @author David Winegar
 * 
 */
public class ViewContainerPanel extends JPanel {

    private static final long serialVersionUID = -2362417830702753508L;
    private BorderLayout myLayout = new BorderLayout(0, 0);
    private JButton myLeftButton;
    private JButton myRightButton;
    private JLabel myLabel;
    private String myGameName;

    /**
     * Creates a panel that provides methods for changing view.
     * 
     * @param gameName name of the game
     */
    public ViewContainerPanel (String gameName) {
        myGameName = gameName;
        setLayout(myLayout);
        createTopPanel();
        createBottomPanel();
        // Dummy panel
        add(new JPanel(), BorderLayout.CENTER);
    }

    /**
     * Changes the center panel and the appended text to the title.
     * 
     * @param panel to put in center
     * @param additionalTitleText text to append to title
     */
    public void changeView (JPanel panel, String additionalTitleText) {
        remove(myLayout.getLayoutComponent(BorderLayout.CENTER));
        add(panel, BorderLayout.CENTER);
        myLabel.setText(myGameName + additionalTitleText);
        validate();
        repaint();
    }

    /**
     * Sets the left button text and action listener.
     * 
     * @param label text
     * @param action actionlistener
     */
    public void changeLeftButton (String label, ActionListener action) {
        setButtonTextAndAction(myLeftButton, label, action);
    }

    /**
     * Sets the right button text and action listener.
     * 
     * @param label text
     * @param action actionlistener
     */
    public void changeRightButton (String label, ActionListener action) {
        setButtonTextAndAction(myRightButton, label, action);
    }

    /**
     * Utility method - removes repeated code from changeRightButton/changeLeftButton.
     */
    private void setButtonTextAndAction (JButton button, String label, ActionListener listener) {
        button.setText(label);
        for (ActionListener al : button.getActionListeners()) {
            button.removeActionListener(al);
        }
        button.addActionListener(listener);
    }

    /**
     * Initializes top panel with just game name.
     */
    private void createTopPanel () {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        myLabel = new JLabel(myGameName);
        topPanel.add(myLabel);
        add(topPanel, BorderLayout.NORTH);
    }

    /**
     * Initializes bottom panel with button labels of the game name.
     */
    private void createBottomPanel () {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout(0, 0));
        myLeftButton = new JButton(myGameName);
        myRightButton = new JButton(myGameName);
        bottomPanel.add(myLeftButton, BorderLayout.WEST);
        bottomPanel.add(myRightButton, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Method for alerting the user to a very important message. Primarily considered for displaying
     * sever errors.
     * 
     * @param title title of the dialog window (ie: Error)
     * @param message message content
     */
    public void showMessageDialog (String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.PLAIN_MESSAGE);
    }
}
