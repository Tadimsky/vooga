package arcade.games;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the class a game uses for persistence of USER information.
 * at the very least your game should define a users score in a game.
 * Any other data, such as what level a user is on should be stored 
 * here in subclassed UserGameDatas.
 * @author Will Nance
 */
public  class UserGameData implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private int myLastScore = 0;
    
    public UserGameData() {
    }
    
    /**
     * Represents something like a score or a win/loss ratio or any number
     * that is relevant to how a user is performing.
     */
    public int getLastScore() {
        return myLastScore;
    }

    /**
     * Lets the game update the user's statistic.
     */
    public void setScore (int score) {
        myLastScore = score;
    }

  
}