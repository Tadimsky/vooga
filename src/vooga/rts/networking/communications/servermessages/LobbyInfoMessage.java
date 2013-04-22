package vooga.rts.networking.communications.servermessages;

import vooga.rts.networking.client.IClientModel;
import vooga.rts.networking.communications.ExpandedLobbyInfo;


/**
 * Sends ExpandedLobbyInfo to the client.
 * 
 * @author David Winegar
 * 
 */
public class LobbyInfoMessage extends ServerInfoMessage {

    private static final long serialVersionUID = 6729477946727355957L;

    private ExpandedLobbyInfo myLobbyInfo;

    /**
     * Instantiates the messsage.
     * 
     * @param info to send
     */
    public LobbyInfoMessage (ExpandedLobbyInfo info) {
        myLobbyInfo = info;
    }

    @Override
    public void affectClient (IClientModel model) {
        // TODO Auto-generated method stub
    }

}
