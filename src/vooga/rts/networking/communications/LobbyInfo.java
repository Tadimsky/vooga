package vooga.rts.networking.communications;

import java.io.Serializable;


/**
 * Basic lobby information used for sending across the network to the server browser. Lightweight so
 * we can send many of these very quickly.
 * 
 * @author David Winegar
 * @author Sean Wareham
 * 
 */
public class LobbyInfo implements Serializable {

    private static final long serialVersionUID = -1941237597305628081L;
    private String myLobbyName;
    private int myMaxPlayers;
    private String myMapName;
    private int myPlayersCount = 0;
    private int myID;

    public LobbyInfo (String lobbyName, String mapName, int maxPlayers, int ID) {
        myLobbyName = lobbyName;
        myMaxPlayers = maxPlayers;
        myMapName = mapName;
        myID = ID;
    }

    /**
     * Returns the name of the lobby
     * 
     * @return
     */
    public String getLobbyName () {
        return myLobbyName;
    }

    /**
     * Returns the maximum number of players this lobby can hold.
     * 
     * @return
     */
    public int getMaxPlayers () {
        return myMaxPlayers;
    }

    /**
     * Returns name of the Map of the current lobby.
     * 
     * @return
     */
    public String getMapName () {
        return myMapName;
    }

    /**
     * Returns true if Lobby is full.
     * 
     * @return
     */
    public boolean isLobbyFull () {
        return (myPlayersCount == myMaxPlayers);
    }

    public void removePlayer () {
        myPlayersCount--;
    }

    public void addPlayer () {
        myPlayersCount++;
    }

    /**
     * Returns the id.
     * 
     * @return id number
     */
    public int getID () {
        return myID;
    }

}
