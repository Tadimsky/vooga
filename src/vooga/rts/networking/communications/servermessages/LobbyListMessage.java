package vooga.rts.networking.communications.servermessages;

import vooga.rts.networking.client.IClientModel;
import vooga.rts.networking.communications.LobbyInfo;
import vooga.rts.networking.communications.Message;


/**
 * Contains a list of all the lobby infos for the server browser. An array was chosen instead of a
 * list for performance reasons, when sending over the network an array is more lightweight.
 * 
 * @author David Winegar
 * 
 */
public class LobbyListMessage extends Message implements ServerInfoMessage {

    private static final long serialVersionUID = -1875703902581296257L;
    private LobbyInfo[] myLobbies;

    /**
     * Instantiates the message with the given array of lobbies.
     * 
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
