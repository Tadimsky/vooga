package vooga.rts.networking.communications.servermessages;

import vooga.rts.networking.client.IClientModel;
import vooga.rts.networking.communications.AbstractLobbyInfoMessage;
import vooga.rts.networking.communications.LobbyInfo;


/**
 * This sends the final lobby info to the client.
 * 
 * @author David Winegar
 * 
 */
public class FinalizeLobbyInfoMessage extends AbstractLobbyInfoMessage implements ServerInfoMessage {

    private static final long serialVersionUID = 8588233124753040070L;

    /**
     * Instantiates message with lobbyinfo
     * 
     * @param info to send
     */
    public FinalizeLobbyInfoMessage (LobbyInfo info) {
        super(info);
    }

    @Override
    public void affectClient (IClientModel model) {
        // TODO
    }

}
