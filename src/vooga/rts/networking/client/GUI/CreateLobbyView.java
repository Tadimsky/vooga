package vooga.rts.networking.client.GUI;

import java.awt.BorderLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * Represents the View for creating a new lobby.
 */
public class CreateLobbyView extends JPanel {

    private static final long serialVersionUID = -5215687034662203967L;

    private JTextField myServerField;
    private JComboBox myMapComboBox;
    private JComboBox myMaxPlayersComboBox;
    
    public CreateLobbyView () {
        setLayout(new BorderLayout(0, 0));
        add(createPanel(), BorderLayout.CENTER);
        
    }
    
    private JPanel createPanel () {
        JPanel innerPanel = new JPanel();
        
        // TODO add resources file
        innerPanel.add(new JLabel("Server name: "));
        myServerField = new JTextField(20);
        innerPanel.add(myServerField);
        
        innerPanel.add(new JLabel("Map: "));
        String[] mapChoices = {"this", "that", "the other"};
        DefaultComboBoxModel mapModel = new DefaultComboBoxModel(mapChoices);
        myMapComboBox = new JComboBox(mapModel);
        innerPanel.add(myMapComboBox);
        
        innerPanel.add(new JLabel("Max players: "));
        Integer[] playerChoices = {2, 3, 4, 5, 6, 7, 8};
        DefaultComboBoxModel maxPlayerModel = new DefaultComboBoxModel(playerChoices);
        myMaxPlayersComboBox = new JComboBox(maxPlayerModel);
        innerPanel.add(myMaxPlayersComboBox);
        
        return innerPanel;
    }
}
