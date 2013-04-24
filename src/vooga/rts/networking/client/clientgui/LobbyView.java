package vooga.rts.networking.client.clientgui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Collection;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
    private JLabel[] myUsernameLabels;
    private JComboBox[] myFactionBoxes;
    private JComboBox[] myTeamBoxes;
    private Player[] myPlayers;
    private IModel myModel;
    private Collection<Player> myUserControlledPlayers;
    private ItemListener myFactionListener;
    private ItemListener myTeamListener;

    /**
     * Create the panel.
     * 
     * @param model model to communicate with
     * @param factions list of factions
     * @param maxPlayers max players of this lobby
     */
    public LobbyView (IModel model, List<String> factions, int maxPlayers) {

        initializeGridBagLayout();

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
        initializeLabelsAndBoxes(factions, teamNumList);
    }

    /**
     * Initializes the title labels and the labels and boxes used in the GridBagLayout for the lobby
     * chooser.
     * 
     * @param factions factions
     * @param teamNumList list of team numbers
     */
    private void initializeLabelsAndBoxes (List<String> factions, Integer[] teamNumList) {
        // initialize labels and buttons for player slots
        for (int i = 0; i < myMaxPlayers; i++) {
            myUsernameLabels[i] = createLabel(NO_PLAYER, COLUMN_1, i + 1);
            myTeamBoxes[i] = createComboBox(teamNumList, COLUMN_2, i + 1, myTeamListener);
            myFactionBoxes[i] =
                    createComboBox(factions.toArray(), COLUMN_3, i + 1, myFactionListener);
        }

        // initialize title labels
        createLabel(NetworkBundle.getString("PlayerName"), COLUMN_1, ROW_1);
        createLabel(NetworkBundle.getString("Team"), COLUMN_2, ROW_1);
        createLabel(NetworkBundle.getString("Faction"), COLUMN_3, ROW_1);
    }

    /**
     * Initializes the layout. This particular method was created by the WindowBuilder and thus is
     * not the most readable. Also has significant conflicts between checkstyle and the formatter.
     */
    private void initializeGridBagLayout () {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0 };
        gridBagLayout.rowHeights =
                new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 1.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights =
                new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                              0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        setLayout(gridBagLayout);
    }

    /**
     * Initializes the listeners into state. myFactionListener listens for changes in factions, and
     * myTeamListener listens for changes in teams, and they update the IModel appropriately.
     */
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
     * Creates a label and adds it to grid. Utility method to avoid repeated code.
     */
    private JLabel createLabel (String name, int xposition, int yposition) {
        JLabel label = new JLabel(name);
        label.setOpaque(true);
        GridBagConstraints labelConstraints = new GridBagConstraints();
        addComponentToGrid(xposition, yposition, label, labelConstraints);
        return label;
    }

    /**
     * Creates a combobox and adds it to grid. Utility method to avoid repeated code.
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
     * adds component to grid at specified place. Utility method to avoid repeated code.
     * Method created through WindowBuilder.
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

    /**
     * Updates all labels and buttons
     */
    private void updateLabelsAndButtons () {
        // for each player
        for (int i = 0; i < myPlayers.length; i++) {
            // set boxes not enabled
            myFactionBoxes[i].setEnabled(false);
            myTeamBoxes[i].setEnabled(false);
            Player player = myPlayers[i];
            // if player exists
            if (player != null) {
                // set player name
                myUsernameLabels[i].setText(player.getName());
                // remove item listeners, because when calling setSelectedItem() it will create an
                // infinite loop with the listener
                myFactionBoxes[i].removeItemListener(myFactionListener);
                myTeamBoxes[i].removeItemListener(myTeamListener);
                // change to reflect current state
                myFactionBoxes[i].setSelectedItem(player.getFaction());
                myTeamBoxes[i].setSelectedItem(player.getTeam());
                // add back item listeners
                myFactionBoxes[i].addItemListener(myFactionListener);
                myTeamBoxes[i].addItemListener(myTeamListener);
                // if slot is user-controllable, set boxes as enabled
                if (myUserControlledPlayers.contains(player)) {
                    myFactionBoxes[i].setEnabled(true);
                    myTeamBoxes[i].setEnabled(true);
                }
            }
            else {
                // set empty label
                myUsernameLabels[i].setText(NO_PLAYER);
            }
        }
        validate();
        repaint();
    }

    /**
     * Update the current LobbyView to reflectt the state passed in.
     * 
     * @param userControlledPlayers new user controlled players
     * @param allPlayers all players
     */
    public void update (Collection<Player> userControlledPlayers, Player[] allPlayers) {
        myPlayers = allPlayers;
        myUserControlledPlayers = userControlledPlayers;
        updateLabelsAndButtons();
    }

}
