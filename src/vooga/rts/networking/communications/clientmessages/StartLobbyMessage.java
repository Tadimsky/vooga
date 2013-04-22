package vooga.rts.networking.communications.clientmessages;

import vooga.rts.networking.communications.LobbyInfo;
import vooga.rts.networking.server.ConnectionThread;
import vooga.rts.networking.server.IThreadContainer;


/**
 * Gives the server the information necessary to start a lobby.
 * 
 * @author David Winegar
 * 
 */
public class StartLobbyMessage extends ClientInfoMessage {

    private static final long serialVersionUID = -6507449287521383418L;
    private LobbyInfo myLobbyInfo;

    /**
     * Instantiates message with lobbyInfo
     * @param lobbyInfo info to send
     */
    public StartLobbyMessage (LobbyInfo lobbyInfo) {
        myLobbyInfo = lobbyInfo;
    }

    @Override
    public void affectServer (ConnectionThread thread, IThreadContainer server) {
        server.startLobby(thread, myLobbyInfo);
    }

}
