package vooga.rts.networking.server;

import java.util.HashMap;
import java.util.Map;
import vooga.rts.networking.communications.LobbyInfo;


/**
 * This is a class that contains all the information about the game on the server. In this case a
 * game refers to a generic game created through VOOGA such as "Age of Empires". It contains methods
 * for clients to interact with Rooms.
 * 
 * @author David Winegar
 * 
 */
public class GameContainer extends AbstractThreadContainer {

    private Map<Integer, Room> myRooms = new HashMap<Integer, Room>();
    private Map<Integer, LobbyInfo> myLobbyInfos = new HashMap<Integer, LobbyInfo>();
    private int myRoomNumber = 0;

    /**
     * Removes the room from the game container.
     */
    protected void removeRoom (Room room) {
        myRooms.remove(room.getID());
    }

    /**
     * Adds the room to the game container.
     */
    protected void addRoom (Room room) {
        myRooms.put(room.getID(), room);
    }

    /**
     * Joins the lobby if the lobby exists.
     * @param thread that is joining
     * @param lobbyNumber number of lobby
     */
    @Override
    public void joinLobby (ConnectionThread thread, int lobbyNumber) {
        if (myRooms.containsKey(lobbyNumber)) {
            removeConnection(thread);
            myRooms.get(lobbyNumber).addConnection(thread);
        }
    }
    
    @Override
    public void startLobby (ConnectionThread thread, LobbyInfo lobbyInfo) {
        Room lobby = new Lobby(myRoomNumber, this, lobbyInfo);
        myRoomNumber++;
        lobby.addConnection(thread);
    }
    
    @Override
    public void requestLobbies (ConnectionThread thread) {
        
    }

}
