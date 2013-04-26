package arcade.view.forms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import arcade.controller.Controller;
import arcade.controller.UserSpecificData;
import arcade.exceptions.DOBFormatException;
import arcade.exceptions.UsernameFormatException;
import arcade.exceptions.UsernameTakenException;
import arcade.view.TextKeywords;


/**
 * The view that allows a new user to register an account.
 * 
 * @author Ellango
 * 
 */
@SuppressWarnings({ "unused", "serial" })
public class RegisterView extends Account {
    private static final String DEFAULT_IMAGE =
            new File(System.getProperty("user.dir") + "/src/arcade/resources/images/rcd.jpg")
                    .getPath();
    private static final int WINDOW_WIDTH = 240;
    private static final int WINDOW_HEIGHT = 330;
    private JTextField myFirstNameTextField;
    private JTextField myLastNameTextField;
    private JTextField myDOBTextField;
    private String myImagePath = DEFAULT_IMAGE;

    /**
     * Constructs the register view with a Controller and Resource Bundle
     * 
     * 
     * @param controller
     * @param resources
     */
    public RegisterView (Controller controller, ResourceBundle resources) {
        this(controller, resources, "", "");
    }

    /**
     * Constructs the register view with a Controller, ResourceBundle, and also
     * fills in the username and password fields with some initial values.
     * This might be useful if the user already typed in these values at a
     * previous point such as the login view.
     * 
     * @param controller
     * @param resources
     * @param initialUsername
     * @param initialPassword
     */
    public RegisterView (Controller controller,
                         ResourceBundle resources,
                         String initialUsername,
                         String initialPassword) {
        super(controller, resources);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);

        setUsername(initialUsername);
        setPassword(initialPassword);
    }

    /**
     * Creates a label representing instructions for the user.
     * 
     * @return
     */
    private JComponent createInstructions () {
        JPanel panel = new JPanel();
        JLabel instruction = new JLabel(getResources().getString(TextKeywords.REGISTER_MESSAGE));
        panel.add(instruction);
        return panel;
    }

    /**
     * Creates the field where the user can enter his/her first name
     * 
     * @return
     */
    private JComponent createFirstNameField () {
        myFirstNameTextField = new JTextField();
        return createTextPanel(TextKeywords.FIRSTNAME, myFirstNameTextField);
    }

    /**
     * Creates the field where the user can enter his/her last name.
     * 
     * @return
     */
    private JComponent createLastNameField () {
        myLastNameTextField = new JTextField();
        return createTextPanel(TextKeywords.LASTNAME, myLastNameTextField);
    }

    /**
     * Creates the field where the user can enter his/her date of birth.
     * 
     * @return
     */
    private JComponent createDOBField () {
        myDOBTextField = new JTextField();
        myDOBTextField.setText(getResources().getString(TextKeywords.BIRTHDATE_MESSAGE));
        myDOBTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained (FocusEvent arg0) {
                myDOBTextField.setText("");
            }

            @Override
            public void focusLost (FocusEvent arg0) {
                // do nothing
            }
        });
        return createTextPanel(TextKeywords.BIRTHDATE, myDOBTextField);
    }

    /**
     * Creates the panel where the user can select his/her profile picture
     * 
     * @return
     */
    private JComponent createProfilePictureSelector () {
        return createImageSelector(TextKeywords.IMAGE_MESSAGE,
                                   TextKeywords.IMAGE_BUTTON,
                                   new FileChooserAction() {
                                       @Override
                                       public void approve (JFileChooser chooser) {
                                           myImagePath = chooser.getSelectedFile().getPath();
                                       }
                                   });
    }

    /**
     * Creates the button where the user can submit his/her information.
     * 
     * @return
     */
    private JComponent createRegisterButton () {
        return createButton(TextKeywords.REGISTER,
                            new ActionListener() {
                                @Override
                                public void actionPerformed (ActionEvent arg0) {
                                    try {
										registerNewUser();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
                                }
                            });
    }

    /**
     * Tries to register a new user in the database. Checks if username and
     * date of birth are in the correct format.
     * 
     * If register successful, logs in to the arcade.
     * @throws IOException 
     * 
     */
    private void registerNewUser () throws IOException {
        try {
            if (!isUsernameCorrectFormat(getUsername())) { throw new UsernameFormatException(); }

            if (!isDOBCorrectFormat(myDOBTextField.getText())) { throw new DOBFormatException(); }

            UserSpecificData data = new UserSpecificData(getUsername(),
                                                         getPassword(),
                                                         myFirstNameTextField.getText(),
                                                         myLastNameTextField.getText(),
                                                         myDOBTextField.getText(),
                                                         myImagePath);
            getController().createNewUserProfile(data);
            dispose();

        }
        catch (UsernameFormatException e) {
            sendMessage(getResources().getString(e.getLocalizedMessage()));
            clearUsername();
        }
        catch (DOBFormatException e) {
            sendMessage(getResources().getString(e.getLocalizedMessage()));
            myDOBTextField.setText(getResources().getString(TextKeywords.BIRTHDATE_MESSAGE));
        }
        catch (UsernameTakenException e) {
            sendMessage(getResources().getString(e.getLocalizedMessage()));
            clearUsername();
        }
    }

    /**
     * Checks if the username is in an okay format. This implementation only
     * requires that it contains some characters, but this method can be overriden
     * if more checks are desired (e.g. a minimum length, no profanity)
     * 
     * @param username
     * @return
     */
    private boolean isUsernameCorrectFormat (String username) {
        return !(username.isEmpty());
    }

    /**
     * Checks if the date of birth is in the correct format.
     * 
     * @param text
     * @return
     */
    private boolean isDOBCorrectFormat (String dob) {
        return dob.matches("[0-1][0-9]/[0-3][0-9]/[1-2][0-9][0-9][0-9]");
    }

}
