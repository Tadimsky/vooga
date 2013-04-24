package vooga.rts.networking.communications.clientmessages;

import vooga.rts.networking.communications.AbstractLobbyInfoMessage;
import vooga.rts.networking.communications.ExpandedLobbyInfo;
import vooga.rts.networking.communications.LobbyInfo;
import vooga.rts.networking.server.ConnectionThread;
import vooga.rts.networking.server.IThreadContainer;


public class UpdateLobbyInfoMessage extends AbstractLobbyInfoMessage implements ClientInfoMessage {
    /**
     * Create the UpdateLobbyInfoMessage with the requisite LobbyInfo.
     * 
     * @param lobbyInfo
     */
    public UpdateLobbyInfoMessage (LobbyInfo lobbyInfo) {
        super(lobbyInfo);
    }

    private static final long serialVersionUID = -8851500817586023212L;

    @Override
    public void affectServer (ConnectionThread thread, IThreadContainer server) {
        server.updateLobbyInfo(thread, (ExpandedLobbyInfo) super.getLobbyInfo());
    }

}
