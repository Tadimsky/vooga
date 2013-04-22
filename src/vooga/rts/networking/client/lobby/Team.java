package vooga.rts.networking.client.lobby;

import java.util.ArrayList;
import java.util.List;

import vooga.rts.networking.client.Player;

public class Team implements Comparable<Team> {
    private int myNumber;
    private int myMaxSize;
    private String myName;
    private List<Player> myPlayers; //TODO: would ideally like a different type than string for players
    
    public Team(int teamNumber, int maxSize, String name ) {
        myNumber = teamNumber;
        myMaxSize = maxSize;
        myPlayers = new ArrayList<Player>();
        myName = name;
        
    }
    
    /**
     * If the team is not full, add a player to the team
     * @param player
     */
    public void addPlayer(Player player) {
        if (!isFull()) {
            myPlayers.add(player);
        }else{
            //TODO: throw some sort of error saying that the team is full. this should neve be allowed to happen
            //internally, this will only be called if the team is not full
        }
    }
    /**
     * Returns whether or not the team is full.
     * @return
     */
    public boolean isFull() {
        if(getSize() == myMaxSize) {
            return true;
        }
        return false;
    }
    /**
     * Returns the name of the team.
     * @return
     */
    public String getName() {
        return myName;
    }
    
    /**
     * Returns the number of the team (ie, team 1, team 2)
     * @return
     */
    public int getNumber() {
        return myNumber;
    }
    /**
     * Returns the current size of the team.
     * @return
     */
    public int getSize() {
        return myPlayers.size();
    }
    
    /**
     * Provided a player, checks if the team contains that player.
     * @param player
     * @return
     */
    public boolean contains(Player player) {
        if (myPlayers.contains(player)) {
            return true;
        }
        return false;
    }
    
    /**
     * Removes the provided player from the team
     * @param player
     */
    public void removePlayer(Player player) {
        myPlayers.remove(player);
    }
    
    @Override
    public int compareTo (Team arg0) {
     // TODO Auto-generated method stub 1 <2 -> -1
        if (getSize() == arg0.getSize()) {
            return getNumber() - arg0.getNumber(); //TODO: confirm this is not backwards sorting
        }
        return getSize() - arg0.getSize();
    }
}
