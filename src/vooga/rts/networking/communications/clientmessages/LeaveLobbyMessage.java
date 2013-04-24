package vooga.rts.networking.communications.clientmessages;

import vooga.rts.networking.communications.AbstractLobbyInfoMessage;
import vooga.rts.networking.communications.ExpandedLobbyInfo;
import vooga.rts.networking.server.ConnectionThread;
import vooga.rts.networking.server.IThreadContainer;


public class LeaveLobbyMessage extends AbstractLobbyInfoMessage implements ClientInfoMessage {

    public LeaveLobbyMessage (ExpandedLobbyInfo lobbyInfo) {
        super(lobbyInfo);
    }

    private static final long serialVersionUID = -7284546842435886007L;

    @Override
    public void affectServer (ConnectionThread thread, IThreadContainer server) {
        server.leaveLobby(thread, (ExpandedLobbyInfo) super.getLobbyInfo());
    }

}
