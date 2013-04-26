package vooga.fighter.controller;
import java.util.List;
import java.util.ArrayList;
import vooga.fighter.model.LevelMode;
import vooga.fighter.model.Mode;
import vooga.fighter.model.utils.Health;
import vooga.fighter.util.HUDVariable;


/**     
 * Contains all information required by the view about game objects in a game loop.
 * List indices line up between lists (i.e. index 0 of all lists is player 1 information,
 * index 1 is player 2 info)
 * @author Matt Parides
 * @author Jerry Li 
 * @author Jack Matteucci 
 * @author Wayne You
 *
 */

public class GameLoopInfo extends DisplayLoopInfo implements ViewDataSource{

    private List<String> myCharacterNames;
    private List<Health> myHealthStats;
    private List<Double> myScores;
    private List<PlayerStatus> myPlayerStats;
    private int myNumberPlayers;
    private LevelMode myLevelMode;

    @HUDVariable(
                 name = "Player1",
                 HUDElementClass = "PlayerScoreAndHealth"
            )
    private PlayerStatus Player1Status;

    @HUDVariable(
                 name = "Player2",
                 HUDElementClass = "PlayerScoreAndHealth"
            )
    private PlayerStatus Player2Status;

    @HUDVariable(
                 name = "Player3",
                 HUDElementClass = "PlayerScoreAndHealth"
            )
    private PlayerStatus Player3Status;

    @HUDVariable(
                 name = "Player4",
                 HUDElementClass = "PlayerScoreAndHealth"
            )
    private PlayerStatus Player4Status;
    
    /**
     * Constructs gameloopinfo with levelmode
     * @param mode
     */
    public GameLoopInfo(LevelMode mode) {
    	super(mode);
    	myLevelMode = mode;
    	myPlayerStats = new ArrayList<PlayerStatus>();
        myHealthStats = new ArrayList<Health>();
        myScores = new ArrayList<Double>();
        myCharacterNames = new ArrayList<String>();
        myNumberPlayers = mode.getCharacterObjects().size();
        initializePlayers();
    }
    
    /**
     * Returns mode
     */
    @Override
    public Mode getMode() {
        return myLevelMode;
    }
    
    /**
     * Initialize player statuses to be displayed.
     * Because of how display is painted in view, method is a little messy.
     * Had to work with what we have. 
     */
    public void initializePlayers() {
          if (myNumberPlayers == 1) {
              Player1Status = new PlayerStatus();
              myPlayerStats.add(Player1Status);
          }
          else if (myNumberPlayers == 2) {
              Player1Status = new PlayerStatus();
              Player2Status = new PlayerStatus();
              myPlayerStats.add(Player1Status);
              myPlayerStats.add(Player2Status);
          }
          else if (myNumberPlayers == 3) {
              Player1Status = new PlayerStatus();
              Player2Status = new PlayerStatus();
              Player3Status = new PlayerStatus();
              myPlayerStats.add(Player1Status);
              myPlayerStats.add(Player2Status);
              myPlayerStats.add(Player3Status);
          }
          else if (myNumberPlayers == 4) {
              Player1Status = new PlayerStatus();
              Player2Status = new PlayerStatus();
              Player3Status = new PlayerStatus();
              Player4Status = new PlayerStatus();
              myPlayerStats.add(Player1Status);
              myPlayerStats.add(Player2Status);
              myPlayerStats.add(Player3Status);
              myPlayerStats.add(Player4Status);
          }
          addHUDElements();
    }
    
    /**
     * Update stats and displays
     */
    @Override
    public void update() {
        super.update();
        updateStats();
       setChanged();
       notifyObservers();
    }
    
    
    /**
     * update the stats by getting information from 
     * mode
     */
    public void updateStats() {
        LevelMode currentMode = (LevelMode) getMode();
        myHealthStats = currentMode.getHealthStats();
        for (int i = 0; i < myNumberPlayers; i++) {
            myPlayerStats.get(i).setHealth(myHealthStats.get(i));
        }
    }
    
    /**
     * @return Health at list index
     */
    public Health getHealth(int index) {
        return myHealthStats.get(index);
    }

    
    /**
     * Return double score at index
     * @param index     index
     * @return          
     */
    public Double getScore(int index) {
        return myScores.get(index);
    }

   

    /**
     * Returns list of health
     * @return the myHealthStats
     */
    public List<Health> getHealthStats() {
        return myHealthStats;
    }

    /**
     * Sets list of health
     * @param myHealthStats the myHealthStats to set
     */
    public void setHealthStats(List<Health> healthStats) {
        myHealthStats = healthStats;
    }
    
    /**
     * Sets health at index
     * @param index     index
     * @param heal      health
     */
    public void setHealthStat(int index, Health heal) {
        myHealthStats.set(index, heal);
    }

    /**
     * Sets scores
     * @param scores
     */
    public void setScores(List<Double> scores) {
        myScores = scores;
    }


    /**
     * Set score at index
     * @param index     index
     * @param score     double score
     */
    public void setScore(int index, double score) {
        myScores.set(index, score);
    }

    /**
     * Return double scores;
     * @return
     */
    public List<Double> getScores() {
        return myScores;
    }


}
