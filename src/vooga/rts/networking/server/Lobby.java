package vooga.rts.networking.server;

import vooga.rts.networking.communications.LobbyInfo;
import vooga.rts.networking.communications.servermessages.LobbyInfoMessage;


/**
 * This class represents a Lobby where users can change information.
 * 
 * @author David Winegar
 * 
 */
public class Lobby extends Room {

    /**
     * Instantiates the Lobby.
     * @param myRoomNumber number of room
     * @param gameContainer game container
     * @param lobbyInfo lobby info
     */
    public Lobby (int myRoomNumber, GameContainer gameContainer, LobbyInfo lobbyInfo) {
        super(myRoomNumber, gameContainer, lobbyInfo);
    }

    @Override
    public void leaveLobby (ConnectionThread thread) {
        removeConnection(thread);
        getGameContainer().addConnection(thread);
        if (haveNoConnections()) {
            getGameContainer().removeRoom(this);
        }
    }

    @Override
    public void startGameServer (ConnectionThread thread) {
        new GameServer(getID(), getGameContainer(), this);
    }

    @Override
    public void addConnection (ConnectionThread thread) {
        super.addConnection(thread);
        thread.sendMessage(new LobbyInfoMessage(getLobbyModel()));
    }
}
