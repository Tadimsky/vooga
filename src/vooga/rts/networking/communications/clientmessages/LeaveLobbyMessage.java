package vooga.rts.networking.communications.clientmessages;

import vooga.rts.networking.communications.AbstractLobbyInfoMessage;
import vooga.rts.networking.communications.ExpandedLobbyInfo;
import vooga.rts.networking.server.ConnectionThread;
import vooga.rts.networking.server.IThreadContainer;


/**
 * Message for leaving a lobby.
 * 
 * @author srwareham
 * 
 */
public class LeaveLobbyMessage extends AbstractLobbyInfoMessage implements ClientInfoMessage {


    /**
     * 
     */
    private static final long serialVersionUID = -5925114408392492922L;

    /**
     * Create a message for leaving a lobby.
     * 
     * @param lobbyInfo 
     */
    public LeaveLobbyMessage (ExpandedLobbyInfo lobbyInfo) {
        super(lobbyInfo);
    }

    @Override
    public void affectServer (ConnectionThread thread, IThreadContainer server) {
        server.leaveLobby(thread, (ExpandedLobbyInfo) super.getLobbyInfo());
    }

}
