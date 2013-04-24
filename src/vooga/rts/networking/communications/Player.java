package vooga.rts.networking.communications;

import java.io.Serializable;


/**
 * Class represents a player that can play a game
 * (can be either AI or Human)
 * This serves as a handover betwee the networking game setup and then the in-game settings.
 * 
 * @author srwareham
 * @author Henrique Moraes
 * 
 */
public class Player implements Serializable {

    private static final long serialVersionUID = -2457375513280272584L;
    private String myName;
    private int myID;
    private String myFaction;
    private int myTeam;

    /**
     * A player object whose purpose is to contain state for the game simulation to read in and
     * construct based off of appropriately.
     * 
     * @param name 
     * @param team 
     * @param faction 
     * @param id 
     */
    public Player (String name, int team, String faction, int id) {
        myName = name;
        myID = id;
        myFaction = faction;
        myTeam = team;
    }

    /**
     * Returns the name of the player
     * 
     * @return
     */
    public String getName () {
        return myName;
    }

    /**
     * Returns the ID of the player.
     * 
     * @return
     */
    public int getId () {
        return myID;
    }

    /**
     * Returns the faction of the player.
     * 
     * @return
     */
    public String getFaction () {
        return myFaction;
    }

    /**
     * Returns the team of the player.
     * 
     * @return
     */
    public int getTeam () {
        return myTeam;
    }

    /**
     * Setter for the faction of the player.
     * 
     * @param faction 
     */
    public void setFaction (String faction) {
        myFaction = faction;
    }

    /**
     * Setter for the team of the player.
     * 
     * @param team 
     */
    public void setTeam (int team) {
        myTeam = team;
    }

    @Override
    public boolean equals (Object obj) {
        if (!(obj instanceof Player)) {
            return false;
        }
        Player other = (Player) obj;
        if (other.getId() == myID) {
            return true;
        }

        return false;
    }
    
    @Override
    public int hashCode () {
        return myID;
    }

}
