package vooga.rts.networking.client.GUI;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Collection;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import vooga.rts.networking.NetworkBundle;
import vooga.rts.networking.communications.Player;


/**
 * Represents the view of the lobby.
 * 
 * @author David Winegar
 * @author Sean Wareham
 * 
 */
public class LobbyView extends JPanel {

    private static final long serialVersionUID = -3777313562807648414L;
    private static final String NO_PLAYER = "";
    private static final int COLUMN_1 = 0;
    private static final int COLUMN_2 = 1;
    private static final int COLUMN_3 = 2;
    private static final int ROW_1 = 0;
    private int myMaxPlayers;
    JLabel[] myUsernameLabels;
    JComboBox[] myFactionBoxes;
    JComboBox[] myTeamBoxes;
    Player[] myPlayers;
    IModel myModel;
    Collection<Player> myUserControlledPlayers;
    private ItemListener myFactionListener;
    private ItemListener myTeamListener;

    /**
     * Create the panel.
     */
    public LobbyView (IModel model, List<String> myFactions, int maxPlayers) {
        // initialize GridBagLayout
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0 };
        gridBagLayout.rowHeights =
                new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 1.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights =
                new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                              0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        setLayout(gridBagLayout);

        // initialize state
        myModel = model;
        myMaxPlayers = maxPlayers;
        myUsernameLabels = new JLabel[myMaxPlayers];
        myTeamBoxes = new JComboBox[myMaxPlayers];
        myFactionBoxes = new JComboBox[myMaxPlayers];
        Integer[] teamNumList = new Integer[myMaxPlayers];
        for (int i = 0; i < myMaxPlayers; i++) {
            teamNumList[i] = i + 1;
        }

        initializeListeners();

        // initialize labels and buttons for player slots
        for (int i = 0; i < myMaxPlayers; i++) {
            myUsernameLabels[i] = createLabel(NO_PLAYER, COLUMN_1, i + 1);
            myTeamBoxes[i] = createComboBox(teamNumList, COLUMN_2, i + 1, myTeamListener);
            myFactionBoxes[i] = createComboBox(myFactions.toArray(), COLUMN_3, i + 1, myFactionListener);
        }

        // initialize title labels
        createLabel(NetworkBundle.getString("PlayerName"), COLUMN_1, ROW_1);
        createLabel(NetworkBundle.getString("Team"), COLUMN_2, ROW_1);
        createLabel(NetworkBundle.getString("Faction"), COLUMN_3, ROW_1);

    }

    private void initializeListeners () {
        myFactionListener = new ItemListener() {
            @Override
            public void itemStateChanged (ItemEvent e) {
                JComboBox box = (JComboBox) e.getSource();
                String faction = (String) box.getSelectedItem();
                for (int i = 0; i < myFactionBoxes.length; i++) {
                    if (box == myFactionBoxes[i]) {
                        myModel.updateFaction(faction, i);
                    }
                }
            }
        };

        myTeamListener = new ItemListener() {
            @Override
            public void itemStateChanged (ItemEvent e) {
                JComboBox box = (JComboBox) e.getSource();
                Integer team = (Integer) box.getSelectedItem();
                for (int i = 0; i < myTeamBoxes.length; i++) {
                    if (box == myTeamBoxes[i]) {
                        myModel.updateTeam(team, i);
                    }
                }
            }
        };

    }

    /**
     * Creates a label and adds it to grid
     */
    private JLabel createLabel (String name, int xposition, int yposition) {
        JLabel label = new JLabel(name);
        label.setOpaque(true);
        GridBagConstraints labelConstraints = new GridBagConstraints();
        addComponentToGrid(xposition, yposition, label, labelConstraints);
        return label;
    }

    /**
     * Creates a combobox and adds it to grid
     */
    private JComboBox createComboBox (Object[] objectArray,
                                      int xposition,
                                      int yposition,
                                      ItemListener listener) {
        JComboBox box = new JComboBox(objectArray);
        GridBagConstraints boxConstraints = new GridBagConstraints();
        boxConstraints.fill = GridBagConstraints.HORIZONTAL;
        addComponentToGrid(xposition, yposition, box, boxConstraints);
        box.setEnabled(false);
        box.addItemListener(listener);
        return box;
    }

    /**
     * adds component to grid at specified place
     */
    private void addComponentToGrid (int xposition,
                                     int yposition,
                                     JComponent component,
                                     GridBagConstraints labelConstraints) {
        labelConstraints.insets = new Insets(0, 5, 5, 5);
        labelConstraints.gridx = xposition;
        labelConstraints.gridy = yposition;
        add(component, labelConstraints);
    }

    private void updateLabelsAndButtons () {
        for (int i = 0; i < myPlayers.length; i++) {
            Player player = myPlayers[i];
            if (player != null) {
                myUsernameLabels[i].setText(player.getName());
                myFactionBoxes[i].setEnabled(true);
                myTeamBoxes[i].setEnabled(true);
                myFactionBoxes[i].removeItemListener(myFactionListener);
                myTeamBoxes[i].removeItemListener(myTeamListener);
                myFactionBoxes[i].setSelectedItem(player.getFaction());
                myTeamBoxes[i].setSelectedItem(player.getTeam());
                myFactionBoxes[i].addItemListener(myFactionListener);
                myTeamBoxes[i].addItemListener(myTeamListener);
                if (!myUserControlledPlayers.contains(player)) {
                    myFactionBoxes[i].setEnabled(false);
                    myTeamBoxes[i].setEnabled(false);
                }
            }
            else {
                myUsernameLabels[i].setText(NO_PLAYER);
            }
        }
        validate();
        repaint();
    }

    public void update (Collection<Player> userControlledPlayers, Player[] allPlayers) {
        myPlayers = allPlayers;
        myUserControlledPlayers = userControlledPlayers;
        updateLabelsAndButtons();
    }

}
