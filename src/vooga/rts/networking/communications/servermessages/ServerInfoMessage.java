package vooga.rts.networking.communications.servermessages;

import vooga.rts.networking.client.IClientModel;
import vooga.rts.networking.communications.Message;


/**
 * Represents a message from server to client.
 * 
 * @author David Winegar
 * 
 */
public abstract class ServerInfoMessage extends Message {

    private static final long serialVersionUID = 1155598288906476729L;

    /**
     * Updates the client in some way.
     * 
     * @param model to affect
     */
    public abstract void affectClient (IClientModel model);

}
