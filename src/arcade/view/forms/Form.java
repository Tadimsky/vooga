package arcade.view.forms;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import util.BackgroundPanel;
import arcade.controller.Controller;
import arcade.exceptions.UndefinedFormException;
import arcade.view.TextKeywords;


/**
 * This represents a form that a user can fill out.
 * 
 * @author Ellango
 */
@SuppressWarnings("serial")
public abstract class Form extends JFrame {
    private static final String FORMS_DIRECTORY = System.getProperty("user.dir") + "/src/arcade/resources/forms/";
    public static final String BACKGROUND_FILENAME = "arcade/resources/images/LoginBackGround.jpg";
    private static final int TEXT_FIELD_HEIGHT = 25;
    private static final int TEXT_FIELD_SIZE = 10;
    private static final int LABEL_WIDTH = 80;
    private static final int MESSAGE_WIDTH = 140;

    private Controller myController;
    private ResourceBundle myResources;
    private JLabel myWarningMessage = new JLabel();

    /**
     * Constructs the form view with a Controller and ResourceBundle. It adds a
     * background and adds all the components from makeComponents().
     * 
     * @param controller
     * @param resources
     */
    public Form (Controller controller, ResourceBundle resources) {
        myController = controller;
        myResources = resources;

        BackgroundPanel background = new BackgroundPanel(BACKGROUND_FILENAME);
        getContentPane().add(background);

        JPanel mainContents = new JPanel();
        mainContents.setLayout(new BoxLayout(mainContents, BoxLayout.Y_AXIS));
        for (JComponent component : makeComponents()) {
            addTransparentComponent(mainContents, component);
        }
        background.add(mainContents);

        setTitle(myResources.getString(TextKeywords.TITLE));
        setResizable(false);
        setVisible(true);
    }

    /**
     * Display an error message in the view.
     * 
     * For example, if user makes failed log in attempt or tries to register an account
     * that has already been named.
     * 
     * @param message
     */
    public void sendMessage (String message) {
        myWarningMessage.setText("<html><body style='width:" + MESSAGE_WIDTH + " px'>"
                                 + "<center><font color = red>" + message);
    }

    /**
     * Create all the components to be displayed in the form.
     * 
     * This is done by reading from the file in the arcade/resources/forms
     * directory that has the same name as this class.  That file contains
     * a list of which components should be added in a specified order.
     * 
     * @return
     */
    private List<JComponent> makeComponents () {
        List<JComponent> components = new ArrayList<JComponent>();
        Class<?> thisClass = getClass();
        try {
            Scanner scanner = new Scanner(new File(FORMS_DIRECTORY + thisClass.getSimpleName()));
            while (scanner.hasNextLine()) {
                String methodName = scanner.nextLine();
                Method createComponent = getMethod(thisClass, methodName);
                createComponent.setAccessible(true);
                JComponent component = (JComponent) createComponent.invoke(this, new Object[0]);
                components.add(component);
            }
            scanner.close();
            return components;
        }
        catch (FileNotFoundException e) {
           throw new UndefinedFormException();
        }
        catch (NoSuchMethodException e) {
            throw new UndefinedFormException();
        }
        catch (SecurityException e) {
            throw new UndefinedFormException();
        }
        catch (IllegalAccessException e) {
            throw new UndefinedFormException();
        }
        catch (IllegalArgumentException e) {
            throw new UndefinedFormException();
        }
        catch (InvocationTargetException e) {
            throw new UndefinedFormException();
        }
    }
    
    /**
     * A helper to find the method named methodName for the provided clazz.
     * Looks through clazz and every superclass of clazz until the method is found.
     * 
     * @param clazz
     * @param methodName
     * @return
     * @throws NoSuchMethodException
     */
    private Method getMethod(Class<?> clazz, String methodName) throws NoSuchMethodException {
        try {
            return clazz.getDeclaredMethod(methodName);
        }
        catch (NoSuchMethodException e) {
            // base case
            if (clazz.equals(Object.class)) {
                throw e;
            }
            return getMethod(clazz.getSuperclass(), methodName);
        }
    }

