package vooga.rts.networking.communications.clientmessages;

import vooga.rts.networking.server.ConnectionThread;
import vooga.rts.networking.server.IThreadContainer;

/**
 * Requests a list of server messages.
 * @author David Winegar
 *
 */
public class RequestServerListMessage extends ClientInfoMessage {

    private static final long serialVersionUID = -2907131995149913098L;
    private int myStartNumber;
    private int myEndNumber;
    
    public RequestServerListMessage (int startNumber, int endNumber) {
        myStartNumber = startNumber;
        myEndNumber = endNumber;
    }

    @Override
    public void affectServer (ConnectionThread thread, IThreadContainer server) {
        server.requestLobbies(thread, myStartNumber, myEndNumber);
    }

}
