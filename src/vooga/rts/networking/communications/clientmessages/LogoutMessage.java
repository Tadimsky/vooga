package vooga.rts.networking.communications.clientmessages;

import vooga.rts.networking.server.ConnectionThread;
import vooga.rts.networking.server.IThreadContainer;

public class LogoutMessage extends ClientInfoMessage {

    private static final long serialVersionUID = 3025289959143418637L;

    @Override
    public void affectServer (ConnectionThread thread, IThreadContainer server) {
        server.removeConnection(thread);
        thread.close();
    }

}
