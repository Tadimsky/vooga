package vooga.rts.networking.communications.clientmessages;

import vooga.rts.networking.communications.ExpandedLobbyInfo;
import vooga.rts.networking.server.ConnectionThread;
import vooga.rts.networking.server.IThreadContainer;

public class UpdateLobbyInfoMessage extends ClientInfoMessage {

    private static final long serialVersionUID = -8851500817586023212L;
    private ExpandedLobbyInfo myLobbyInfo;
    public UpdateLobbyInfoMessage (ExpandedLobbyInfo lobbyInfo) {
        myLobbyInfo = lobbyInfo;
    }
    @Override
    public void affectServer (ConnectionThread thread, IThreadContainer server) {
        server.updateLobbyInfo(thread, myLobbyInfo);
    }

}
