package vooga.rts.networking.communications.clientmessages;

import vooga.rts.networking.communications.ExpandedLobbyInfo;
import vooga.rts.networking.server.ConnectionThread;
import vooga.rts.networking.server.IThreadContainer;

public class LeaveLobbyMessage extends ClientInfoMessage {

    private static final long serialVersionUID = -7284546842435886007L;
    private ExpandedLobbyInfo myLobbyInfo;

    public LeaveLobbyMessage (ExpandedLobbyInfo lobbyInfo) {
        myLobbyInfo = lobbyInfo;
    }

    @Override
    public void affectServer (ConnectionThread thread, IThreadContainer server) {
       server.leaveLobby(thread, myLobbyInfo);
    }

}
