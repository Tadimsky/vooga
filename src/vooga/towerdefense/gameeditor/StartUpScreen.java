package vooga.towerdefense.gameeditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * StartUpScreen starts the GameEditor for the
 * game developer.
 * 
 * @author Angelica Schwartz
 * @author Leonard K. Ng'eno
 */
public class StartUpScreen extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final String WELCOME_KEYWORD = "WELCOME TO GAME EDITOR";
    private static final String START_KEYWORD = "START";
    private static final String NEXT_SCREEN_NAME = "vooga.towerdefense.gameeditor.ViewEditorScreen";
    private static final String GAME_NAME_TEXT = "Game Name: ";
    private static final String SELECT_NAME_TEXT = "Confirm this as Game Name";
    private static final int TEXT_FIELD_WIDTH = 20;
    private JButton myStartButton;
    private JButton myGameNameConfirmation;
    private JTextField myGameName;
    private MouseAdapter myMouseAdapter;
    private GameEditorController myController;

    /**
     * Constructor.
     * 
     * @param size
     * @param controller
     */
    public StartUpScreen (Dimension size, GameEditorController controller) {
        myController = controller;
        setSize(size);
        setPreferredSize(size);
        setVisible(true);
        this.setLayout(new BorderLayout());
        makeMouseAdapter();
        this.add(makeLabel(), BorderLayout.NORTH);
        this.add(makeStartButton(), BorderLayout.SOUTH);
        this.add(makeGameNamingSection(), BorderLayout.CENTER);
    }

    /**
     * paints the screen.
     * 
     * @param pen
     */
    @Override
    public void paintComponent (Graphics pen) {
        super.paintComponents(pen);
        pen.setColor(Color.WHITE);
        pen.fillRect(0, 0, getSize().width, getSize().height);
    }

    /**
     * Helper method to make a welcome title label.
     * 
     * @return the JLabel created
     */
    private JComponent makeLabel () {
        JPanel panel = new JPanel();
        JLabel label = new JLabel(WELCOME_KEYWORD);
        panel.setPreferredSize(new Dimension (this.getWidth(), 100));
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel makeGameNamingSection() {
        JPanel panel = new JPanel();
        panel.add(new JLabel(GAME_NAME_TEXT), BorderLayout.NORTH);
        myGameName = new JTextField(TEXT_FIELD_WIDTH);
        panel.add(myGameName, BorderLayout.CENTER);
        myGameNameConfirmation = new JButton(SELECT_NAME_TEXT);
        myGameNameConfirmation.addMouseListener(myMouseAdapter);
        panel.add(myGameNameConfirmation, BorderLayout.SOUTH);
        return panel;
    }

    /**
     * helper method to make the buttons.
     * 
     * @return the JButton created
     */
    private JComponent makeStartButton () {
        JPanel panel = new JPanel();
        myStartButton = new JButton(START_KEYWORD);
        myStartButton.addMouseListener(myMouseAdapter);
        panel.add(myStartButton);
        panel.setPreferredSize(new Dimension(this.getWidth(), 400));
        return panel;
    }

    private void makeMouseAdapter () {
        myMouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent e) {
                if (e.getSource().equals(myGameNameConfirmation)) {
                    myController.setNameOfGame(myGameName.getText());
                }
                else if (e.getSource().equals(myStartButton)) {
                    try {
                        setVisible(false);
                        myController.displayNextScreen(NEXT_SCREEN_NAME);
                    }
                    catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    }
                    catch (InstantiationException e1) {
                        e1.printStackTrace();
                    }
                    catch (IllegalAccessException e1) {
                        e1.printStackTrace();
                    }
                    catch (SecurityException e1) {
                        e1.printStackTrace();
                    }
                    catch (IllegalArgumentException e1) {
                        e1.printStackTrace();
                    }
                    catch (NoSuchMethodException e1) {
                        e1.printStackTrace();
                    }
                    catch (InvocationTargetException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        };
    }
}
