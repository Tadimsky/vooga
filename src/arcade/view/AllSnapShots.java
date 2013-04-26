package arcade.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Collection;
import java.util.ResourceBundle;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.Scrollable;
import arcade.controller.Controller;
import arcade.games.GameInfo;


/**
 * AllSnapShots is a panel containing many individual SnapShots. AllSnapShots
 * is Scrollable so when more SnapShots are available than fit on the screen,
 * the rest can be viewed by scrolling down if this class is added to a
 * JScrollPane
 * 
 * @author Ellango
 * 
 */
@SuppressWarnings("serial")
public class AllSnapShots extends JPanel implements Scrollable {
    private static final int SNAPSHOTS_PER_ROW = 3;
    private static final int SCROLL_AMOUNT = 30;

    private Controller myController;
    private ResourceBundle myResources;
    private Dimension mySize;

    /**
     * Creates the panel containing all SnapShots tiled together in a grid.
     * Constructed with a Controller, ResourceBundle, and Dimension for the size
     * of this panel.
     * 
     * @param controller
     * @param resources
     * @param size
     */
    public AllSnapShots (Controller controller,
                         ResourceBundle resources,
                         Collection<GameInfo> games,
                         Dimension size) {
        myController = controller;
        myResources = resources;
        mySize = size;

        setBackground(Color.WHITE);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        addSnapShots(games);
    }

    /**
     * Adds all of the SnapShots to this panel. Adds from left to right until
     * SNAPSHOTS_PER_ROW are filled, and then goes to the next row.
     */
    private void addSnapShots (Collection<GameInfo> games) {
        int counter = 0;
        JPanel row = createNewRow();
        for (GameInfo game : games) {
            SnapShot snapshot = new SnapShot(game, myResources, myController);
            if (counter % SNAPSHOTS_PER_ROW == 0) {
                row = createNewRow();
                add(row);
            }
            row.add(snapshot);
            counter++;
        }
        int fillersNeeded = SNAPSHOTS_PER_ROW - (counter % SNAPSHOTS_PER_ROW);
        for (int i = 0; i < fillersNeeded; i++) {
            row.add(createFiller());
        }
    }

    /**
     * Helper called by addSnapShots. Creates a new row when the previous row
     * is filled.
     * 
     * @return
     */
    private JPanel createNewRow () {
        JPanel row = new JPanel();
        row.setBackground(Color.WHITE);
        return row;
    }

    /**
     * Helper called by addSnapShots. After all SnapShots have been added to this
     * panel, pad the rest of the row with filler so that all SnapShots arranged
     * nicely in a grid.
     * 
     * @return
     */
    private JPanel createFiller () {
        JPanel filler = new JPanel();
        filler.setBackground(Color.WHITE);
        filler.setPreferredSize(new Dimension(SnapShot.THUMBNAIL_SIZE, SnapShot.THUMBNAIL_SIZE));
        return filler;
    }

    @Override
    public Dimension getPreferredScrollableViewportSize () {
        return mySize;
    }

    @Override
    public int getScrollableBlockIncrement (Rectangle arg0, int arg1, int arg2) {
        return SCROLL_AMOUNT;
    }

    @Override
    public boolean getScrollableTracksViewportHeight () {
        return false;
    }

    @Override
    public boolean getScrollableTracksViewportWidth () {
        return true;
    }

    @Override
    public int getScrollableUnitIncrement (Rectangle arg0, int arg1, int arg2) {
        return SCROLL_AMOUNT;
    }

}
