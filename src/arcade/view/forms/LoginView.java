package arcade.view.forms;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import arcade.controller.Controller;
import arcade.exceptions.LoginErrorException;
import arcade.view.TextKeywords;


/**
 * The initial view when the arcade is launched. Allows a user to login
 * or create a new account.
 * 
 * @author David Liu, Ellango
 * 
 */
@SuppressWarnings({ "unused", "serial" })
public class LoginView extends Account {
    private static final String LOGO_FILENAME = "../../resources/images/VoogaLogo.png";
    private static final int WINDOW_WIDTH = 260;
    private static final int WINDOW_HEIGHT = 240;

    /**
     * Constructs the LoginView with a Controller and ResourceBundle
     * 
     * @param controller to authenticate the login information
     * @param resources to display text of appropriate language on screen
     */
    public LoginView (Controller controller, ResourceBundle resources) {
        super(controller, resources);

        setPasswordFieldListener(new KeyAdapter() {
            @Override
            public void keyPressed (KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    login();
                }
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);
    }

    /**
     * create the main logo and center it.
     */
    private JComponent createLogo () {
        JPanel panel = new JPanel();
        ImageIcon headIcon = new ImageIcon(this.getClass().getResource(LOGO_FILENAME));
        JLabel head = new JLabel(headIcon);
        panel.add(head);
        return panel;
    }

    /**
     * Create the login and register buttons.
     * 
     * @return
     */
    private JComponent createButtons () {
        JPanel buttonPanel = new JPanel();

        JButton login = new JButton(getResources().getString(TextKeywords.LOGIN));
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent arg0) {
                login();
            }
        });
        buttonPanel.add(login);

        JButton register = new JButton(getResources().getString(TextKeywords.REGISTER));
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent arg0) {
                new RegisterView(getController(), getResources(), getUsername(), getPassword());
                dispose();
            }
        });
        buttonPanel.add(register);

        resizeComponentWidths(buttonPanel);
        return buttonPanel;
    }

    /**
     * Finds the maximum preferred width for all components in a container,
     * and then resizes all components to match that width.
     * 
     * @param container
     */
    private void resizeComponentWidths (Container container) {
        int max = 0;
        for (Component c : container.getComponents()) {
            int width = c.getPreferredSize().width;
            if (width > max) {
                max = width;
            }
        }

        for (Component c : container.getComponents()) {
            c.setPreferredSize(new Dimension(max, c.getPreferredSize().height));
        }
    }

    /**
     * Sends the inputs to the controller to try logging in.
     */
    private void login () {
        String usernameInput = getUsername();
        String passwordInput = getPassword();
        clearUsername();
        clearPassword();
        try {
            getController().authenticate(usernameInput, passwordInput);
        }
        catch (LoginErrorException e) {
            sendMessage(getResources().getString(e.getLocalizedMessage()));
        }
    }
}
