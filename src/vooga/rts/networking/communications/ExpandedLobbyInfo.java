package vooga.rts.networking.communications;

import java.util.Arrays;


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
    private int myMaxTeams;
    private Player[] myPlayers;
    private int myNextSlot = 0;

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
        myPlayers = new Player[maxPlayers];
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
    public void addPlayer (Player player) {
        if (myNextSlot != getMaxPlayers()) {
            addPlayer();
            myPlayers[myNextSlot] = player;
            for (int i = myNextSlot + 1; i < myPlayers.length; i++) {
                if (myPlayers[i] == null) {
                    myNextSlot = i;
                    return;
                }
            }
            myNextSlot = getMaxPlayers();
        }
    }

    /**
     * Removes the given player from the lobby.
     * 
     * @param player to remove
     */
    public void removePlayer (Player player) {
        for (int i = 0; i < myPlayers.length; i++) {
            if (myPlayers[i] != null && myPlayers[i].equals(player)) {
                myPlayers[i] = null;
                if (myNextSlot > i) {
                    myNextSlot = i;
                }
            }
        }
    }

    /**
     * Swaps out the player with a newer version.
     * 
     * @param player to change
     */
    public void changePlayer (Player player) {
        for (int i = 0; i < myPlayers.length; i++) {
            if (myPlayers[i] != null && myPlayers[i].equals(player)) {
                myPlayers[i] = player;
            }
        }
    }
    
    /**
     * Gets the player in the given position
     * @param position of player
     * @return player
     */
    public Player getPlayerAtPosition (int position) {
        return myPlayers[position];
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
     * Returns a copy of the current players.
     * 
     * @return copy of player array
     */
    public Player[] getPlayers () {
        return Arrays.copyOf(myPlayers, myPlayers.length);
    }
    
    /**
     * Returns if the game is startable.
     * @return true if game can be started
     */
    public boolean canStartGame () {
        for (int i = 0; i < myPlayers.length; i++) {
            if (myPlayers[i] == null) {
                return false;
            }
        }
        
        int team1 = myPlayers[0].getTeam();
        for (int i = 0; i < myPlayers.length; i++) {
            if (myPlayers[i].getTeam() != team1) {
                return true;
            }
        }
        return false;
    }

}
