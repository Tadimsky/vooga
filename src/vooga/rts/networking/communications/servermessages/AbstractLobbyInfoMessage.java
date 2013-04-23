package vooga.rts.networking.communications.servermessages;

import vooga.rts.networking.communications.LobbyInfo;

/**
 * Abstract class for sending LobbyInfo and ExpandedLobbyInfo.
 * @author David Winegar
 *
 */
public abstract class AbstractLobbyInfoMessage extends ServerInfoMessage {

    private static final long serialVersionUID = 2163042663466305077L;
    private LobbyInfo myLobbyInfo;
    
    /**
     * Takes in expandedLobbyInfo.
     * @param lobbyInfo info
     */
    public AbstractLobbyInfoMessage (LobbyInfo lobbyInfo) {
        myLobbyInfo = lobbyInfo;
    }
    
    /**
     * Allows subclasses to get lobbyInfo.
     */
    protected LobbyInfo getLobbyInfo () {
        return myLobbyInfo;
    }

}
