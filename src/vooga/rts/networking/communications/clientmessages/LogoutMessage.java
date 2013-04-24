package vooga.rts.networking.communications.clientmessages;

import vooga.rts.networking.communications.Message;
import vooga.rts.networking.server.ConnectionThread;
import vooga.rts.networking.server.IThreadContainer;


/**
 * Sends a message that asks the server to close the connection.
 * 
 * @author David Winegar
 * 
 */
public class LogoutMessage extends Message implements ClientInfoMessage {

    private static final long serialVersionUID = 3025289959143418637L;

    @Override
    public void affectServer (ConnectionThread thread, IThreadContainer server) {
        server.removeConnection(thread);
        thread.close();
    }

}