    /**
     * Makes the provided component transparent, and then adds it to the provided
     * container.
     * 
     * @param container
     * @param component
     */
    private void addTransparentComponent (Container container, JComponent component) {
        component.setOpaque(false);
        container.add(component);
    }
    
    /**
     * Create an instruction for the user.  The instruction is chosen from the
     * ResourceBundle with instructionKeyword
     * 
     * The result is wrapped in a panel so box layout behaves.
     * @return
     */
    protected JComponent createInstruction(String instructionKeyword) {
        JPanel panel = new JPanel();
        panel.add(new JLabel(getResources().getString(instructionKeyword)));
        return panel;
    }

    /**
     * Create a panel with a description and a corresponding text field.
     * 
     * @param descriptionKeyword is the keyword in the resource bundle.
     * @param inputField
     * @return
     */
    protected JComponent createTextPanel (String descriptionKeyword, JTextField inputField) {
        JPanel panel = new JPanel();

        String description = myResources.getString(descriptionKeyword);
        JLabel label = new JLabel("<html><b>" + description);
        label.setPreferredSize(new Dimension(LABEL_WIDTH, label.getPreferredSize().height));
        panel.add(label);

        inputField.setColumns(TEXT_FIELD_SIZE);
        inputField.setPreferredSize(new Dimension(getPreferredSize().width, TEXT_FIELD_HEIGHT));
        panel.add(inputField);

        return panel;
    }

    /**
     * Create a panel with a description of an instruction, and a button to
     * select an image.
     * 
     * @param descriptionKeyword is the ResourceBundle keyword for the description
     * @param buttonKeyword is the ResourceBundle keyword for the button label
     * @param action is the FileChooserAction with the method defined for what
     *        to do on approval
     */
    protected JComponent createImageSelector (String descriptionKeyword,
                                              String buttonKeyword,
                                              final FileChooserAction action) {
        FileFilter filter = new FileNameExtensionFilter(getResources().getString(TextKeywords.IMAGE),
                                                        "jpg", "gif", "png");
        return createFileSelector(descriptionKeyword, buttonKeyword, action, filter);
    }
    
    
    /**
     * Create a panel with a description of an instruction, and a button to
     * select a file.
     * 
     * @param descriptionKeyword is the ResourceBundle keyword for the description
     * @param buttonKeyword is the ResourceBundle keyword for the button label
     * @param action is the FileChooserAction with the method defined for what
     *        to do on approval
     * @param filter is the Filter on the file chooser.
     */
    protected JComponent createFileSelector (String descriptionKeyword,
                                             String buttonKeyword,
                                             final FileChooserAction action,
                                             final FileFilter filter) {
        JPanel panel = new JPanel();
        JLabel description = new JLabel(getResources().getString(descriptionKeyword));
        panel.add(description);
        JButton button = new JButton(getResources().getString(buttonKeyword));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent arg0) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileFilter(filter);

                int returnVal = chooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    action.approve(chooser);
                }
            }
        });
        panel.add(button);
        return panel;
    }
    
    /**
     * Create a label where an error message can be displayed.
     * 
     * @return
     */
    protected JComponent createMessageArea () {
        JPanel panel = new JPanel();
        panel.add(myWarningMessage);
        return panel;
    }
    
    /**
     * Create a button to submit results.  The text on the button is from the 
     * ResourceBundle for buttonKeyword.  On hitting the button, the performed
     * action is taken.
     * 
     * Button is wrapped in a panel so BoxLayout works correctly.
     * 
     */
    protected JComponent createButton(String buttonKeyword, ActionListener action) {
        JPanel panel = new JPanel();
        JButton button = new JButton(getResources().getString(buttonKeyword));
        button.addActionListener(action);
        panel.add(button);
        return panel;
    }

    /**
     * Access to the controller for subclasses.
     * 
     * @return
     */
    protected Controller getController () {
        return myController;
    }

    /**
     * Access to the resources for subclasses.
     * 
     * @return
     */
    protected ResourceBundle getResources () {
        return myResources;
    }
}
