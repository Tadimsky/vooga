package vooga.fighter.controller;

import java.util.List;
import java.util.ResourceBundle;
import vooga.fighter.util.HUDVariable;

/**
 * Displays winning Player by text
 * 
 * @author Jerry Li
 *
 */
public class ScoreInfo extends DisplayLoopInfo implements ViewDataSource{
    
    
    /**
     * File where win statements are located
     */
    private static final String myFileName = "WinStatements";
    
    @HUDVariable(
                 name = "Winner",
                 HUDElementClass = "Text"
            )
    private String myWinner;
    private String myWinStatement;
    private ResourceBundle myWinStatements;
    
    /**
     * Constructors Score info with default
     * config file package name
     */
    public ScoreInfo(String config) {
        super();
        myWinStatements = ResourceBundle.getBundle(config+myFileName);
        myWinStatement = myWinStatements.getString(this.getClass().getSimpleName());
    }
    
    /**
     * Gets the winners and displays
     * To change the win statement, just change the text in
     * WinningText.properties
     * @param winners   list of winners
     */
    public void setWinners(List<Integer> winners) {
        System.out.println(myWinStatement + winners.get(0));
        for (int i = 0; i < winners.size(); i++) {
            myWinner = myWinStatement + i;
        }
        addHUDElements();
    }
    
    
}
