package vooga.rts.networking.communications.clientmessages;

import vooga.rts.networking.communications.IMessage;
import vooga.rts.networking.server.ConnectionThread;
import vooga.rts.networking.server.IThreadContainer;


/**
 * Represents a message from client to server.
 * 
 * @author David Winegar
 * @author srwareham
 * 
 */
public interface ClientInfoMessage extends IMessage {

    /**
     * Called by the server with the current thread and thread container.
     * 
     * @param thread thread that sent message
     * @param server current ThreadContainer for thread
     */
    public abstract void affectServer (ConnectionThread thread, IThreadContainer server);

}
