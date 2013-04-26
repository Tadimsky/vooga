package arcade.view.forms;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import twitter4j.TwitterException;
import util.ImageHelper;
import arcade.controller.Controller;
import arcade.games.Score;
import arcade.view.MainView;
import arcade.view.TextKeywords;


/**
 * The view that shows up at the end of the game. Shows the user's score here,
 * high scores, and the option to tweet his/her score.
 * 
 * @author Ellango
 * 
 */
@SuppressWarnings({ "serial", "unused" })
public class ScoresView extends Form {
    private static final String BUTTON_IMAGE = "Twitter.gif";
    private static final int NAME_WIDTH = 100;
    private static final int SCORE_WIDTH = 40;
    private static final int MAX_HIGH_SCORES_SHOWN = 5;
    private static final int WINDOW_WIDTH = 270;
    private static final int HEIGHT_OFFSET = 20;

    private int myScore;
    private String myGameName;
    
    private JLabel myUserScore;
    private JPanel myPinPanel;
    private JTextField myPinTextField;

    /**
     * Creates a ScoresView with a Controller, ResourceBundle, and the score 
     * of the user for the game that has just been played.
     * 
     * @param controller
     * @param resources
     */
    public ScoresView (Controller controller, ResourceBundle resources, int score) {
        super(controller, resources);
        myScore = score;
        myGameName = controller.getCurrentGame();
        setUserScoreMessage(score);
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(0, 0, WINDOW_WIDTH, getPreferredSize().height + HEIGHT_OFFSET);
        setLocationRelativeTo(null);
    }
    
    /**
     * Creates the message for the user displaying his/her score.
     * 
     * To fill in the label, must call setUserScoreMessage
     * @return
     */
    private JComponent createUserScoreMessage() {
        JPanel panel = new JPanel();
        myUserScore = new JLabel("");
        panel.add(myUserScore);
        return panel;
    }
    
    /**
     * Creates the heading to denote high scores are listed below.
     * @return
     */
    private JComponent createHighScoresHeading() {
        return createInstruction(TextKeywords.HIGH_SCORES);
    }
    
    /**
     * Creates the list of the high scores for the game that has just been played.
     * @return
     */
    private JComponent createHighScoresList() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        List<Score> scores = getController().getCurrentGameInfo().getSortedScores();
        if (scores.size() > MAX_HIGH_SCORES_SHOWN) {
            scores = scores.subList(0, MAX_HIGH_SCORES_SHOWN);
        }
        int ranking = 1;
        for (Score score : scores) {
            JPanel row = new JPanel();
            row.add(new JLabel(ranking + ".  "));
            JLabel user = new JLabel(score.getUser());
            user.setPreferredSize(new Dimension(NAME_WIDTH, user.getPreferredSize().height));
            row.add(user);
            JLabel points = new JLabel(score.getScore()+"");
            points.setPreferredSize(new Dimension(SCORE_WIDTH, points.getPreferredSize().height));
            row.add(points);
            row.setOpaque(false);
            panel.add(row);
            ranking++;
        }
        return panel;
    }

    /**
     * Creates the button to start the tweet request.
     * 
     * @return
     */
    private JComponent createTweetButton () {
        JPanel panel = new JPanel();
        ImageIcon icon = new ImageIcon(MainView.IMAGES_DIRECTORY + BUTTON_IMAGE);
        final JButton button = new JButton(icon);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent arg0) {
                startTwitterRequest();
                button.setEnabled(false);
            }
        });
        panel.add(button);
        return panel;
    }
    
    /**
     * Starts the Twitter request.  Opens up a browser for the user to 
     * authorize the twitter request.  Sets the panel visible for the user
     * to enter the pin after they finish authorizing.
     */
    private void startTwitterRequest() {
        try {
            String urlToAuthorize = getController().setUpTwitterRequest();
            Desktop.getDesktop().browse(URI.create(urlToAuthorize));
            myPinPanel.setVisible(true);
            setBounds(0, 0, WINDOW_WIDTH, getPreferredSize().height + HEIGHT_OFFSET);
            setLocationRelativeTo(null);
        }
        catch (TwitterException e) {
            sendMessage(getResources().getString(TextKeywords.TWITTER_ERROR));
        }
        catch (IOException e) {
            sendMessage(getResources().getString(TextKeywords.TWITTER_ERROR));
        }
    }

    /**
     * Creates the panel where the user can enter the pin for the twitter
     * authorization.
     * 
     * Initially set as invisible until startTwitterRequest() called.
     * @return
     */
    private JComponent createPinEntry () {
        myPinPanel = new JPanel();
        myPinPanel.setLayout(new BoxLayout(myPinPanel, BoxLayout.Y_AXIS));
        myPinTextField = new JTextField();
        JComponent textPane = createTextPanel(TextKeywords.PIN_INSTRUCTION, myPinTextField);
        textPane.setOpaque(false);
        myPinPanel.add(textPane);
        JComponent submitButton = createButton(TextKeywords.SUBMIT,
                                      new ActionListener() {
                                          @Override
                                          public void actionPerformed (ActionEvent e) {
                                              sendTweet();
                                          }
                                      });
        submitButton.setOpaque(false);
        myPinPanel.add(submitButton);
        myPinPanel.setVisible(false);
        return myPinPanel;
    }
    
    /**
     * After clicking the submit button for the pin, send the tweet
     * message for the game and score.
     */
    private void sendTweet() {
        String pin = myPinTextField.getText();
        try {
            getController().sendTweet(pin, generateTweetMessage());
        }
        catch (TwitterException e) {
            sendMessage(getResources().getString(TextKeywords.TWITTER_ERROR));
        }
    }
    
    /**
     * Returns the string to be tweeted.
     * @return
     */
    private String generateTweetMessage() {
        return getResources().getString(TextKeywords.MY_SCORE) 
               + " " + myScore + " " 
               + getResources().getString(TextKeywords.IN) + " " 
               + myGameName + " " 
               + getResources().getString(TextKeywords.VOOGA_HASHTAG);
    }
    
    /**
     * Sets the user score message with the text containing the user's score.
     */
    private void setUserScoreMessage(double score) {
        myUserScore.setText(getResources().getString(TextKeywords.SCORE_MESSAGE) + " " + score);
    }
}
