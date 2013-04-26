package vooga.towerdefense.view.introscreens;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.swing.JPanel;
import vooga.towerdefense.view.TDView;

/**
 * An abstract that aids in selection of maps and levels when the game is being 
 * loaded by the player.
 * 
 * @author Leonard K. Ng'eno
 *
 */
public abstract class SelectScreen extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final int XCOORD = 0;
    private static final int YCOORD = 0;
    private Color myBackgroundColor = Color.WHITE;
    private TDView myView;

    public SelectScreen (Dimension size, TDView view) {
        setPreferredSize(size);
        myView = view;
        setVisible(true);
    }

    @Override
    public void paintComponent (Graphics pen) {
        super.paintComponent(pen);
        pen.setColor(myBackgroundColor);
        pen.fillRect(XCOORD, YCOORD, getSize().width, getSize().height);

    }

    /**
     * 
     * @return  parent view container
     */
    public TDView getView () {
        return myView;
    }

    /**
     *  Show the pixmap images representing the level/map
     * @param pen
     */
    public abstract void displayImages (Graphics2D pen);

    /**
     * Check whether the mouse click position represent's an actual map/level
     * image position
     * 
     * @param point     mouse click position
     */
    public abstract void checkPositionClicked (Point point);

}
