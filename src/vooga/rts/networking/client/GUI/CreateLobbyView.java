package vooga.rts.networking.client.GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import vooga.rts.networking.NetworkBundle;
import vooga.rts.networking.communications.LobbyInfo;


/**
 * Represents the View for creating a new lobby. This is a panel that gives the user the option of
 * choosing the server name, map name (choices passed in), and maximum number of players (also
 * passed in).
 * 
 * @author David Winegar
 */
public class CreateLobbyView extends JPanel {

    private static final int DEFAULT_TEXT_FIELD_SIZE = 20;

    private static final long serialVersionUID = -5215687034662203967L;

    private JTextField myServerField;
    // Java 6 does not allow paramaterized JComboBoxes :(
    private JComboBox myMapComboBox;
    private JComboBox myMaxPlayersComboBox;
    private List<Integer> myMaxPlayerArray;
    private List<String> myMapChoices;

    /**
     * Creates the lobby view.
     * 
     * @param maps map names
     * @param maxPlayerArray maximum players corresponding to map names
     */
    public CreateLobbyView (List<String> maps, List<Integer> maxPlayerArray) {
        setLayout(new BorderLayout(0, 0));
        myMaxPlayerArray = maxPlayerArray;
        myMapChoices = maps;
        add(createPanel(), BorderLayout.CENTER);
    }

    /**
     * Creates the panel.
     */
    private JPanel createPanel () {
        JPanel innerPanel = new JPanel();

        innerPanel.add(new JLabel(NetworkBundle.getString("ServerName")));
        myServerField = new JTextField(DEFAULT_TEXT_FIELD_SIZE);
        innerPanel.add(myServerField);

        // Creates the map combo box
        innerPanel.add(new JLabel(NetworkBundle.getString("Map")));
        DefaultComboBoxModel mapModel = new DefaultComboBoxModel(myMapChoices.toArray());
        myMapComboBox = new JComboBox(mapModel);
        innerPanel.add(myMapComboBox);
        // Add an action listener to get the choices for number of players
        myMapComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent arg0) {
                String choice = (String) myMapComboBox.getSelectedItem();
                Integer maxPlayers = myMaxPlayerArray.get(myMapChoices.indexOf(choice));

                Integer[] maxPlayerChoices = new Integer[maxPlayers - 1];
                for (int b = 2; b <= maxPlayers; b++) {
                    maxPlayerChoices[b - 2] = b;
                }

                myMaxPlayersComboBox.removeAllItems();
                for (int i = 0; i < maxPlayerChoices.length; i++) {
                    myMaxPlayersComboBox.insertItemAt(maxPlayerChoices[i], i);
                }

            }
        });

        // Add max players label and combobox
        innerPanel.add(new JLabel(NetworkBundle.getString("MaxPlayers")));
        myMaxPlayersComboBox = new JComboBox();
        innerPanel.add(myMaxPlayersComboBox);

        return innerPanel;
    }

    /**
     * Returns whether the server name, map, and number are chosen.
     * 
     * @return true if ready to get lobby info
     */
    public boolean allItemsChosen () {
        return myMaxPlayersComboBox.getSelectedIndex() != -1 &&
               myMapComboBox.getSelectedIndex() != -1 &&
               !myServerField.getText().equals("");
    }

    /**
     * Returns the lobbyInfo used to create a new server as long as everything has been chosen.
     * 
     * @return lobbyInfo
     */
    public LobbyInfo getLobbyInfo () {
        return new LobbyInfo(myServerField.getText(),
                             (String) myMapComboBox.getSelectedItem(),
                             (Integer) myMaxPlayersComboBox.getSelectedItem(), 0);
    }
}
