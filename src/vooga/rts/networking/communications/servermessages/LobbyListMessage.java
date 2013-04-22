package vooga.rts.networking.communications.servermessages;

import vooga.rts.networking.client.IClientModel;
import vooga.rts.networking.communications.LobbyInfo;


/**
 * Contains a list of all the lobby infos for the server browser.
 * 
 * @author David Winegar
 * 
 */
public class LobbyListMessage extends ServerInfoMessage {

    private static final long serialVersionUID = -1875703902581296257L;
    private LobbyInfo[] myLobbies;

    /**
     * Instantiates the message with the given list.
     * @param lobbyList list to send
     */
    public LobbyListMessage (LobbyInfo[] lobbyList) {
        myLobbies = lobbyList;
    }

    @Override
    public void affectClient (IClientModel model) {
        model.addLobbies(myLobbies);
    }

}
