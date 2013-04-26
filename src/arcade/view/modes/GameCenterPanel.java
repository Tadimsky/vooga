package arcade.view.modes;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Collection;
import java.util.ResourceBundle;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import arcade.controller.Controller;
import arcade.games.GameInfo;
import arcade.view.AllSnapShots;
import arcade.view.TextKeywords;


/**
 * 
 * The GameCenterPanel is where the user can view SnapShots for all the games
 * s/he has bought. These SnapShots can be clicked to launch a more detailed
 * view for that game.
 * 
 * @author David Liu, Ellango Jothimurugesan
 * 
 */
@SuppressWarnings("serial")
public class GameCenterPanel extends JPanel {

    private static final int PANEL_HEIGHT = 540;
    private static final int PANEL_WIDTH = 590;

    /**
     * Creates the GameCenterPanel with a Controller and ResourceBundle
     * 
     * If no games purchased, then displays a default message.
     */
    public GameCenterPanel (Controller controller, ResourceBundle resources) {
        Collection<GameInfo> games = controller.getGamesPurchased();
        if (!games.isEmpty()) {
            AllSnapShots allSnapShots = new AllSnapShots(controller,
                                                         resources,
                                                         games,
                                                         new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
            add(new JScrollPane(allSnapShots));
        }
        else {
            setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
            setLayout(new GridBagLayout());
            add(new JLabel(resources.getString(TextKeywords.NO_GAMES)), new GridBagConstraints());
        }
    }
}
