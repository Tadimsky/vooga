package vooga.rts.networking.client;

import vooga.rts.networking.communications.ExpandedLobbyInfo;
import vooga.rts.networking.communications.PlayerInfo;


/**
 * Represents the game to the client-side networking architecture.
 * 
 * @author David Winegar
 * 
 */
public interface NetworkedGame {

    /**
     * Loads the game. Once this terminates, the client tells the server that it may start the game
     * at any time.
     * 
     * @param info info about server
     * @param userPlayer player on this client
     */
    public void loadGame (ExpandedLobbyInfo info, PlayerInfo userPlayer);

    /**
     * Starts the game.
     * 
     * @param client client to start sending messages to and from.
     */
    public void startGame (IClient client);

    /**
     * Notifies the networked game that the server browser has been closed.
     */
    public void serverBrowserClosed ();
}
