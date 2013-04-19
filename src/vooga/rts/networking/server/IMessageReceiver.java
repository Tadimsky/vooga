package vooga.rts.networking.server;

import vooga.rts.networking.communications.Message;

/**
 * An interface for adding ability to interact with ConnectionThread.
 * Permits sending messages or objects to parent server.
 * 
 * @author srwareham
 * @author David Winegar
 * 
 */
public interface IMessageReceiver {

    /**
     * Receives the passed in message from the ConnectionThread.
     * @param message to receive
     * @param thread where message is from
     */
    public void receiveMessageFromClient (Message message, ConnectionThread thread);
    
    /**
     * Removes this ConnectionThread from the IMessageReceiver.
     * @param thread to remove
     */
    public void removeConnection (ConnectionThread thread);

}

