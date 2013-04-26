package vooga.towerdefense.arcadeinteraction;

import java.util.List;
import arcade.games.UserGameData;

/**
 * Creates a user profile for a towerdefense player.
 * Extends UserGameData from the arcade.
 *
 * @author Angelica Schwartz
 */
public class TowerDefenseUserGameData extends UserGameData {
    
    private double myMoney;
    private List<Double> myScores;
    
    /**
     * constructor.
     */
    public TowerDefenseUserGameData() {
        super();
      //TODO: get starting money value from resource file?
        myMoney = 10;
    }
    
    /**
     * method to set the default starting money value.
     * @param money is the default value
     */
    public void setStartingMoneyValue(double money) {
        myMoney = money;
    }
    
    /**
     * Represents something like a score or a win/loss ratio or any number
     * that is relevant to how a user is performing.
     */
    public List<Double> getScores () {
        return myScores;
    }

    /**
     * Lets the game update the user's statistic.
     */
    public void setScore (double score) {
        myScores.add(score);
    }

}
