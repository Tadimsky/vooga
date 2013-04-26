package vooga.towerdefense.gameeditor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * LevelEditorScreen is responsible for helping
 * the game developer make levels.
 * 
 * @author Angelica Schwartz
 */
public class GameElementEditorScreen extends ElementWithActionEditorScreen {

    /**
     * default serialized id.
     */
    private static final long serialVersionUID = 1L;
    /**
     * specifies user directory.
     */
    private static final String USER_DIR = "user.dir";
    /**
     * next screen constant.
     */
    private static final String NEXT_SCREEN_NAME = "LevelEditorScreen";
    /**
     * constant for the image selector button.
     */
    private static final String IMAGE_SELECTOR_KEYWORD = "Select Image From File";
    /**
     * package for the wave action factories.
     */
    private static final String ACTION_PACKAGE_PATH = "vooga.towerdefense.factories.actionfactories";
    /**
     * package for the attributes.
     */
    private static final String ATTRIBUTE_CLASS_PATH = "vooga.towerdefense.attributes.AttributeConstants";
    /**
     * text on button to add a new type.
     */
    private static final String ADD_NEW_TYPE_TEXT = "Add new type";
    /**
     * title constant.
     */
    private static final String TITLE_NAME = "GAME ELEMENT ";
    /**
     * keyword for units.
     */
    private static final String UNIT_TYPE = "Unit";
    /**
     * keyword for towers.
     */
    private static final String TOWER_TYPE = "Tower";
    /**
     * keyword for projectiles.
     */
    private static final String PROJECTILE_TYPE = "Projectile";
    /**
     * used to choose file in the directory.
     */
    private JFileChooser myFileChooser;
    /**
     * arraylist with the default available types.
     */
    private List<String> myAvailableTypes;
    /**
     * box to enter the type of the game element.
     */
    private JComboBox myTypeBox;
    /**
     * box to enter the image of the game element.
     */
    private JTextField myImageBox;
    /**
     * button add a new type of game element.
     */
    private JButton myAddTypeButton;
    /**
     * button to get the image of the game element from the file system.
     */
    private JButton myImageSelector;

    /**
     * Constructor.
     * 
     * @param size
     * @param controller
     * @throws ClassNotFoundException 
     * @throws IOException 
     */
    public GameElementEditorScreen (Dimension size, GameEditorController controller) throws ClassNotFoundException, IOException {
        super(size, controller, TITLE_NAME, NEXT_SCREEN_NAME);
        myFileChooser = new JFileChooser(System.getProperties().getProperty(USER_DIR));
        myAvailableTypes = new ArrayList<String>();
        myAvailableTypes.add(UNIT_TYPE);
        myAvailableTypes.add(TOWER_TYPE);
        myAvailableTypes.add(PROJECTILE_TYPE);
        setActionPath(ACTION_PACKAGE_PATH);
        makeAttributesSection(getController().getAttributes(ATTRIBUTE_CLASS_PATH));
        makeActionsSection(ACTION_PACKAGE_PATH);
        makeScreen();
    }
    
    @Override
    public void makeScreen() {
        super.makeScreen();        
        JPanel typePanel = new JPanel(new BorderLayout());
        typePanel.add(new JLabel("Type: "), BorderLayout.NORTH);
        myAddTypeButton = new JButton(ADD_NEW_TYPE_TEXT);
        myAddTypeButton.addMouseListener(getMouseAdapter());
        typePanel.add(myAddTypeButton, BorderLayout.SOUTH);
        myTypeBox = new JComboBox();
        for (String type : myAvailableTypes) {
            myTypeBox.addItem(type);
        }
        typePanel.add(myTypeBox, BorderLayout.CENTER);
        add(typePanel, BorderLayout.NORTH);
        
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(new JLabel("Image: "), BorderLayout.NORTH);
        myImageBox = new JTextField(TEXT_AREA_WIDTH);
        myImageSelector = new JButton(IMAGE_SELECTOR_KEYWORD);
        myImageSelector.addMouseListener(getMouseAdapter());
        imagePanel.add(myImageBox, BorderLayout.CENTER);
        imagePanel.add(myImageSelector, BorderLayout.SOUTH);
        add(imagePanel, BorderLayout.NORTH);
    }
    
    @Override
    public void clearScreen() {
        super.clearScreen();
        myImageBox.setText("");
    }

    /**
     * adds this game element to the game.
     */
    public void addElementToGame() {
        getController().addGameElementToGame(myTypeBox.getSelectedItem().toString(), getName(),
                                         myImageBox.getText(),
                                         getAttributes(),
                                         getActions());
    }

    /**
     * adds additional mouse behavior specific
     * to the GameElementEditorScreen.
     * 
     * @param e is the MouseEvent
     */
    @Override
    public void addAdditionalMouseBehavior (MouseEvent e) {
        super.addAdditionalMouseBehavior(e);
        if (e.getSource().equals(myAddTypeButton)) {
            String newType = JOptionPane.showInputDialog("Enter your new type");
            myTypeBox.addItem(newType);
            myTypeBox.setSelectedItem(newType);
        }
        else if (e.getSource().equals(myImageSelector)) {
            int response = myFileChooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                File file = myFileChooser.getSelectedFile();
                String path = file.getPath().replace("%20", " ");
                myImageBox.setText(path);
            }
        }
    }
}
