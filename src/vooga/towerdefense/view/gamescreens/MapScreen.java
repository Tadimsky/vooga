package vooga.towerdefense.view.gamescreens;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;

import vooga.towerdefense.controller.Controller;


/**
 * Displays the map and everything on the map.
 * 
 * @author Angelica Schwartz
 * 
 */
public class MapScreen extends JPanel {

    /**
     * default serialized id.
     */
    private static final long serialVersionUID = 1L;
    /**
     * controller to pass information to the back-end.
     */
    private Controller myController;
    /**
     * mouse listener for clicking.
     */
    private MouseAdapter myMouseAdapter;
    /**
     * mouse listener for movement & dragging.
     */
    private MouseMotionAdapter myMouseMotionAdapter;
    /**
     * size of the MapScreen.
     */
    private Dimension mySize;

    /**
     * Constructor.
     * 
     * @param size
     * @param controller
     */
    public MapScreen (Dimension size, Controller controller) {
        mySize = size;
        setPreferredSize(mySize);
        setFocusable(true);
        setVisible(true);
        myController = controller;
        makeMouseAdapters();
        addMouseListener(myMouseAdapter);
        addMouseMotionListener(myMouseMotionAdapter);
        repaint();
    }

    /**
     * updates the MapScreen appropriately.
     */
    public void update () {
        revalidate();
        repaint();
    }

    /**
     * paints the MapScreen component.
     * 
     * @param pen is the graphics object
     */
    @Override
    public void paintComponent (Graphics pen) {
        super.paintComponent(pen);
        // paintGridLines(pen);
        myController.paintMap(pen);
    }

    /**
     * helper method to create the listener for mouse input.
     */
    // TODO: integrate this with input team
    private void makeMouseAdapters () {
        myMouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent e) {
                myController.handleMapClick(e.getPoint());
            }
        };
        myMouseMotionAdapter = new MouseMotionAdapter() {
            @Override
            public void mouseMoved (MouseEvent e) {
                myController.handleMapMouseDrag(e.getPoint());

            }
        };
    }
}
