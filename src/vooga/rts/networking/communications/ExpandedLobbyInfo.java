package vooga.rts.networking.communications;

import java.util.ArrayList;
import java.util.List;
import vooga.rts.networking.client.lobby.Player;


/**
 * Expanded lobby information used by the lobby model and view. Passed back and forth between the
 * server and client.
 * 
 * @author Sean Wareham
 * @author David Winegar
 * @author Henrique Moraes
 * 
 */
public class ExpandedLobbyInfo extends LobbyInfo {

    private static final long serialVersionUID = 8433220026468566119L;
    /**
     * Inner list represents a team, outer list represents all the teams
     */
    List<ArrayList<Player>> myPlayerList = new ArrayList<ArrayList<Player>>();
    private int myMaxTeams;

    /**
     * Creates the expanded lobby info.
     * 
     * @param lobbyName
     * @param mapName
     * @param maxPlayers
     * @param ID
     */
    public ExpandedLobbyInfo (String lobbyName,
                              String mapName,
                              int maxPlayers,
                              int ID) {
        super(lobbyName, mapName, maxPlayers, ID);
        myMaxTeams = maxPlayers;
    }

    /**
     * Copies the existing lobbyInfo parameters to make the new lobbyInfo.
     * 
     * @param lobbyInfo info to copy
     */
    public ExpandedLobbyInfo (LobbyInfo lobbyInfo) {
        this(lobbyInfo.getLobbyName(), lobbyInfo.getMapName(), lobbyInfo.getMaxPlayers(), lobbyInfo
                .getID());
    }

    /**
     * Copies the existing lobbyInfo parameters to make the new lobbyInfo, except it changes the ID.
     * 
     * @param lobbyInfo info to copy
     * @param newID new ID to give
     */
    public ExpandedLobbyInfo (LobbyInfo lobbyInfo, int newID) {
        this(lobbyInfo.getLobbyName(), lobbyInfo.getMapName(), lobbyInfo.getMaxPlayers(), newID);
    }

    /**
     * This method is used to add a new player to the next available slot. It distributes players
     * evenly among teams
     * 
     * @param player player to add
     */
    public int addPlayer (Player player) {
        addPlayer();
        extendTeams(myPlayerList.size() + 1);
        int oldPlayerCount = 0;
        for (int i = 0; i < myPlayerList.size(); i++) {
            ArrayList<Player> team = myPlayerList.get(i);
            if (oldPlayerCount > team.size()) {
            	team.add(player);
            	return i + 1;
            }
            oldPlayerCount = team.size();
        }
        // should never trigger
        myPlayerList.get(0).add(player);
        return 1;
    }
    
    /**
     * This method is used to add a new player to the specified team.
     * 
     * @param player player to add
     */
    public void addPlayer (Player player, int teamNumber) {
    	if (myMaxTeams < teamNumber) return;

    	addPlayer();
    	extendTeams(teamNumber);
    	ArrayList<Player> team = myPlayerList.get(teamNumber);
    	team.add(player);

    }

    /**
     * Extends the player list to the desired number of teams
     * @param numOfTeams
     */
    private void extendTeams(int numOfTeams){
    	if (numOfTeams > myMaxTeams || myPlayerList.size() >= numOfTeams) return;
    	
    	while (myPlayerList.size() < numOfTeams) {
    		myPlayerList.add(new ArrayList<Player>());
    	}
    }

    /**
     * Removes the given player from the lobby.
     * 
     * @param player to remove
     */
    public void removePlayer (Player player) {
        removePlayer();
        for (ArrayList<Player> team : myPlayerList) {
            team.remove(player);
        }
    }

    /**
     * Moves the given player to the team number
     * 
     * @param player
     * @param team
     */
    public void movePlayer (Player player, int team) {
        if (team <= myMaxTeams) {
            removePlayer(player);
            // Keep the player count correct
            addPlayer();
            myPlayerList.get(team - 1).add(player);
        }
    }

    /**
     * returns the max teams.
     * 
     * @return maximum number of teams
     */
    public int getMaxTeams () {
        return myMaxTeams;
    }

}
