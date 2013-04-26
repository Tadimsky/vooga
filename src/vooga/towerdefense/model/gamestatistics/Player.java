package vooga.towerdefense.model.gamestatistics;

import java.util.HashMap;
import java.util.Map;
import vooga.towerdefense.controller.Controller;

/**
 * 
 * This class maintains the state of each game, i.e. the scores that the player achieved,
 * the amount of currency that they have, their health, etc. 
 * 
 * @author Leonard K. Ng'eno
 *
 */
public class Player {

    private static final int ZERO = 0;
    private static final int DEFAULT_SCORE = 0;
    private static final int DEFAULT_MONEY = 100;
    private static final int BEGINNING_HEALTH = 5;
    private static final String CURRENCY_KEYWORD = "Currency";
    private static final String HEALTH_KEYWORD = "Health";
    private static final String SCORE_KEYWORD = "Score";
    private int myCurrency;
    private int myHealth;
    private int myScore;
    private int myKilledUnits;
    private int myEscapedUnits;
    private Controller myController;
    private Map<String, Integer> myStats;
    
    public Player (Controller controller) {
        myController = controller;
        myStats = new HashMap<String, Integer>();
        initDefaults();
    }

    //TODO read these from a file/resource
    private void initDefaults () {
        myCurrency = DEFAULT_MONEY;
        myHealth = BEGINNING_HEALTH;
        myScore = DEFAULT_SCORE;
        myKilledUnits = ZERO;
        myEscapedUnits = ZERO;
        update();
    }
    
    public Map<String, Integer> getPlayerData () {
        myStats.put(CURRENCY_KEYWORD, myCurrency);
        myStats.put(HEALTH_KEYWORD, myHealth);
        myStats.put(SCORE_KEYWORD, myScore);
        return myStats;
    }
    
    private void update() {
        myController.displayPlayerStatistics(getPlayerData());
    }
    
    /**
     * update the player's currency
     * 
     * @param addition  the value to be added to the player's total currency amount
     */
    public void setCurrency(int addition) {
        myCurrency += addition;
        update();
    }
    
    /**
     * get the amount of currency the player has
     * 
     * @return  the player's current currency amount
     */
    public int getCurrency () {
        return myCurrency;
    }
    
    /**
     * set the player's currency
     * 
     * @param health    the amount by which to update the player's health
     */
    public void setHealth (int health) {
        myHealth += health;
        update();
    }
    
    /**
     * get the player's current health
     * 
     * @return  the player's health for this game
     */
    public int getHealth () {
        return myHealth;
    }
    
    /**
     * update the player's game score 
     * @param score     the amount by which to update the player's score
     */
    public void setScore (int score) {
        myScore += score;
        update();
    }
    
    /**
     * get the player's game score
     * @return  the player's score for this game
     */
    public int getScore () {
        return myScore;
    }
    
    /**
     * Updates number of units that successfully reached the end destination on the map
     * @param unit
     */
    public void setEscapedUnits (int unit) {
        myEscapedUnits +=unit;
    }
    
    /**
     * Updates number of units that the player has managed to kill
     * @param unit
     */
    public void setKilledUnits (int unit) {
        myKilledUnits +=unit;
    }
}
