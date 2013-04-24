package vooga.rts.networking.server;

import java.util.logging.Level;

import util.logger.NetworkLogger;
import vooga.rts.networking.NetworkBundle;
import vooga.rts.networking.communications.ExpandedLobbyInfo;
import vooga.rts.networking.communications.LobbyInfo;
import vooga.rts.networking.communications.servermessages.SendLobbyInfoUpdatesMessage;
import vooga.rts.networking.communications.servermessages.SwitchToLobbyMessage;


/**
 * This class represents a Lobby where users can change information.
 * 
 * @author David Winegar
 * 
 */
public class Lobby extends Room {

    /**
     * Instantiates the Lobby.
     * 
     * @param myRoomNumber number of room
     * @param gameContainer game container
     * @param lobbyInfo lobby info
     */
    public Lobby (int myRoomNumber, GameContainer gameContainer, LobbyInfo lobbyInfo) {
        super(myRoomNumber, gameContainer, lobbyInfo);
    }

    @Override
    public void leaveLobby (ConnectionThread thread, ExpandedLobbyInfo lobbyInfo) {
        setLobbyInfo(lobbyInfo);
        removeConnection(thread);
        getGameContainer().addConnection(thread);
        getGameContainer().decrementLobbyInfoSize(getID());
        if (haveNoConnections()) {
            getGameContainer().removeRoom(this);
        }
        else {
            sendMessageToAllConnections(new SendLobbyInfoUpdatesMessage(lobbyInfo));
        }
        NetworkLogger.getLogger().log(Level.INFO,
                                 NetworkBundle.getString("LobbyLeft") + ": " +
                                         lobbyInfo.getLobbyName());
    }

    @Override
    public void startGameServer (ConnectionThread thread) {
        new GameServer(getID(), getGameContainer(), this);
    }

    @Override
    public void addConnection (ConnectionThread thread) {
        super.addConnection(thread);
        thread.sendMessage(new SwitchToLobbyMessage(getLobbyModel(), thread.getID()));
        getGameContainer().incrementLobbyInfoSize(getID());
    }

    @Override
    public void updateLobbyInfo (ConnectionThread thread, ExpandedLobbyInfo lobbyInfo) {
        setLobbyInfo(lobbyInfo);
        sendMessageToAllConnections(new SendLobbyInfoUpdatesMessage(lobbyInfo));
    }

}
