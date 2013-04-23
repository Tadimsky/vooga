package vooga.rts.networking.client.GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import vooga.rts.networking.communications.LobbyInfo;


/**
 * Represents the View for creating a new lobby.
 */
public class CreateLobbyView extends JPanel {

    private static final long serialVersionUID = -5215687034662203967L;

    private JTextField myServerField;
    private JComboBox myMapComboBox;
    private JComboBox myMaxPlayersComboBox;
    private Integer[][] myMaxPlayerArray;
    private String[] myMapChoices;

    public CreateLobbyView (String[] mapChoices, Integer[][] maxPlayers) {
        setLayout(new BorderLayout(0, 0));
        myMaxPlayerArray = maxPlayers;
        myMapChoices = mapChoices;
        add(createPanel(), BorderLayout.CENTER);
    }

    private JPanel createPanel () {
        JPanel innerPanel = new JPanel();

        // TODO add resources file
        innerPanel.add(new JLabel("Server name: "));
        myServerField = new JTextField(20);
        innerPanel.add(myServerField);

        innerPanel.add(new JLabel("Map: "));
        DefaultComboBoxModel mapModel = new DefaultComboBoxModel(myMapChoices);
        myMapComboBox = new JComboBox(mapModel);
        innerPanel.add(myMapComboBox);
        myMapComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent arg0) {
                String choice = (String) myMapComboBox.getSelectedItem();
                Integer[] maxPlayerChoices = null;
                for (int i = 0; i < myMapChoices.length; i++) {
                    if (myMapChoices[i].equals(choice)) {
                        maxPlayerChoices = myMaxPlayerArray[i];
                        break;
                    }
                }

                myMaxPlayersComboBox.removeAllItems();
                for (int i = 0; i < maxPlayerChoices.length; i++) {
                    myMaxPlayersComboBox.insertItemAt(maxPlayerChoices[i], i);
                }

            }
        });

        innerPanel.add(new JLabel("Max players: "));
        myMaxPlayersComboBox = new JComboBox();
        innerPanel.add(myMaxPlayersComboBox);

        return innerPanel;
    }

    public LobbyInfo getLobbyInfo () {
        return new LobbyInfo(myServerField.getText(),      
                             (String) myMapComboBox.getSelectedItem(),
                             (Integer) myMaxPlayersComboBox.getSelectedItem(), 0);
    }
}
