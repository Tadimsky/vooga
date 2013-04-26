package games.examplegenre.example2;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import arcade.games.ArcadeInteraction;
import arcade.games.Game;

/**
 * This example game requires the user to click a button.  Their score is 
 * proportional to amount of time it takes for him/her to click it.
 * 
 * @author Ellango
 *
 */
public class Example2 extends Game{
    private static final String BUTTON_LABEL = "Click here";
    private static final String INSTRUCTION = "To win, click the button. Score is determined by time it takes.";
    private static final int VERTICAL_GAP = 20;
    private static final double SCALING_CONSTANT = 0.01;
    private long myStartTime;
    
    /**
     * Creates the example2 game and sets up its view.
     * 
     * @param arcade
     */
    public Example2 (ArcadeInteraction arcade) {
        super(arcade);
        setUpView();
    }
    
    /**
     * sets the view up with a label for instruction and the button that the user
     * clicks.
     */
    private void setUpView() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Container view = frame.getContentPane();
        view.setLayout(new BorderLayout(0, VERTICAL_GAP));
        view.add(new JLabel(INSTRUCTION), BorderLayout.NORTH);
        JButton button = new JButton(BUTTON_LABEL);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent arg0) {
               int score = computeScore(arg0.getWhen());
               endGame(score);
            }
        });
        view.add(button, BorderLayout.CENTER);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.pack();
    }
    
    @Override
    public void run () {
        myStartTime = System.currentTimeMillis();
    }
    

    /**
     * Computes the score for this game -- the time between starting the game
     * and clicking the button times a scaling constant.
     * @param endTime
     * @return
     */
    private int computeScore (double endTime) {
        return (int) ((endTime - myStartTime) * SCALING_CONSTANT);
    }    
    
    /**
     * After win condition met, wraps the game up by informing
     * the arcade what the score was and that the game is over.
     */
    private void endGame(int score) {
        getArcade().getUserGameData(this).setScore(score);
        getArcade().killGame();
    }
    
    public static void main (String[] args) {
        Game g = new Example2(null);
        g.run();
    }

}
