package vooga.rts.networking.communications.clientmessages;

import vooga.rts.networking.communications.AbstractLobbyInfoMessage;
import vooga.rts.networking.communications.LobbyInfo;
import vooga.rts.networking.server.ConnectionThread;
import vooga.rts.networking.server.IThreadContainer;


/**
 * Gives the server the information necessary to start a lobby.
 * 
 * @author David Winegar
 * 
 */
public class StartLobbyMessage extends AbstractLobbyInfoMessage implements ClientInfoMessage {
    /**
     * Create the StartLobbyMessage with the requisite LobbyInfo.
     * 
     * @param lobbyInfo
     */
    public StartLobbyMessage (LobbyInfo lobbyInfo) {
        super(lobbyInfo);
        // TODO Auto-generated constructor stub
    }

    private static final long serialVersionUID = -6507449287521383418L;

    /**
     * Instantiates message with lobbyInfo
     * 
     * @param lobbyInfo info to send
     */

    @Override
    public void affectServer (ConnectionThread thread, IThreadContainer server) {
        server.startLobby(thread, super.getLobbyInfo());
    }

}
