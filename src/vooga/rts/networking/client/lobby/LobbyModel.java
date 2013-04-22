package vooga.rts.networking.client.lobby;

import java.util.Comparator;
import java.util.PriorityQueue;

import vooga.rts.networking.client.Player;
import vooga.rts.networking.communications.ExpandedLobbyInfo;

public class LobbyModel {
    private ExpandedLobbyInfo myInfo;
    private PriorityQueue<Team> myTeams;
    private String myLobbyName;
    private String myMapName;
    
    
/**
 * This functions as the waiting area for a game about to commence.
 * This also contains necessary state to pass along to the game in order to begin the simulation/ gameplay.
 * Note: this will probably be mooved to the server, and the view for this will live
 * in the client (and pull from the server-side model)
 * @param lobbyName
 * @param mapName
 * @param seedPlayer
 * @param maxPlayers
 * @param playersPerTeam
 */
    public LobbyModel(String lobbyName,String mapName, Player seedPlayer, int maxPlayers, int playersPerTeam) {
        myLobbyName = lobbyName;
        myMapName= mapName;
        
        int numTeams = maxPlayers/ playersPerTeam;
        myTeams = new PriorityQueue<Team>(numTeams);
    }
    
    
    /**
     * This function serves to offer support for sending a serialized summary of the lobby
     * so that connected clients can have an object sent to them containing relevant on-screen information.
     * @return
     */
    public ExpandedLobbyInfo getLobbyInfo() {
        return myInfo;
        
    }
    /**
     * This function serves to offer support of overwriting the state of the model from a summary file
     * Note :This will only be used if we decide to implement the LobbyModel in both the server and Client
     * @param info
     */
    public void updateLobbyInfo(ExpandedLobbyInfo info) {
        myInfo = info;
    }
    
    /**
     * This Method checks to see if there are any available slots in the lobby, if so, 
     * A player is added to the team with the fewest players.  If all teams share
     * the same number of players, the new player is added to the team with the lowest number, ie team 1 first.
     * @param player
     */
    public void addPlayer(Player player) {
        Team head = myTeams.peek();
        if (!head.isFull()) {
            head.addPlayer(player);
        }else{
            //TODO:throw error message that the server is full.  once again this
            //is called internally and this error should never occur
        }
    }
    
    /**
     * Provided a player to remove, this method searches through the existing teams and
     * removes said player from its team.  If it is not a member of a team Should not occur) nothing happens
     * @param player
     */
    public void removePlayer(Player player) {
        Team team = getTeam(player);
        if (team != null) {
            team.removePlayer(player);
        }
    }
    
    /**
     * Returns the Parent team of an input player. Returns null if player
     * is not a member of any team.
     * @param player
     * @return
     */
    public Team getTeam(Player player) {
        for (Team team: myTeams) {
            if (team.contains(player)) {
                return team;
            }
        }
            return null;
    }
    
    /**
     * Method to remove a player from its current team, and add it to the 
     * desired new team
     * Note: perhaps wil need some sort of collection of connected peers
     * from which the move method will not remove a player so that
     * they are constantly considered connected.  Calling remove player
     * may become an issue, this implementation may need to change
     * if removing a player is to cut connections with them.
     * @param player
     * @param newTeam
     */
    public void movePlayer(Player player, Team newTeam) {
        removePlayer(player);
        newTeam.addPlayer(player);
    }
    
    // perhaps need a method here that talks to the server checking who is still conencted
    //if someone disconnects, they will be removed from the team
    
    /**
     * This method returns whether or not the lobby is full.
     * 
     * Note: could probably just return the value of the head, because the queue is in order
     * @return
     */
    public boolean isLobbyFull() {
        for (Team team: myTeams) {
            if (!team.isFull()) {
                return false;
            }
        }
        return true;
    }    
    
    /**
     * Returns the name of the lobby.
     * @return
     */
    public String getName() {
        return myLobbyName;
    }
    
    /**
     * Returns the name of the current Map.
     * Note: perhaps will actually return a map object instead, if desired
     * @return
     */
    public String getMapName() {
        return myMapName;
    }
    
    //TODO:  Either need a method to actually start the game by passing all the necessary constructors,
    //or some sort of boolean that states that the game is ready to be played, so come find my relevant information
    //and begin the game with it
}
