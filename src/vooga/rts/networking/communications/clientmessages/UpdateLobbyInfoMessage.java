package vooga.rts.networking.communications.clientmessages;

import vooga.rts.networking.communications.AbstractLobbyInfoMessage;
import vooga.rts.networking.communications.ExpandedLobbyInfo;
import vooga.rts.networking.communications.LobbyInfo;
import vooga.rts.networking.server.ConnectionThread;
import vooga.rts.networking.server.IThreadContainer;


/**
 * Message for updating the lobby information.
 * 
 * @author srwareham
 * 
 */
public class UpdateLobbyInfoMessage extends AbstractLobbyInfoMessage implements ClientInfoMessage {
    /**
     * 
     */
    private static final long serialVersionUID = 397434129045885123L;

    /**
     * Create the UpdateLobbyInfoMessage with the requisite LobbyInfo.
     * 
     * @param lobbyInfo 
     */
    public UpdateLobbyInfoMessage (LobbyInfo lobbyInfo) {
        super(lobbyInfo);
    }

    @Override
    public void affectServer (ConnectionThread thread, IThreadContainer server) {
        server.updateLobbyInfo(thread, (ExpandedLobbyInfo) super.getLobbyInfo());
    }

}
