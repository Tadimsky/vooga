package vooga.rts.networking.client;

import vooga.rts.networking.communications.IMessage;


/**
 * Represents what is receiving messages on the client. In a game, implmented by a class that
 * receives all network input.
 * 
 * @author David Winegar
 * 
 */
public interface IMessageReceiver {
    /**
     * Returns the current message being recieved.
     * 
     * @param message message received
     */
    public void getMessage (IMessage message);

    /**
     * Notifies the message receiver that the connection has been closed.
     */
    public void connectionClosed ();
}
