package vooga.towerdefense.view.gamescreens;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import vooga.towerdefense.controller.Controller;


/**
 * This class enables the player to click on items
 * on the ShopScreen section, buy these
 * items, and then place them on the map screen.
 * 
 * @author Leonard K. Ng'eno
 * 
 */
public class ShopScreen extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final int XCOORD = 0;
    private static final int YCOORD = 0;
    private static final String CANCEL_BUTTON_KEYWORD = "CancelButtonName";
    private Color myBackgroundColor = Color.WHITE;
    private MouseAdapter myMouseListener;
    private Controller myController;
    private JButton myCancelButton;

    /**
     * constructor.
     * 
     * @param size
     * @param controller
     */
    public ShopScreen (Dimension size, Controller controller) {
        setPreferredSize(size);
        setFocusable(true);
        myController = controller;
        makeMouseListener();
        addMouseListener(myMouseListener);
        setVisible(true);
        makeCancelButton();
    }

    /**
     * paints the ShopScreen.
     * 
     * @param pen
     */
    @Override
    public void paintComponent (Graphics pen) {
        super.paintComponent(pen);
        pen.setColor(myBackgroundColor);
        pen.fillRect(XCOORD, YCOORD, getSize().width, getSize().height);
        myController.paintShop(pen);
    }

    /**
     * helper method to make the mouse listener.
     */
    private void makeMouseListener () {
        myMouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent e) {
                if (e.getSource().equals(myCancelButton)) {
                    myController.cancelPurchaseFromShop();
                }
                else {
                    setCancelButtonVisibility(true);
                    myController.handleShopClickOnItem(e.getPoint());
                }
            }
        };
    }

    private void makeCancelButton () {
        myCancelButton = new JButton(myController.getStringFromResources(CANCEL_BUTTON_KEYWORD));
        myCancelButton.addMouseListener(myMouseListener);
        this.add(myCancelButton, BorderLayout.PAGE_END);
        setCancelButtonVisibility(false);
    }

    /**
     * Sets the visibility of the Cancel Button to the value of the parameter
     * 
     * @param visibility
     */
    public void setCancelButtonVisibility (boolean visibility) {
        myCancelButton.setVisible(visibility);
    }
}
