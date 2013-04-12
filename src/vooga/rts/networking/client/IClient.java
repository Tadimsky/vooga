package vooga.rts.networking.client;

import vooga.rts.networking.communications.Message;

/**
 * Interface for game to be able to access features of the client / server setup.
 * Presumably this will be implemented in a thread that will continuously send and receive
 * messages to the server
 * @author srwareham
 *
 */
public interface IClient {
    public abstract void sendData (Message message);

    public abstract Message getData ();
}