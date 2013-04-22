package vooga.rts.networking.communications.servermessages;

import vooga.rts.networking.client.IClientModel;
import vooga.rts.networking.communications.ExpandedLobbyInfo;


/**
 * This sends the final lobby info to the client.
 * 
 * @author David Winegar
 * 
 */
public class FinalizeLobbyInfoMessage extends ServerInfoMessage {

    private static final long serialVersionUID = 8588233124753040070L;
    private ExpandedLobbyInfo myLobbyInfo;

    /**
     * Instantiates message with lobbyinf
     * 
     * @param info to send
     */
    public FinalizeLobbyInfoMessage (ExpandedLobbyInfo info) {
        myLobbyInfo = info;
    }

    @Override
    public void affectClient (IClientModel model) {
        // TODO
    }

}
