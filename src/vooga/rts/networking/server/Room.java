package vooga.rts.networking.server;

import vooga.rts.networking.communications.ExpandedLobbyInfo;
import vooga.rts.networking.communications.LobbyInfo;


/**
 * This is the superclass for Lobby and GameServer. It represents a small
 * 
 * @author David Winegar
 * 
 */
public class Room extends AbstractThreadContainer {
    private GameContainer myGameContainer;
    private int myID;
    private ExpandedLobbyInfo myLobbyModel;

    /**
     * Creates a room with ID and GameContainer.
     * 
     * @param id room number
     * @param container GameContainer
     * @param lobbyInfo lobby info
     */
    public Room (int id, GameContainer container, LobbyInfo lobbyInfo) {
        setIDandContainer(id, container);
        myLobbyModel = new ExpandedLobbyInfo(lobbyInfo, id);
    }

    /**
     * Creates a Room with ID and GameContainer while copying in all current connections from the
     * other room, removing that room's connections, removing that room from the container, and
     * adding this room to the container.
     * 
     * @param id room number
     * @param container GameContainer
     * @param room room to replace
     */
    public Room (int id, GameContainer container, Room room) {
        super(room);
        setIDandContainer(id, container);
        myLobbyModel = room.getLobbyModel();
        room.removeAllConnections();
        container.removeRoom(room);
        container.addRoom(this);
    }

    private void setIDandContainer (int id, GameContainer container) {
        myID = id;
        myGameContainer = container;
    }

    protected GameContainer getGameContainer () {
        return myGameContainer;
    }

    protected int getID () {
        return myID;
    }

    protected ExpandedLobbyInfo getLobbyModel () {
        return myLobbyModel;
    }

}
