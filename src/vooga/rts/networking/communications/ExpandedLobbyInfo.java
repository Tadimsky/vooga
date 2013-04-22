package vooga.rts.networking.communications;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import vooga.rts.networking.client.Player;


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
    private List<List<Player>> myPlayerList = new ArrayList<List<Player>>();
    private int myMaxTeams;

    /**
     * Creates the expanded lobby info.
     * 
     * @param lobbyName name of lobby
     * @param mapName name of map
     * @param maxPlayers max players
     * @param ID id
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
            List<Player> team = myPlayerList.get(i);
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
     * @param teamNumber number of team
     */
    public void addPlayer (Player player, int teamNumber) {
        if (myMaxTeams < teamNumber) return;

        addPlayer();
        extendTeams(teamNumber);
        myPlayerList.get(teamNumber).add(player);
    }

    /**
     * Extends the player list to the desired number of teams
     * 
     * @param numOfTeams
     */
    private void extendTeams (int numOfTeams) {
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
        for (List<Player> team : myPlayerList) {
            if (team.contains(player)) {
                team.remove(player);
                removePlayer();
            }
        }
    }

    /**
     * Moves the given player to the team number
     * 
     * @param player player to move
     * @param team team to move to
     */
    public void movePlayer (Player player, int team) {
        if (team <= myMaxTeams) {
            removePlayer(player);
            addPlayer(player, team);
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

    /**
     * 
     * @return List with the teams in the lobby, it returns a copy of the
     *         original list so it is used for read purposes only
     */
    public List<List<Player>> getTeams () {
        List<List<Player>> teams = new LinkedList<List<Player>>();
        for (int i = 0; i < myPlayerList.size(); i++) {
            List<Player> players = new LinkedList<Player>();
            teams.add(new LinkedList<Player>());
            for (Player p : myPlayerList.get(i)) {
                players.add(p);
            }
        }
        return teams;
    }

}
