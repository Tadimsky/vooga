package vooga.rts.networking.communications.clientmessages;

import vooga.rts.networking.server.ConnectionThread;
import vooga.rts.networking.server.IThreadContainer;

/**
 * Requests information about a specific server.
 * @author David Winegar
 *
 */
public class JoinLobbyMessage extends ClientInfoMessage {

    private static final long serialVersionUID = -874567492715054864L;
    private int myLobbyNumber;
    
    /**
     * Joins the lobby.
     * @param lobbyNumber number of the lobby
     */
    public JoinLobbyMessage (int lobbyNumber) {
        myLobbyNumber = lobbyNumber;
    }
    
    @Override
    public void affectServer (ConnectionThread thread, IThreadContainer server) {
        server.joinLobby(thread, myLobbyNumber);
    }

}
