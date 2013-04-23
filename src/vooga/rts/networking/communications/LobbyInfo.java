package vooga.rts.networking.communications;

import java.io.Serializable;
import java.util.Observable;


/**
 * Basic lobby information used for sending across the network to the server browser. Lightweight so
 * we can send many of these very quickly.
 * 
 * @author David Winegar
 * @author Sean Wareham
 * 
 */
public class LobbyInfo extends Observable implements Serializable {

    private static final long serialVersionUID = -1941237597305628081L;
    private String myLobbyName;
    private int myMaxPlayers;
    private String myMapName;
    private int myPlayersCount = 0;
    private int myID;

    /**
     * Creates the lobbyInfo.
     * @param lobbyName name of lobby
     * @param mapName name of map
     * @param maxPlayers maximum players
     * @param ID id
     */
    public LobbyInfo (String lobbyName, String mapName, int maxPlayers, int ID) {
        myLobbyName = lobbyName;
        myMaxPlayers = maxPlayers;
        myMapName = mapName;
        myID = ID;
    }
    
    /**
     * Constructor that creates a new LobbyInfo with a different ID
     * @param lobbyInfo info
     * @param newID new ID
     */
    public LobbyInfo (LobbyInfo lobbyInfo, int newID) {
        this(lobbyInfo.getLobbyName(), lobbyInfo.getMapName(), lobbyInfo.getMaxPlayers(), newID);
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
        return myPlayersCount == myMaxPlayers;
    }

    /**
     * Decreases current player count by 1
     */
    public void removePlayer () {
        myPlayersCount--;
    }

    /**
     * Increases player count by 1
     */
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
    
    /**
     * Gets the current number of players
     * @return number of players
     */
    public int getCurrentPlayers () {
        return myPlayersCount;
    }

}
