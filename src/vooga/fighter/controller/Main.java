package vooga.fighter.controller;
import java.awt.*;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import vooga.fighter.view.Canvas;


/**
 * Creates window that can be moved, resized, and closed by the user.
 *
 * @author Jack Matteucci
 */
public class Main extends JFrame
{
    // constants
    public static final Dimension SIZE = new Dimension(800, 600);
    public static final String TITLE = "FightingGame";


    /**
     * main --- where the program starts
     * @param args
     */
    public static void main (String args[])
    {
        // view of user's content
    	
        // start animation
        GameManager control = new GameManager();
        control.run();
        // Stopping the game is handled in the MarioTime Class, as it can implement methods that update 
        // as the game updates
        // Full Screen Options are handled in the Canvas Class (as they must be updated with the game).
    }
}
