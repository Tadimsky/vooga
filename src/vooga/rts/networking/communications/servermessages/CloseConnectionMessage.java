package vooga.rts.networking.communications.servermessages;

import vooga.rts.networking.client.IClientModel;


/**
 * Closes the client connection.
 * 
 * @author David Winegar
 * 
 */
public class CloseConnectionMessage extends ServerInfoMessage {

    private static final long serialVersionUID = 1723928759615502490L;

    @Override
    public void affectClient (IClientModel model) {
        model.closeConnection();
    }

}
