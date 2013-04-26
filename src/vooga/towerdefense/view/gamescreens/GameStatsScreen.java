package vooga.towerdefense.view.gamescreens;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.swing.JPanel;
import vooga.towerdefense.controller.Controller;
import util.ValueText;


/**
 * Heads Up Display of the game statistics
 * 
 * @author Leonard K. Ng'eno
 * 
 */

public class GameStatsScreen extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final String MONEY_LABEL = "$$";
    private static final String LIVES_LABEL = "Lives";
    private static final Color LABEL_COLOR = Color.GREEN;
    private static final int LABELS_X_OFFSET = 40;
    private static final int MONEY_LABEL_Y_OFFSET = 10;
    private static final int LIVES_LABEL_Y_OFFSET = 30;
    private static final int XCOORD = 0;
    private static final int YCOORD = 0;
    private int myStartingMoney = 0;
    private int myStartingLives = 5;
    private Color myBackgroundColor = Color.WHITE;
    private ValueText myMoney;
    private ValueText myLives;

    public GameStatsScreen (Dimension size, Controller controller) {
        setPreferredSize(size);
        setFocusable(true);

        initStatistics();

        setVisible(true);
    }

    @Override
    public void paintComponent (Graphics pen) {
        super.paintComponent(pen);
        pen.setColor(myBackgroundColor);
        pen.fillRect(XCOORD, YCOORD, getSize().width, getSize().height);
        paintStats((Graphics2D) pen);
    }

    public void update () {
        // reset the values
    }

    private void initStatistics () {
        myMoney = new ValueText(MONEY_LABEL, myStartingMoney);
        myLives = new ValueText(LIVES_LABEL, myStartingLives);
    }

    private void paintStats (Graphics2D pen) {
        myMoney.paint(pen, new Point(LABELS_X_OFFSET, MONEY_LABEL_Y_OFFSET), LABEL_COLOR);
        myLives.paint(pen, new Point(LABELS_X_OFFSET, LIVES_LABEL_Y_OFFSET), LABEL_COLOR);
    }
}
