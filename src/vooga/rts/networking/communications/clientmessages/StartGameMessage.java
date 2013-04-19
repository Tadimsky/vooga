package vooga.rts.networking.communications.clientmessages;

import vooga.rts.networking.server.ConnectionThread;
import vooga.rts.networking.server.IThreadContainer;

public class StartGameMessage extends ClientInfoMessage {

    private static final long serialVersionUID = -1959013766966880914L;

    @Override
    public void affectServer (ConnectionThread thread, IThreadContainer server) {
        server.startGameServer(thread);
    }

}
