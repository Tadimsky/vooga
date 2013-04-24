package vooga.rts.networking.communications.servermessages;

import vooga.rts.networking.client.IClientModel;
import vooga.rts.networking.communications.IMessage;


/**
 * Represents a message from server to client.
 * 
 * @author David Winegar
 * @author srwareham
 * 
 */
public interface ServerInfoMessage extends IMessage {

    /**
     * Updates the client in some way.
     * 
     * @param model to affect
     */
    public abstract void affectClient (IClientModel model);

}
