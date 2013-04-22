package vooga.rts.networking.client;

import java.util.List;
import vooga.rts.networking.communications.LobbyInfo;

/**
 * Represents the client to ServerInfoMessages
 * @author David Winegar
 *
 */
public interface IClientModel {

    public void closeConnection ();

    public void addLobbies (LobbyInfo[] lobbies);

}
