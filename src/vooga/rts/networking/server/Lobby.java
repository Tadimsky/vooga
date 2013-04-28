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

    private int myNumberOfClientsReady = 0;

    /**
     * Instantiates the Lobby.
     * 
     * @param myRoomNumber number of room
     * @param gameContainer game container
     * @param lobbyInfo lobby info
     * @param logger log this
     */
    public Lobby (int myRoomNumber, GameContainer gameContainer, LobbyInfo lobbyInfo, Logger logger) {
        super(myRoomNumber, gameContainer, lobbyInfo, logger);
    }

    @Override
    public void leaveLobby (ConnectionThread thread, ExpandedLobbyInfo lobbyInfo) {
        setLobbyInfo(lobbyInfo);
        getGameContainer().addConnection(thread);
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
        myNumberOfClientsReady++;
        if (myNumberOfClientsReady == getNumberOfConnections()) {
            sendMessageToAllConnections(new StartGameMessage());
            createGameServerFromLobby();
        }
    }

    /**
     * Creates a game server and destroys this lobby. This is overridable for any subclasses that
     * want to make a different type of GameServer.
     */
    protected void createGameServerFromLobby () {
        new GameServer(getID(), getGameContainer(), this, getLogger());
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

    @Override
    public void removeConnection (ConnectionThread thread) {
        getLobbyInfo().removePlayer(thread.getID());
        removeConnectionAndUpdateInfo(thread);

    }

    /**
     * Removes the connection from the threads, decrements the lobby size, and if there are no more
     * connections, removes the lobby. If not it sends a message to all connections.
     * 
     * @param thread to remove
     */
    protected void removeConnectionAndUpdateInfo (ConnectionThread thread) {
        super.removeConnection(thread);
        getGameContainer().decrementLobbyInfoSize(getID());
        if (haveNoConnections()) {
            getGameContainer().removeRoom(this);
        }
        else {
            sendMessageToAllConnections(new SendLobbyInfoUpdatesMessage(getLobbyInfo()));
        }
    }

}
