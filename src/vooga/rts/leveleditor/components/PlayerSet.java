package vooga.rts.leveleditor.components;

import java.util.HashMap;
import java.util.Map;
import vooga.rts.util.Location;
/**
 * this class contains all players in this game
 * @author Richard Yang
 *
 */
public class PlayerSet {
    
    private int myPlayerUpperLimit;
    private int myTeamUpperLimit;
    private int myPlayerNumber;
    
    private Map<Integer, Location> myPlayers;
    
    /**
     * constructor for player set
     * @param playerLimit the upper limit of player
     * @param teamLimit the upper limit of team
     */
    public PlayerSet(int playerLimit, int teamLimit) {
        myPlayerUpperLimit = playerLimit;
        myTeamUpperLimit = teamLimit;
        myPlayerNumber = 0;
        
        myPlayers = new HashMap<Integer, Location>();
    }
    
    /**
     * add a player based on it s
     * @param loc of the player
     */
    public void addPlayer(Location loc) {
        myPlayerNumber++;
        myPlayers.put(myPlayerNumber, loc);
    }
    /**
     * add a player based on x and y
     * @param x x location
     * @param y y location
     */
    public void addPlayer(int x, int y) {
        addPlayer(new Location(x, y));
    }
    
    /**
     * remove a player based on the start location and end location
     * @param startLocation start location of the mouse
     * @param endLocation end location of the mouse
     */
    public void removePlayer(Location startLocation, Location endLocation) {
        int startX = (int)startLocation.getX();
        int startY = (int)startLocation.getY();
        
        int endX = (int) endLocation.getX();
        int endY = (int) endLocation.getY();
        removePlayer(startX, startY, endX, endY);      
    }
    
    /**
     * remove a player based on x and y
     * @param startX start x value
     * @param startY start y
     * @param endX end x
     * @param endY end y
     */
    public void removePlayer(int startX, int startY, int endX, int endY) {
       
       for (Integer i : myPlayers.keySet()) {
            Location loc = myPlayers.get(i);
            int x = (int)loc.getX();
            int y = (int)loc.getY();
            if (((x >= startX && x <= endX)||(x <= startX && x>= endX))&&
                    ((y >= startY && y <= endY)||(y <= startY && y >= endY))) {
                myPlayers.remove(i);
            }
       }
    }

    /**
     * clear all players
     */
    public void clearPlayers() {
        myPlayers.clear();
        myPlayerNumber = 0;
    }

    /**
     * get the upperlimit of players
     * @return player upper limit
     */
    public int getMyPlayerUpperLimit() {
        return myPlayerUpperLimit;
    }
    
    /**
     * get the upper limit of teams
     * @return
     */
    public int getMyTeamUpperLimit() {
        return myTeamUpperLimit; 
    }
    
    /**
     * return the number of players
     * @return
     */
    public int getMyPlayerNumber() {
        return myPlayerNumber;
    }
    
    /**
     * get all players
     * @return myPlayers
     */
    public Map<Integer, Location> getAllPlayers() {
        return myPlayers;
    }
}
