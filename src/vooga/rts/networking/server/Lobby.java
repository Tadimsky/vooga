package vooga.rts.networking.server;

import java.util.logging.Level;
import java.util.logging.Logger;
import vooga.rts.networking.NetworkBundle;
import vooga.rts.networking.communications.ExpandedLobbyInfo;
import vooga.rts.networking.communications.LobbyInfo;
import vooga.rts.networking.communications.servermessages.FinalizeLobbyInfoMessage;
import vooga.rts.networking.communications.servermessages.SendLobbyInfoUpdatesMessage;
import vooga.rts.networking.communications.servermessages.StartGameMessage;
import vooga.rts.networking.communications.servermessages.SwitchToLobbyMessage;


/**
 * This class represents a Lobby where users can change information.
 * 
 * @author David Winegar
 * 
 */
public class Lobby extends Room {

    private int numberOfClientsReady = 0;

    /**
     * Instantiates the Lobby.
     * 
     * @param myRoomNumber number of room
     * @param gameContainer game container
     * @param lobbyInfo lobby info
     */
    public Lobby (int myRoomNumber, GameContainer gameContainer, LobbyInfo lobbyInfo, Logger logger) {
        super(myRoomNumber, gameContainer, lobbyInfo, logger);
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
        getLogger().log(Level.INFO,
                        NetworkBundle.getString("LobbyLeft") + ": " +
                                lobbyInfo.getLobbyName());
    }

    @Override
    public void requestGameStart (ConnectionThread thread) {
        if (getLobbyInfo().isLobbyFull() &&
            getLobbyInfo().getNumberOfPlayers() == getNumberOfConnections()) {
            sendMessageToAllConnections(new FinalizeLobbyInfoMessage(getLobbyInfo()));
        }
    }

    @Override
    public void clientIsReadyToStart (ConnectionThread thread) {
        numberOfClientsReady++;
        if (numberOfClientsReady == getNumberOfConnections()) {
            sendMessageToAllConnections(new StartGameMessage());
            new GameServer(getID(), getGameContainer(), this, getLogger());
        }
    }

    @Override
    public void addConnection (ConnectionThread thread) {
        super.addConnection(thread);
        thread.sendMessage(new SwitchToLobbyMessage(getLobbyInfo(), thread.getID()));
        getGameContainer().incrementLobbyInfoSize(getID());
    }

    @Override
    public void updateLobbyInfo (ConnectionThread thread, ExpandedLobbyInfo lobbyInfo) {
        setLobbyInfo(lobbyInfo);
        sendMessageToAllConnections(new SendLobbyInfoUpdatesMessage(lobbyInfo));
    }

}
