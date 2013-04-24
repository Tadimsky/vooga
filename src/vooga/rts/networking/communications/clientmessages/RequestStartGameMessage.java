package vooga.rts.networking.communications.clientmessages;

import vooga.rts.networking.communications.Message;
import vooga.rts.networking.server.ConnectionThread;
import vooga.rts.networking.server.IThreadContainer;


/**
 * Asks the server to start the current lobby.
 * 
 * @author David Winegar
 * 
 */
public class RequestStartGameMessage extends Message implements ClientInfoMessage {

    private static final long serialVersionUID = -1959013766966880914L;

    @Override
    public void affectServer (ConnectionThread thread, IThreadContainer server) {
        server.requestGameStart(thread);
    }

}
