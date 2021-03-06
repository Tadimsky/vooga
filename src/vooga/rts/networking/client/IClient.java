package vooga.rts.networking.client;

import vooga.rts.networking.communications.IMessage;


/**
 * Interface for game to be able to access features of the client / server setup.
 * Presumably this will be implemented in a thread that will continuously send and receive
 * messages to the server
 * 
 * @author David Winegar
 * 
 */
public interface IClient {
    /**
     * Sends this message to the server.
     * 
     * @param message to send
     */
    public void sendMessage (IMessage message);

    /**
     * Sets this as the message receiver.
     * 
     * @param messageReceiver new object that receives messages
     */
    public void setMessageReceiver (IMessageReceiver messageReceiver);

    /**
     * Closes the connection with the server.
     */
    public void closeConnection ();

}
