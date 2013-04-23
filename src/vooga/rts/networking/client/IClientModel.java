package vooga.rts.networking.client;

import vooga.rts.networking.communications.ExpandedLobbyInfo;
import vooga.rts.networking.communications.LobbyInfo;


/**
 * Represents the client to ServerInfoMessages
 * 
 * @author David Winegar
 * 
 */
public interface IClientModel {

    public void closeConnection ();

    public void addLobbies (LobbyInfo[] lobbies);
    
    public void switchToLobby (ExpandedLobbyInfo lobbyInfo, int id);
    
    public void updateLobby (ExpandedLobbyInfo lobbyInfo);

}
