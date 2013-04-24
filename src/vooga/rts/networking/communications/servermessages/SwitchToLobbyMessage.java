package vooga.rts.networking.communications.servermessages;

import vooga.rts.networking.client.IClientModel;
import vooga.rts.networking.communications.AbstractLobbyInfoMessage;
import vooga.rts.networking.communications.ExpandedLobbyInfo;


/**
 * Tells the client to switch to a lobby.
 * 
 * @author David Winegar
 * 
 */
public class SwitchToLobbyMessage extends AbstractLobbyInfoMessage implements ServerInfoMessage {

    private static final long serialVersionUID = 8524506463613939449L;
    private int myID;

    /**
     * Instantiates the message
     * 
     * @param lobbyInfo info to store
     */
    public SwitchToLobbyMessage (ExpandedLobbyInfo lobbyInfo, int initialID) {
        super(lobbyInfo);
        myID = initialID;
    }

    @Override
    public void affectClient (IClientModel model) {
        model.switchToLobby((ExpandedLobbyInfo) getLobbyInfo(), myID);
    }

}
